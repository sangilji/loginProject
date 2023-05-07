package com.example.login.domain;

import org.springframework.core.log.LogMessage;
import org.springframework.security.access.hierarchicalroles.CycleInRoleHierarchyException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRoleHierarchyImpl extends RoleHierarchyImpl {

    private Map<String, String> roleHierarchyMapRepresentation = null;

    private Map<String, Set<GrantedAuthority>> rolesReachableInOneStepMap = null;

    private Map<String, Set<GrantedAuthority>> rolesReachableInOneOrMoreStepsMap = null;


    public void setHierarchy(Map<String, String> roleHierarchyStringRepresentation) {
        this.roleHierarchyMapRepresentation = roleHierarchyStringRepresentation;

        buildRolesReachableInOneStepMap();
        buildRolesReachableInOneOrMoreStepsMap();
    }


    private void buildRolesReachableInOneStepMap() {
        this.rolesReachableInOneStepMap = new HashMap<>();

        roleHierarchyMapRepresentation.forEach((parent, child) -> {
            String higherRole = parent;
            GrantedAuthority lowerRole = new SimpleGrantedAuthority(child);
            Set<GrantedAuthority> rolesReachableInOneStepSet;
            if (!this.rolesReachableInOneStepMap.containsKey(higherRole)) {
                rolesReachableInOneStepSet = new HashSet<>();
                this.rolesReachableInOneStepMap.put(higherRole, rolesReachableInOneStepSet);
            } else {
                rolesReachableInOneStepSet = this.rolesReachableInOneStepMap.get(higherRole);
            }
            rolesReachableInOneStepSet.add(lowerRole);

        });
    }


    private void buildRolesReachableInOneOrMoreStepsMap() {
        this.rolesReachableInOneOrMoreStepsMap = new HashMap<>();
        // iterate over all higher roles from rolesReachableInOneStepMap
        for (String roleName : this.rolesReachableInOneStepMap.keySet()) {
            Set<GrantedAuthority> rolesToVisitSet = new HashSet<>(this.rolesReachableInOneStepMap.get(roleName));
            Set<GrantedAuthority> visitedRolesSet = new HashSet<>();
            while (!rolesToVisitSet.isEmpty()) {
                // take a role from the rolesToVisit set
                GrantedAuthority lowerRole = rolesToVisitSet.iterator().next();
                rolesToVisitSet.remove(lowerRole);
                if (!visitedRolesSet.add(lowerRole)
                        || !this.rolesReachableInOneStepMap.containsKey(lowerRole.getAuthority())) {
                    continue; // Already visited role or role with missing hierarchy
                } else if (roleName.equals(lowerRole.getAuthority())) {
                    throw new CycleInRoleHierarchyException();
                }
                rolesToVisitSet.addAll(this.rolesReachableInOneStepMap.get(lowerRole.getAuthority()));
            }
            this.rolesReachableInOneOrMoreStepsMap.put(roleName, visitedRolesSet);
        }

    }
}

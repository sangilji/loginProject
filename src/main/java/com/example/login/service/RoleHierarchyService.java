package com.example.login.service;

import com.example.login.domain.RoleHierarchy;
import com.example.login.repository.RoleHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RoleHierarchyService {

    private final RoleHierarchyRepository roleHierarchyRepository;

    @Transactional
    public Map<String, String> findAllHierarchyToMap() {
        List<RoleHierarchy> roleHierarchies = roleHierarchyRepository.findAll();
        Map<String, String> map = new HashMap<>();

        for (RoleHierarchy roleHierarchy : roleHierarchies) {
            if (roleHierarchy.getParentName() != null) {
                map.put(roleHierarchy.getParentName().getChildName(), roleHierarchy.getChildName());
            }
        }
        return map;
    }

    @Transactional
    public String findAllHierarchy() {
        List<RoleHierarchy> rolesHierarchy = roleHierarchyRepository.findAll();

        Iterator<RoleHierarchy> itr = rolesHierarchy.iterator();
        StringBuffer concatedRoles = new StringBuffer();
        while (itr.hasNext()) {
            RoleHierarchy model = itr.next();
            if (model.getParentName() != null) {
                concatedRoles.append(model.getParentName().getChildName());
                concatedRoles.append(" > ");
                concatedRoles.append(model.getChildName());
                concatedRoles.append("\n");
            }
        }
        return concatedRoles.toString();
    }
}

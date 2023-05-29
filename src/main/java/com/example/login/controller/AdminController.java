package com.example.login.controller;

import com.example.login.config.UrlFilterInvocationSecurityMetadataSource;
import com.example.login.domain.Resources;
import com.example.login.domain.ResourceDto;
import com.example.login.domain.Role;
import com.example.login.repository.RoleRepository;
import com.example.login.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final ResourceService resourceService;
    private final RoleRepository roleRepository;
    private final UrlFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @GetMapping(value="/resources")
    public ResponseEntity<?> getResources() throws Exception {
        return new ResponseEntity<>(resourceService.getResources(),HttpStatus.OK);
    }


    @PostMapping("/resources")
    public ResponseEntity<?> createResources(@RequestBody ResourceDto resourceDto) throws Exception {
        log.info("{}",resourceDto);
        ModelMapper modelMapper = new ModelMapper();
        Role role = roleRepository.findByRoleName(resourceDto.getRoleName());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        Resources resources = modelMapper.map(resourceDto, Resources.class);
        resources.setRoleSet(roles);
        resourceService.insertResources(resources);
        filterInvocationSecurityMetadataSource.reload();
        return new ResponseEntity<>(resourceService.getResources(), HttpStatus.OK);
    }
}

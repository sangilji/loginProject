package com.example.login.service;

import com.example.login.domain.ResourceDto;
import com.example.login.domain.Resources;
import com.example.login.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    @Override
    public Resources getResources(long id) {
        return resourceRepository.findById(id).orElse(new Resources());
    }

    @Override
    public List<ResourceDto> getResources() {
        List<Resources> resources = resourceRepository.findAll();
        List<ResourceDto> returnResources = new ArrayList<>();
        resources.forEach(resource -> returnResources.add(ResourceDto.builder()
                        .resourceName(resource.getResourceName())
                        .resourceType(resource.getResourceType())
                        .orderNum(resource.getOrderNum())
                        .httpMethod(resource.getHttpMethod())
                        .roleName(resource.getRoleSet().iterator().next().getRoleName())
                .build()));
        return returnResources;
    }

    @Override
    public void insertResources(Resources resources) {
        resourceRepository.save(resources);
    }

    @Override
    public void deleteResources(long id) {
        resourceRepository.deleteById(id);

    }
}

package com.example.login.service;

import com.example.login.domain.ResourceDto;
import com.example.login.domain.Resources;

import java.util.List;

public interface ResourceService {
    Resources getResources(long id);

    List<ResourceDto> getResources();

    void insertResources(Resources resources);

    void deleteResources(long id);
}

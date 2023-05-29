package com.example.login.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {

    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;
    private String roleName;

}

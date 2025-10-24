package com.api.apiRest.dtos.request;

import lombok.Data;

@Data
public class StudentCreateDTO {
    private String name;
    private String enrollment; // Matrícula
}
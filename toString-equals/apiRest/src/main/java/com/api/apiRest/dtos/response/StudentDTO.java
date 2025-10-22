package com.api.apiRest.dtos.response;
import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    private String enrollment; // Matrícula
}

package com.api.apiRest.dtos.response;
import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String name;
    private ProfessorDTO professor;
}
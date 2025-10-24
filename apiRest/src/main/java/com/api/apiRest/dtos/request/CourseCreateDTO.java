package com.api.apiRest.dtos.request;
import lombok.Data;

@Data
public class CourseCreateDTO {
    private String name;
    private Long professorId;
}
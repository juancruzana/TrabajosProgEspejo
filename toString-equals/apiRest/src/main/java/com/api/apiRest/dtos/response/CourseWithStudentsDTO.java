package com.api.apiRest.dtos.response;
import lombok.Data;
import java.util.List;

@Data
public class CourseWithStudentsDTO {
    private Long id;
    private String name;
    private ProfessorDTO professor;
    private List<StudentDTO> students;
}
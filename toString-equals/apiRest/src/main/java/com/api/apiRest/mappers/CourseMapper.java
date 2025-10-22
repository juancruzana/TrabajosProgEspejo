package com.api.apiRest.mappers;

import com.api.apiRest.dtos.response.CourseDTO;
import com.api.apiRest.dtos.response.CourseWithStudentsDTO;
import com.api.apiRest.entity.Course;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfessorMapper.class, StudentMapper.class})
public interface CourseMapper {
    CourseDTO toDto(Course course);

    @Mapping(source = "students", target = "students")
    CourseWithStudentsDTO toWithStudentsDto(Course course);

    List<CourseDTO> toDtoList(List<Course> courses);
}
package com.api.apiRest.mappers;

import com.api.apiRest.dtos.request.ProfessorCreateDTO;
import com.api.apiRest.dtos.response.ProfessorDTO;
import com.api.apiRest.entity.Professor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    ProfessorDTO toDto(Professor professor);

    List<ProfessorDTO> toDtoList(List<Professor> professors);

    Professor toEntity(ProfessorCreateDTO createDTO);
}
package com.api.apiRest.service;

import com.api.apiRest.dtos.request.ProfessorCreateDTO;
import com.api.apiRest.dtos.response.ProfessorDTO;
import com.api.apiRest.entity.Professor;
import com.api.apiRest.mappers.ProfessorMapper;
import com.api.apiRest.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    public List<ProfessorDTO> findAll() {
        List<Professor> professors = professorRepository.findAll();
        return professorMapper.toDtoList(professors);
    }

    public ProfessorDTO create(ProfessorCreateDTO createDTO) {
        Professor professor = professorMapper.toEntity(createDTO);

        Professor savedProfessor = professorRepository.save(professor);

        return professorMapper.toDto(savedProfessor);
    }
}
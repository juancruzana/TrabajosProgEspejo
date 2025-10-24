package com.api.apiRest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.api.apiRest.dtos.response.ProfessorDTO;
import com.api.apiRest.dtos.request.ProfessorCreateDTO;
import com.api.apiRest.service.ProfessorService;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping
    public List<ProfessorDTO> getAllProfessors() {
        return professorService.findAll();
    }

    @PostMapping
    public ProfessorDTO createProfessor(@RequestBody ProfessorCreateDTO createDTO) {
        return professorService.create(createDTO);
    }
}
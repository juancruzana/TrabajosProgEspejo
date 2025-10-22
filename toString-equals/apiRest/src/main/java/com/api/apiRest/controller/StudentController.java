package com.api.apiRest.controller;

import com.api.apiRest.dtos.request.StudentCreateDTO;
import com.api.apiRest.dtos.response.StudentDTO;
import com.api.apiRest.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("*")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<StudentDTO> getAllProfessors() {
        return studentService.findAll();
    }

    @PostMapping
    public StudentDTO createProfessor(@RequestBody StudentCreateDTO createDTO) {
        return studentService.create(createDTO);
    }
}

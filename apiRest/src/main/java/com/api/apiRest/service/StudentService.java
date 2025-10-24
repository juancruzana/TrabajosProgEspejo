package com.api.apiRest.service;

import com.api.apiRest.dtos.request.StudentCreateDTO;
import com.api.apiRest.dtos.response.StudentDTO;
import com.api.apiRest.entity.Student;
import com.api.apiRest.mappers.StudentMapper;
import com.api.apiRest.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public List<StudentDTO> findAll() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.toDtoList(students);
    }

    public StudentDTO create(StudentCreateDTO createDTO) {
        Student student = studentMapper.toEntity(createDTO);
        // La relación ManyToMany se inicializa vacía.
        student.setCourses(new HashSet<>());

        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDto(savedStudent);
    }
}
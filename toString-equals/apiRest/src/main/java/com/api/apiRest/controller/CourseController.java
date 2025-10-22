package com.api.apiRest.controller;

import com.api.apiRest.dtos.request.CourseCreateDTO;
import com.api.apiRest.dtos.response.CourseDTO;
import com.api.apiRest.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseCreateDTO createDTO) {
        CourseDTO newCourse = courseService.createCourse(createDTO);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }
}
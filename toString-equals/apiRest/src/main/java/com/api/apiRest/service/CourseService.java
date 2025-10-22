package com.api.apiRest.service;

import com.api.apiRest.dtos.request.CourseCreateDTO;
import com.api.apiRest.dtos.request.EnrollmentDTO;
import com.api.apiRest.dtos.response.CourseDTO;
import com.api.apiRest.dtos.response.CourseWithStudentsDTO;
import com.api.apiRest.entity.Course;
import com.api.apiRest.entity.Professor;
import com.api.apiRest.entity.Student;
import com.api.apiRest.mappers.CourseMapper;
import com.api.apiRest.repository.CourseRepository;
import com.api.apiRest.repository.ProfessorRepository;
import com.api.apiRest.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Necesario para transacciones complejas

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final CourseMapper courseMapper;


    public List<CourseDTO> findAll() {
        List<Course> courses = courseRepository.findAll();
        return courseMapper.toDtoList(courses);
    }


    @Transactional
    public CourseDTO createCourse(CourseCreateDTO createDTO) {
        // Buscar el profesor, si no existe, lanzar excepción
        Professor professor = professorRepository.findById(createDTO.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor not found with ID: " + createDTO.getProfessorId()));

        // Crear y configurar la Entidad Course
        Course course = new Course();
        course.setName(createDTO.getName());
        course.setProfessor(professor);
        course.setStudents(new HashSet<>());

        // Guardar y mapear a DTO
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }


    @Transactional // Asegura que ambas partes de la relación se guarden
    public CourseWithStudentsDTO enrollStudent(Long courseId, EnrollmentDTO enrollmentDTO) {
        // Buscar el curso y el estudiante
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + enrollmentDTO.getStudentId()));

        // Regla de negocio: Asignar (lado del Curso)
        course.getStudents().add(student);

        // Regla de negocio: Mantener la relación bidireccional consistente (lado del Estudiante)
        // Esto es crucial en las relaciones @ManyToMany
        student.getCourses().add(course);

        studentRepository.save(student);
        Course updatedCourse = courseRepository.save(course);

        return courseMapper.toWithStudentsDto(updatedCourse);
    }


    public List<CourseDTO> findCoursesByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        return courseMapper.toDtoList(
                student.getCourses().stream().toList() // Convertir Set a List para el mapper
        );
    }
}
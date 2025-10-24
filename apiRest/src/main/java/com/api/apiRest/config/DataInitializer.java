package com.api.apiRest.config; // Crea este paquete

import com.api.apiRest.entity.Course;
import com.api.apiRest.entity.Professor;
import com.api.apiRest.entity.Student;
import com.api.apiRest.repository.CourseRepository;
import com.api.apiRest.repository.ProfessorRepository;
import com.api.apiRest.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
public class DataInitializer {

    // Spring ejecuta este método al iniciar la aplicación.
    @Bean
    public CommandLineRunner initDatabase(
            ProfessorRepository professorRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {

        return args -> {
            try {
                System.out.println("Iniciando la carga de datos de prueba...");

                // Evitar duplicados si ya hay datos
                if (professorRepository.count() > 0 || studentRepository.count() > 0 || courseRepository.count() > 0) {
                    System.out.println("Datos existentes detectados. Se omite la inicialización de datos.");
                    return;
                }

                // Crear Profesores
                Professor prof1 = new Professor();
                prof1.setName("Dr. Alan Turing");
                prof1.setEmail("alan.turing@api.com");
                professorRepository.save(prof1);

                Professor prof2 = new Professor();
                prof2.setName("Ing. Grace Hopper");
                prof2.setEmail("grace.hopper@api.com");
                professorRepository.save(prof2);

                System.out.println("Profesores creados.");

                // Crear Estudiantes
                Student std1 = new Student();
                std1.setName("Juan Perez");
                std1.setEnrollment("M-001");
                // Inicializar las listas de cursos para manejo bidireccional
                std1.setCourses(new HashSet<>());
                studentRepository.save(std1);

                Student std2 = new Student();
                std2.setName("Maria Lopez");
                std2.setEnrollment("M-002");
                std2.setCourses(new HashSet<>());
                studentRepository.save(std2);

                System.out.println("Estudiantes creados.");


                // Crear Curso y Asignar Profesor
                Course course1 = new Course();
                course1.setName("Algoritmos y Estructuras de Datos");
                course1.setProfessor(prof1); // Asignar al Dr. Turing
                course1.setStudents(new HashSet<>()); // Inicializar la lista de estudiantes


                course1.getStudents().add(std1);
                course1.getStudents().add(std2);

                std1.getCourses().add(course1);
                std2.getCourses().add(course1);

                courseRepository.save(course1);
                studentRepository.save(std1); // Guardar los estudiantes de nuevo para actualizar el ManyToMany
                studentRepository.save(std2);

                System.out.println("Curso creado y estudiantes asignados.");
                System.out.println("Carga de datos de prueba finalizada.");
            } catch (Exception e) {
                System.err.println("Error durante la inicialización de datos: " + e.getMessage());
            }
        };
    }
}
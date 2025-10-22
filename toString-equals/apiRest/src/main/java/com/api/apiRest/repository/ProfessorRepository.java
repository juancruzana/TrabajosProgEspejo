package com.api.apiRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apiRest.entity.Professor;


public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
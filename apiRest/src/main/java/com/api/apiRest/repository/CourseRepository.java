package com.api.apiRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apiRest.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}

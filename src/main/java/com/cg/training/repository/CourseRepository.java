package com.cg.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.training.entity.Courses;

public interface CourseRepository extends JpaRepository<Courses, Integer>{

	@Query("from Courses a where a.courseName=:courseName")
	public Courses findByCourseName(@Param("courseName")String courseName);

}

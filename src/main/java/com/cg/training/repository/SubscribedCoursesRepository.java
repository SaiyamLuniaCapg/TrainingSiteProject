package com.cg.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.training.entity.SubscribedCourses;

public interface SubscribedCoursesRepository extends JpaRepository<SubscribedCourses,String>{

}

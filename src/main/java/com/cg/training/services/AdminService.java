package com.cg.training.services;

import java.util.List;

import com.cg.training.entity.Courses;
import com.cg.training.entity.Customer;
import com.cg.training.entity.SubscribedCourses;
import com.cg.training.exceptions.IncorrectResourceDetailException;
import com.cg.training.exceptions.ResourceAlreadyExistException;
import com.cg.training.exceptions.ResourceNotFoundException;
import com.cg.training.models.CourseModel;

public interface AdminService {

	public Customer loginAdmin(String userName, double uniqueCode) throws IncorrectResourceDetailException;

	public CourseModel addCourse(CourseModel courseModel) throws ResourceAlreadyExistException;

	public List<Courses> getAllCourseDetails() throws ResourceNotFoundException;

	public Courses getCourseDetails(String courseName) throws ResourceNotFoundException;

	public void archieveCourseDetails(String courseName) throws ResourceNotFoundException;

	public Courses updateCourseDetails(CourseModel courseModel) throws ResourceNotFoundException;

	public List<SubscribedCourses> getCustomerCourseSubscription(String email) throws ResourceNotFoundException;
}

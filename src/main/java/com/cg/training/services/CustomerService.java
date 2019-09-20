package com.cg.training.services;

import java.util.List;

import com.cg.training.entity.Customer;
import com.cg.training.entity.SubscribedCourses;
import com.cg.training.exceptions.IncorrectResourceDetailException;
import com.cg.training.exceptions.ResourceAlreadyExistException;
import com.cg.training.exceptions.ResourceNotFoundException;
import com.cg.training.models.CourseModel;
import com.cg.training.models.CustomerModel;

public interface CustomerService {
	public boolean registerCustomer(CustomerModel customerModel)
			throws ResourceAlreadyExistException;

	public String getAccountStatus(String email, long mobileNo, String userName) throws ResourceNotFoundException, IncorrectResourceDetailException;

	public Customer loginCustomer(String email, double uniqueCode)
			throws ResourceNotFoundException, ResourceAlreadyExistException, IncorrectResourceDetailException;

	public List<CourseModel> getAllCourseDetails() throws ResourceNotFoundException;

	public boolean subscribeCourse(String email, String courseName)
			throws ResourceNotFoundException, ResourceAlreadyExistException;

	public List<SubscribedCourses> getAllSubscribeCourseDetails(String email)
			throws ResourceNotFoundException;

	public CustomerModel updateCustomerProfile(String email, CustomerModel customer) throws ResourceNotFoundException, ResourceAlreadyExistException;

}

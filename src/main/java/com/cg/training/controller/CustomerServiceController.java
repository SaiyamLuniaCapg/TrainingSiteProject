package com.cg.training.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.training.entity.Customer;
import com.cg.training.entity.SubscribedCourses;
import com.cg.training.exceptions.IncorrectResourceDetailException;
import com.cg.training.exceptions.ResourceAlreadyExistException;
import com.cg.training.exceptions.ResourceNotFoundException;
import com.cg.training.models.CourseModel;
import com.cg.training.models.CustomerModel;
import com.cg.training.responses.CustomResponse;
import com.cg.training.services.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "APIs for Customer", description = "Operations performed by Customer.", tags = { "APIs for Customer" })
@RequestMapping("/customer")
public class CustomerServiceController {

	private Customer customer;
	@Autowired
	private CustomerService customerService;

	@ApiOperation(value = "Customer can register from here.")
	@PostMapping(value = {
			"/register" }, headers = "Accept=application/json", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CustomResponse> registerCustomer(@Valid @RequestBody CustomerModel customerModel)
			throws ResourceAlreadyExistException {
		customerService.registerCustomer(customerModel);
		return new ResponseEntity<>(new CustomResponse("Request sent to admin for approval", 123), HttpStatus.OK);
	}

	@ApiOperation(value = "Customer can check his account status.")
	@GetMapping(value = {
			"/status/{email}/{mobileNo}/{userName}" }, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomResponse> checkAccountStatus(@PathVariable(value = "email") String email,
			@PathVariable(value = "mobileNo") long mobileNo, @PathVariable(value = "userName") String userName)
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		String customerAccountStatus = customerService.getAccountStatus(email, mobileNo, userName);
		return new ResponseEntity<>(new CustomResponse(customerAccountStatus, 123), HttpStatus.OK);
	}

	@ApiOperation(value = "Customer can login by entering valid credentials.")
	@GetMapping(value = "/login", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CustomResponse> loginCustomer(@RequestParam String email, @RequestParam double uniqueCode)
			throws ResourceNotFoundException, ResourceAlreadyExistException, IncorrectResourceDetailException {
		customer = customerService.loginCustomer(email, uniqueCode);
		return new ResponseEntity<>(new CustomResponse("Customer Login Success! Welcome "
				+ customer.getFirstName().toUpperCase() + " " + customer.getLastName().toUpperCase(), 123),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Customer can retrieve all available courses.")
	@GetMapping(value = "/courses/{email}", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CourseModel>> getAllCoursesDetails() throws ResourceNotFoundException {
		return new ResponseEntity<>(customerService.getAllCourseDetails(), HttpStatus.OK);
	}

	@ApiOperation(value = "Customer can subscribe to a course to get its content.")
	@PostMapping(value = "/courses/{email}/{courseName}", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomResponse> subscribeCourseToCustomer(@PathVariable(value = "email") String email,
			@PathVariable(value = "courseName") String courseName)
			throws ResourceNotFoundException, ResourceAlreadyExistException {
		customerService.subscribeCourse(email, courseName);
		return new ResponseEntity<>(new CustomResponse("Course subscribed successfully", 123), HttpStatus.OK);

	}

	@ApiOperation(value = "Customer can check his/her all subscribed courses.")
	@GetMapping(value = "/courses/subscribeList/{email}", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SubscribedCourses>> getAllSubscribeCoursesDetails(
			@PathVariable(value = "email") String email) throws ResourceNotFoundException {
		return new ResponseEntity<>(customerService.getAllSubscribeCourseDetails(email), HttpStatus.OK);
	}

	@ApiOperation(value = "Customer can update their personal details.")
	@PutMapping(value = "/profile/{email}", headers = "Accept=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomResponse> updateCustomerProfile(@PathVariable(value = "email") String email,
			@RequestBody CustomerModel customerModel) throws ResourceNotFoundException, ResourceAlreadyExistException {
		customerModel = customerService.updateCustomerProfile(email, customerModel);
		return new ResponseEntity<>(
				new CustomResponse("Profile Updated Successfully! New Profile Details: " + customerModel, 123),
				HttpStatus.OK);
	}
}

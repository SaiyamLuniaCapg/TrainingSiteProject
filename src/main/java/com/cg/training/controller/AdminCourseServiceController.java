package com.cg.training.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.training.entity.Courses;
import com.cg.training.entity.Customer;
import com.cg.training.entity.SubscribedCourses;
import com.cg.training.exceptions.IncorrectResourceDetailException;
import com.cg.training.exceptions.ResourceAlreadyExistException;
import com.cg.training.exceptions.ResourceNotFoundException;
import com.cg.training.models.CourseModel;
import com.cg.training.responses.CustomResponse;
import com.cg.training.services.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "APIs for Admin to login and control Course Database", description = "Operations performed by admin to handle Courses.", tags = {
		"APIs for Admin to login and control Course Database" })
@RequestMapping("/admin")
public class AdminCourseServiceController {

	@Autowired
	private AdminService adminService;

	@ApiOperation(value = "Login Admin with correct credentials.")
	@PostMapping(value = "/login", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomResponse> loginUser(@RequestParam String userName, @RequestParam double password)
			throws IncorrectResourceDetailException {
		Customer admin = adminService.loginAdmin(userName, password);
		return new ResponseEntity<>(new CustomResponse("Admin Login Success! Welcome " + admin.getUserName(), 123),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Add course to database.")
	@PostMapping(value = "/courses", headers = "Accept=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomResponse> addCourseDetails(
			@ApiParam(value = "Course object store in database table", required = true) @Valid @RequestBody CourseModel courseModel)
			throws ResourceAlreadyExistException, ResourceNotFoundException {
		courseModel = adminService.addCourse(courseModel);
		return new ResponseEntity<>(
				new CustomResponse("Course Added Successfully with courseName " + courseModel.getCourseName(), 123),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Get all the courses from the database", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/courses", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Courses>> getAllCourseDetails() throws ResourceNotFoundException {
		return new ResponseEntity<>(adminService.getAllCourseDetails(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get a course by Course Name")
	@GetMapping(value = {
			"/courses/{courseName}" }, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Courses> getCourseDetails(
			@ApiParam(value = "Course name from which course object will be retrieved", required = true) @PathVariable(value = "courseName") String courseName)
			throws ResourceNotFoundException {
		Courses course = adminService.getCourseDetails(courseName);
		return new ResponseEntity<>(course, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a course by Course Name")
	@DeleteMapping(value = { "/courses/{courseName}" })
	public ResponseEntity<CustomResponse> removeCourseDetails(
			@ApiParam(value = "Course Name from which course object will be deleted from database table", required = true) @PathVariable(value = "courseName") String courseName)
			throws ResourceNotFoundException {
		adminService.archieveCourseDetails(courseName);
		return new ResponseEntity<>(new CustomResponse("Course Details successfully removed", 123), HttpStatus.OK);

	}

	@ApiOperation(value = "Update a existing course")
	@PutMapping(value = "/courses", headers = "Accept=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomResponse> updateCourseDetails(@RequestBody CourseModel courseModel)
			throws ResourceNotFoundException {
		Courses course = adminService.updateCourseDetails(courseModel);
		return new ResponseEntity<>(
				new CustomResponse("Course Updated Successfully! New Course Details: " + course, 123), HttpStatus.OK);
	}

	@ApiOperation(value = "Get Report on Customer-Course Subscription by entering valid EmailId or Course Name.", response = List.class)
	@GetMapping(value = "/courseSubscription/{id}", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SubscribedCourses>> getCustomerCourseSubscription(
			@ApiParam(value = "Enter a valid Email ID or Course Name", required = true) @PathVariable(value = "id") String id)
			throws ResourceNotFoundException {
		return new ResponseEntity<>(adminService.getCustomerCourseSubscription(id), HttpStatus.OK);
	}
}

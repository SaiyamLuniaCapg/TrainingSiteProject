package com.cg.training.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.training.TrainingApplication;
import com.cg.training.entity.Courses;
import com.cg.training.entity.Customer;
import com.cg.training.entity.SubscribedCourses;
import com.cg.training.exceptions.IncorrectResourceDetailException;
import com.cg.training.exceptions.ResourceAlreadyExistException;
import com.cg.training.exceptions.ResourceNotFoundException;
import com.cg.training.models.CourseModel;
import com.cg.training.repository.CourseRepository;
import com.cg.training.repository.CustomerRepository;
import com.cg.training.repository.SubscribedCoursesRepository;

@Component("adminService")
//@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private SubscribedCoursesRepository subscribedCoursesRepository;
	private static final Logger logger = LoggerFactory.getLogger(TrainingApplication.class);
	private Courses course;

	public AdminServiceImpl(CourseRepository courseRepository, CustomerRepository customerRepository,
			SubscribedCoursesRepository subscribedCoursesRepository) {
		super();
		this.courseRepository = courseRepository;
		this.customerRepository = customerRepository;
		this.subscribedCoursesRepository = subscribedCoursesRepository;
	}

	@Override
	public Customer loginAdmin(String userName, double uniqueCode) throws IncorrectResourceDetailException {
		logger.info("Admin Login");
		Customer customer = customerRepository
				.findAll().stream().filter(cust -> cust.getUserName().equals(userName)
						&& cust.getUniqueCode() == uniqueCode && cust.getCustomerAccountStatus().equals("ADMIN"))
				.findAny().orElse(null);
		if (customer == null) {
			customerRepository.findAll().stream().forEach(cust -> {
				cust.setAccountAccess(false);
				customerRepository.save(cust);
			});
			throw new IncorrectResourceDetailException("Authentication Failed.");
		}
		customer.setAccountAccess(true);
		customerRepository.save(customer);
		return customer;
	}

	@Override
	public CourseModel addCourse(CourseModel courseModel)
			throws ResourceAlreadyExistException, ResourceNotFoundException {
		if (!isValidAdmin())
			throw new ResourceNotFoundException("Admin need to login first.");
		if (courseRepository.findByCourseName(courseModel.getCourseName()) != null)
			throw new ResourceAlreadyExistException("Course Name Already Exist");
		course = new Courses(courseModel.getCourseName(), courseModel.getCoursePlatform(), courseModel.getCoursePrice(),
				1, true);
		courseRepository.save(course);
		courseModel = new DozerBeanMapper().map(course, CourseModel.class);
		return courseModel;
	}

	@Override
	public List<Courses> getAllCourseDetails() throws ResourceNotFoundException {
		if (!isValidAdmin())
			throw new ResourceNotFoundException("Admin need to login first.");
		List<Courses> courseList = courseRepository.findAll().stream().filter(c -> c.isCourseStatus() == true)
				.collect(Collectors.toList());
		if (courseList.isEmpty())
			throw new ResourceNotFoundException("No Course is added right now");
		else
			return courseList;
	}

	@Override
	public Courses getCourseDetails(String courseName) throws ResourceNotFoundException {
		if (!isValidAdmin())
			throw new ResourceNotFoundException("Admin need to login first.");
		course = courseRepository.findAll().stream()
				.filter(courses -> courses.isCourseStatus() && courses.getCourseName().equals(courseName)).findAny()
				.orElse(null);
		if (course != null)
			return course;
		else
			throw new ResourceNotFoundException("No such course exist with Course Name " + courseName);
	}

	@Override
	public void archieveCourseDetails(String courseName) throws ResourceNotFoundException {
		if (!isValidAdmin())
			throw new ResourceNotFoundException("Admin need to login first.");
		course = getCourseDetails(courseName);
		course.setCourseStatus(false);
		courseRepository.save(course);
	}

	@Override
	public Courses updateCourseDetails(CourseModel courseModel) throws ResourceNotFoundException {
		if (!isValidAdmin())
			throw new ResourceNotFoundException("Admin need to login first.");
		Courses previousCourse = getCourseDetails(courseModel.getCourseName());
		archieveCourseDetails(courseModel.getCourseName());
		course = new DozerBeanMapper().map(courseModel, Courses.class);
		course.setCourseStatus(true);
		course.setCourseVersion(previousCourse.getCourseVersion() + 1);
		courseRepository.save(course);
		return course;
	}

	@Override
	public List<SubscribedCourses> getCustomerCourseSubscription(String id) throws ResourceNotFoundException {
		if (!isValidAdmin())
			throw new ResourceNotFoundException("Admin need to login first.");
		Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
		Matcher mat = pattern.matcher(id);
		if (mat.matches()) {
			customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Email ID is invalid."));
			List<SubscribedCourses> subscribedCourses = subscribedCoursesRepository.findAll().stream()
					.filter(c -> c.getEmail().equals(id)).collect(Collectors.toList());
			if (subscribedCourses.isEmpty())
				throw new ResourceNotFoundException("No Course is subcribed by this Customer");
			return subscribedCourses;
		}
		if (courseRepository.findByCourseName(id) == null)
			throw new ResourceNotFoundException("Course Not Found.");
		List<SubscribedCourses> subscribedCourses = subscribedCoursesRepository.findAll().stream()
				.filter(c -> c.getCourseName().equals(id)).collect(Collectors.toList());
		if (subscribedCourses.isEmpty())
			throw new ResourceNotFoundException("No Customer has subscribed this course");
		return subscribedCourses;
	}

	public boolean isValidAdmin() {
		Customer admin = customerRepository.findAll().stream()
				.filter(cust -> cust.getCustomerAccountStatus().equals("ADMIN")).findAny().orElse(null);
		if (admin.isAccountAccess())
			return true;
		return false;
	}
}

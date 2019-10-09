package com.cg.training.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.training.entity.Courses;
import com.cg.training.entity.Customer;
import com.cg.training.entity.SubscribedCourses;
import com.cg.training.exceptions.IncorrectResourceDetailException;
import com.cg.training.exceptions.ResourceAlreadyExistException;
import com.cg.training.exceptions.ResourceNotFoundException;
import com.cg.training.models.CourseModel;
import com.cg.training.models.CustomerModel;
import com.cg.training.repository.CourseRepository;
import com.cg.training.repository.CustomerRepository;
import com.cg.training.repository.SubscribedCoursesRepository;

@Component("customerService")
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private SubscribedCoursesRepository subscribedCoursesRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository, CourseRepository courseRepository,
			SubscribedCoursesRepository subscribedCoursesRepository) {
		this.customerRepository = customerRepository;
		this.courseRepository = courseRepository;
		this.subscribedCoursesRepository = subscribedCoursesRepository;
	}

	@Override
	public boolean registerCustomer(CustomerModel customerModel) throws ResourceAlreadyExistException {
		if (findCustomerProfileEmail(customerModel.getEmail()) != null)
			throw new ResourceAlreadyExistException("EmailID already used with another Customer.");
		if (findCustomerProfileMobile(customerModel.getMobileNo()) != null)
			throw new ResourceAlreadyExistException("Mobile No already used with another Customer.");
		if (findCustomerProfileUserName(customerModel.getUserName()) != null)
			throw new ResourceAlreadyExistException("Username is already taken");
		customerRepository.save(new Customer(customerModel.getFirstName(), customerModel.getLastName(),
				customerModel.getUserName(), customerModel.getMobileNo(), customerModel.getEmail(), "PENDING"));
		return true;
	}

	public Customer findCustomerProfileEmail(String email) {
		return customerRepository.findCustomerByEmail(email);
	}

	public Customer findCustomerProfileMobile(long mobileNo) {
		return customerRepository.findCustomerByMobile(mobileNo);
	}

	public Customer findCustomerProfileUserName(String userName) {
		return customerRepository.findUserByCustomerUserName(userName);
	}

	@Override
	public String getAccountStatus(String email, long mobileNo, String userName)
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		String customerAccountStatus = null;
		Customer customer = customerRepository.findById(email)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		if ((customer.getMobileNo() != mobileNo) || (!customer.getUserName().equals(userName)))
			throw new IncorrectResourceDetailException("Authentication Failed.");
		if (customer.getCustomerAccountStatus().equals("PENDING"))
			customerAccountStatus = "Account is not yet verified.";
		if (customer.getCustomerAccountStatus().equals("DISABLE"))
			customerAccountStatus = "Account is temporarily blocked.";
		if (customer.getCustomerAccountStatus().equals("ENABLE"))
			customerAccountStatus = "Account is running successfully.";
		if (customer.getCustomerAccountStatus().equals("APPROVE")) {
			customer.setCustomerAccountStatus("ENABLE");
			customerAccountStatus = "Account Verification is done. Your unique key is " + (int) customer.getUniqueCode()
					+ ". [This is a one time message]";
		}
		customerRepository.save(customer);
		if (customer.getCustomerAccountStatus().equals("REJECT")) {
			customerRepository.delete(customer);
			customerAccountStatus = "Account Registration is declined by Admin. [This is a one time message]";
		}
		return customerAccountStatus;
	}

	@Override
	public Customer loginCustomer(String email, double uniqueCode)
			throws ResourceNotFoundException, ResourceAlreadyExistException, IncorrectResourceDetailException {
		Customer customer = customerRepository.findById(email)
				.orElseThrow(() -> new ResourceNotFoundException("Authentication Failed."));
		if (customer.getCustomerAccountStatus().equals("ENABLE")) {
			if (customer.getUniqueCode() == uniqueCode)
				return customer;
			else
				throw new IncorrectResourceDetailException("Authentication Failed.");
		} else if (customer.getCustomerAccountStatus().equals("PENDING"))
			throw new IncorrectResourceDetailException("Account is not yet verified.");
		else
			throw new ResourceAlreadyExistException("Your Account is currently disabled. Please try later.");
	}

	@Override
	public List<CourseModel> getAllCourseDetails() throws ResourceNotFoundException {
		List<Courses> courseList = courseRepository.findAll().stream().filter(course -> course.isCourseStatus() == true)
				.collect(Collectors.toList());
		List<CourseModel> courseModelList = new ArrayList<>();
		for (Courses course : courseList) {
			courseModelList.add(new DozerBeanMapper().map(course, CourseModel.class));
		}
		if (courseModelList.isEmpty())
			throw new ResourceNotFoundException("No Courses is available right now");
		else
			return courseModelList;
	}

	@Override
	public boolean subscribeCourse(String email, String courseName)
			throws ResourceNotFoundException, ResourceAlreadyExistException {
		customerRepository.findById(email).orElseThrow(() -> new ResourceNotFoundException("EmailId is invalid."));
		if (courseRepository.findByCourseName(courseName) == null)
			throw new ResourceNotFoundException("Course Name is invalid.");
		List<SubscribedCourses> subscribedCourses = new ArrayList<>();
		subscribedCourses = subscribedCoursesRepository.findAll();
		for (SubscribedCourses sc : subscribedCourses) {
			if (sc.getEmail().equals(email) && sc.getCourseName().equals(courseName))
				throw new ResourceAlreadyExistException("Course is already subscribed.");
		}
		subscribedCoursesRepository.save(new SubscribedCourses(email, courseName));
		return true;

	}

	@Override
	public List<SubscribedCourses> getAllSubscribeCourseDetails(String email) throws ResourceNotFoundException {
		customerRepository.findById(email).orElseThrow(() -> new ResourceNotFoundException("EmailId is invalid."));
		List<SubscribedCourses> subscribedCourses = subscribedCoursesRepository.findAll().stream()
				.filter(c -> c.getEmail().equals(email)).collect(Collectors.toList());
		if (subscribedCourses.isEmpty())
			throw new ResourceNotFoundException("No Course is subscribed yet");
		return subscribedCourses;
	}

	@Override
	public CustomerModel updateCustomerProfile(String email, CustomerModel customerModel)
			throws ResourceNotFoundException, ResourceAlreadyExistException {
		Customer oldCustomer = customerRepository.findById(email)
				.orElseThrow(() -> new ResourceNotFoundException("Authentication Failed."));
		List<Customer> customerList = customerRepository.findAll().stream()
				.filter(customer -> customer.getEmail().equals(customerModel.getEmail())
						|| customer.getMobileNo() == customerModel.getMobileNo()
						|| customer.getUserName().equals(customerModel.getUserName()))
				.collect(Collectors.toList());
		if (customerList.size() == 1) {
			oldCustomer.setEmail(customerModel.getEmail());
			oldCustomer.setFirstName(customerModel.getFirstName());
			oldCustomer.setLastName(customerModel.getLastName());
			oldCustomer.setUserName(customerModel.getUserName());
			oldCustomer.setMobileNo(customerModel.getMobileNo());
			customerRepository.save(oldCustomer);
			return new DozerBeanMapper().map(oldCustomer, CustomerModel.class);

		} else
			throw new ResourceAlreadyExistException("Details already exist with different customer. Try again!");
	}
}

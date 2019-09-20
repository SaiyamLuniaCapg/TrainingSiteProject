package com.cg.training.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

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
import com.cg.training.services.CustomerService;
import com.cg.training.services.CustomerServiceImpl;

public class CustomerServiceTest {
	private static CustomerService customerService;
	private static CustomerRepository mockCustomerRepository = mock(CustomerRepository.class);
	private static CourseRepository mockCourseRepository = mock(CourseRepository.class);
	private static SubscribedCoursesRepository mockSubscribedCoursesRepository = mock(
			SubscribedCoursesRepository.class);
	List<Customer> customerList = new ArrayList<>();
	List<Courses> courseList = new ArrayList<>();
	List<Courses> emptyCourseList = new ArrayList<>();
	List<CourseModel> courseModelList = new ArrayList<>();
	List<SubscribedCourses> subscribedCoursesList = new ArrayList<>();
//	List<CourseModel> courseModelEmptyList = new ArrayList<>();

	@BeforeClass
	public static void setUpTestEnv() {
		customerService = new CustomerServiceImpl(mockCustomerRepository, mockCourseRepository,
				mockSubscribedCoursesRepository);
	}

	@Before
	public void setUpTestData() {
		MockitoAnnotations.initMocks(this);
		Customer customer1 = new Customer("Saiyam", "Lunia", "salunia", 8981273780L, "saiyam@gmail.com", "ENABLE",
				123789);
		Customer customer2 = new Customer("Sayan", "Datta", "saydat", 3265273780L, "sayan@gmail.com", "PENDING",
				876785);
		Customer customer3 = new Customer("Saptarshi", "Das", "saptdas", 7899473780L, "saptarshi@gmail.com", "DISABLE",
				234523);
		Customer customer4 = new Customer("Swastik", "Bhatt", "sawbhat", 8981212455L, "swastik@gmail.com", "APPROVE",
				463453);
		Customer customer5 = new Customer("Shyam", "Kumar", "shymar", 7418529630L, "shyam@gmail.com", "REJECT", 463453);
		customerList.add(customer1);
		customerList.add(customer2);
		customerList.add(customer3);
		customerList.add(customer4);
		customerList.add(customer5);
		when(mockCustomerRepository.findAll()).thenReturn(customerList);
		when(mockCustomerRepository.findById("saiyam@gmail.com")).thenReturn(Optional.of(customer1));
		when(mockCustomerRepository.findById("sayan@gmail.com")).thenReturn(Optional.of(customer2));
		when(mockCustomerRepository.findById("saptarshi@gmail.com")).thenReturn(Optional.of(customer3));
		when(mockCustomerRepository.findById("swastik@gmail.com")).thenReturn(Optional.of(customer4));
		when(mockCustomerRepository.findById("shyam@gmail.com")).thenReturn(Optional.of(customer5));
		when(mockCustomerRepository.findCustomerByEmail("saiyam@gmail.com")).thenReturn(customer1);
		when(mockCustomerRepository.findCustomerByMobile(8981273780L)).thenReturn(customer1);
		when(mockCustomerRepository.findUserByCustomerUserName("salunia")).thenReturn(customer1);

		Courses course1 = new Courses("Angular", "Classroom", 450, 1, true);
		Courses course2 = new Courses("Postman", "Online", 700, 1, true);
		Courses course3 = new Courses("Javascript", "Classroom", 200, 1, false);
		Courses course4 = new Courses("Spring Boot", "Online", 1000, 1, true);
		Courses course5 = new Courses("Javascript", "Online", 450, 2, true);
		courseList.add(course1);
		courseList.add(course2);
		courseList.add(course3);
		courseList.add(course4);
		courseList.add(course5);
		when(mockCourseRepository.findByCourseName("Angular")).thenReturn(course1);
		when(mockCourseRepository.findByCourseName("Spring Boot")).thenReturn(course4);
		when(mockCourseRepository.findByCourseName("Javascript")).thenReturn(course5);
		when(mockCourseRepository.findAll()).thenReturn(courseList);

		CourseModel courseModel1 = new CourseModel("Angular", "Classroom", 450);
		CourseModel courseModel2 = new CourseModel("Postman", "Online", 700);
		CourseModel courseModel3 = new CourseModel("Javascript", "Classroom", 200);
		CourseModel courseModel4 = new CourseModel("Spring Boot", "Online", 1000);
		courseModelList.add(courseModel1);
		courseModelList.add(courseModel2);
		courseModelList.add(courseModel3);
		courseModelList.add(courseModel4);

		SubscribedCourses subscribedCourses1 = new SubscribedCourses("saiyam@gmail.com", "Javascript");
		SubscribedCourses subscribedCourses2 = new SubscribedCourses("saiyam@gmail.com", "Angular");
		SubscribedCourses subscribedCourses3 = new SubscribedCourses("saptarshi@gmail.com", "Spring Boot");
		subscribedCoursesList.add(subscribedCourses1);
		subscribedCoursesList.add(subscribedCourses2);
		subscribedCoursesList.add(subscribedCourses3);
		when(mockSubscribedCoursesRepository.findAll()).thenReturn(subscribedCoursesList);
	}

	@Test
	public void testRegisterCustomer_forUniqueValues() throws ResourceAlreadyExistException {
		boolean actual = customerService
				.registerCustomer(new CustomerModel("Harsimean", "Bedi", "harbedi", 9874563210L, "harbedi@gmail.com"));
		assertEquals(true, actual);
	}

	@Test(expected = ResourceAlreadyExistException.class)
	public void testRegisterCustomer_forExistingEmailID_toGetException() throws ResourceAlreadyExistException {
		customerService
				.registerCustomer(new CustomerModel("Harsimean", "Bedi", "harbedi", 9874563210L, "saiyam@gmail.com"));
	}

	@Test(expected = ResourceAlreadyExistException.class)
	public void testRegisterCustomer_forExistingMobile_toGetException() throws ResourceAlreadyExistException {
		customerService
				.registerCustomer(new CustomerModel("Harsimean", "Bedi", "harbedi", 8981273780L, "harbedi@gmail.com"));
	}

	@Test(expected = ResourceAlreadyExistException.class)
	public void testRegisterCustomer_forExistingUsername_toGetException() throws ResourceAlreadyExistException {
		customerService
				.registerCustomer(new CustomerModel("Harsimean", "Bedi", "salunia", 9874563210L, "harbedi@gmail.com"));
	}

	@Test
	public void testGetAccountStatus_forValidCredential_forActiveAccount()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		String expectedMessage = "Account is running successfully.";
		String actualMessage = customerService.getAccountStatus("saiyam@gmail.com", 8981273780L, "salunia");
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testGetAccountStatus_forValidCredential_forBlockAccount()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		String expectedMessage = "Account is temporarily blocked.";
		String actualMessage = customerService.getAccountStatus("saptarshi@gmail.com", 7899473780L, "saptdas");
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testGetAccountStatus_forValidCredential_forPendingAccount()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		String expectedMessage = "Account is not yet verified.";
		String actualMessage = customerService.getAccountStatus("sayan@gmail.com", 3265273780L, "saydat");
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testGetAccountStatus_forValidCredential_forRejectAccount()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		String expectedMessage = "Account Registration is declined by Admin. [This is a one time message]";
		String actualMessage = customerService.getAccountStatus("shyam@gmail.com", 7418529630L, "shymar");
		assertEquals(expectedMessage, actualMessage);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetAccountStatus_forInvalidEmailID_toGetException()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		customerService.getAccountStatus("fake@gmail.com", 7897897890L, "qwerty");
	}

	@Test(expected = IncorrectResourceDetailException.class)
	public void testGetAccountStatus_forInvalidMobile_toGetException()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		customerService.getAccountStatus("saiyam@gmail.com", 7897897890L, "salunia");
	}

	@Test(expected = IncorrectResourceDetailException.class)
	public void testGetAccountStatus_forInvalidUsername_toGetException()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		customerService.getAccountStatus("saiyam@gmail.com", 8981273780L, "qwerty");
	}

	@Test
	public void testLoginCustomer_forValidCredential_EnableAccount()
			throws ResourceNotFoundException, ResourceAlreadyExistException, IncorrectResourceDetailException {
		Customer expectedCustomer = new Customer("Saiyam", "Lunia", "salunia", 8981273780L, "saiyam@gmail.com",
				"ENABLE", 123789);
		Customer actualCustomer = customerService.loginCustomer("saiyam@gmail.com", 123789);
		assertEquals(expectedCustomer, actualCustomer);
	}

	@Test(expected = IncorrectResourceDetailException.class)
	public void testLoginCustomer_forIncorrectUniqueKey_EnableAccount()
			throws ResourceNotFoundException, ResourceAlreadyExistException, IncorrectResourceDetailException {
		customerService.loginCustomer("saiyam@gmail.com", 456963);
	}

	@Test(expected = IncorrectResourceDetailException.class)
	public void testLoginCustomer_forValidCredential_PendingAccount()
			throws ResourceNotFoundException, ResourceAlreadyExistException, IncorrectResourceDetailException {
		customerService.loginCustomer("sayan@gmail.com", 876785);
	}

	@Test(expected = ResourceAlreadyExistException.class)
	public void testLoginCustomer_forValidCredential_DisableAccount()
			throws ResourceNotFoundException, ResourceAlreadyExistException, IncorrectResourceDetailException {
		customerService.loginCustomer("saptarshi@gmail.com", 234523);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testLoginCustomer_forInvalidEmailID()
			throws ResourceNotFoundException, ResourceAlreadyExistException, IncorrectResourceDetailException {
		customerService.loginCustomer("fake@gmail.com", 456963);
	}

	@Test
	public void testGetAllCourseDetails() throws ResourceNotFoundException {
		List<CourseModel> actualModelCourseList = customerService.getAllCourseDetails();
		assertEquals(4, actualModelCourseList.size());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetAllCourseDetails_toGetException() throws ResourceNotFoundException {
		when(mockCourseRepository.findAll()).thenReturn(emptyCourseList);
		customerService.getAllCourseDetails();
	}

	@Test
	public void testSubscribeCourse_forValidParameters()
			throws ResourceNotFoundException, ResourceAlreadyExistException {
		boolean actualResult = customerService.subscribeCourse("saiyam@gmail.com", "Spring Boot");
		assertEquals(true, actualResult);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testSubscribeCourse_forInvalidEmailID_toGetException()
			throws ResourceNotFoundException, ResourceAlreadyExistException {
		customerService.subscribeCourse("fakeId@gmail.com", "Spring Boot");
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testSubscribeCourse_forInvalidCourseName_toGetException()
			throws ResourceNotFoundException, ResourceAlreadyExistException {
		customerService.subscribeCourse("saiyam@gmail.com", "Some Course Name");
	}

	@Test(expected = ResourceAlreadyExistException.class)
	public void testSubscribeCourse_forAlreadySubscribedCourse_toGetException()
			throws ResourceNotFoundException, ResourceAlreadyExistException {
		customerService.subscribeCourse("saiyam@gmail.com", "Angular");
	}

	@Test
	public void testGetAllSubscribeCourseDetails_forValidCustomer() throws ResourceNotFoundException {
		List<SubscribedCourses> actualSubscribedCourseList = customerService
				.getAllSubscribeCourseDetails("saiyam@gmail.com");
		assertEquals(2, actualSubscribedCourseList.size());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetAllSubscribeCourseDetails_forValidCustomer_butNoSubscription_toGetException()
			throws ResourceNotFoundException {
		customerService.getAllSubscribeCourseDetails("swastik@gmail.com");
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testGetAllSubscribeCourseDetails_forInvalidEmailID_toGetException()
			throws ResourceNotFoundException {
		customerService.getAllSubscribeCourseDetails("fakeID@gmail.com");
	}
}

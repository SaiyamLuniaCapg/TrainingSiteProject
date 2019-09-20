package com.cg.training.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.cg.training.repository.CourseRepository;
import com.cg.training.repository.CustomerRepository;
import com.cg.training.repository.SubscribedCoursesRepository;
import com.cg.training.services.AdminService;
import com.cg.training.services.AdminServiceImpl;

public class AdminServiceTest {
	private static AdminService adminService;
	private static CourseRepository mockCourseRepository = mock(CourseRepository.class);
	private static CustomerRepository mockCustomerRepository = mock(CustomerRepository.class);
	private static SubscribedCoursesRepository mockSubscribedCoursesRepository = mock(
			SubscribedCoursesRepository.class);
	List<Customer> customerList = new ArrayList<>();
	List<Courses> courseList = new ArrayList<>();
	List<Courses> emptyCourseList = new ArrayList<>();
	List<SubscribedCourses> subscribedCoursesList = new ArrayList<>();

	@BeforeClass
	public static void setUpTestEnv() {
		adminService = new AdminServiceImpl(mockCourseRepository, mockCustomerRepository,
				mockSubscribedCoursesRepository);
	}

	@Before
	public void setUpTestData() {
		MockitoAnnotations.initMocks(this);
		Customer customer1 = new Customer("Saiyam", "Lunia", "salunia", 8981273780L, "saiyam@gmail.com", "ADMIN",
				123789);
		Customer customer2 = new Customer("Swastik", "Bhatt", "swabha", 8981123654L, "swastik@gmail.com", "PENDING", 0);
		Customer customer3 = new Customer("Sayan", "Datta", "saydat", 7885273780L, "sayan@gmail.com", "ENABLE", 123654);
		Customer customer4 = new Customer("Saptarshi", "Das", "sapdas", 7856063780L, "saptarshi@gmail.com", "ENABLE",
				123654);
		customerList.add(customer1);
		customerList.add(customer2);
		customerList.add(customer3);
		customerList.add(customer4);
		when(mockCustomerRepository.findAll()).thenReturn(customerList);
		when(mockCustomerRepository.findById("swastik@gmail.com")).thenReturn(Optional.of(customer2));
		when(mockCustomerRepository.findById("saptarshi@gmail.com")).thenReturn(Optional.of(customer4));

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
		when(mockCourseRepository.findByCourseName("Postman")).thenReturn(course2);
		when(mockCourseRepository.findAll()).thenReturn(courseList);

		SubscribedCourses subscribedCourses1 = new SubscribedCourses("swastik@gmail.com", "Javascript");
		SubscribedCourses subscribedCourses2 = new SubscribedCourses("swastik@gmail.com", "Angular");
		SubscribedCourses subscribedCourses3 = new SubscribedCourses("swastik@gmail.com", "Spring Boot");
		SubscribedCourses subscribedCourses4 = new SubscribedCourses("sayan@gmail.com", "Javascript");
		subscribedCoursesList.add(subscribedCourses1);
		subscribedCoursesList.add(subscribedCourses2);
		subscribedCoursesList.add(subscribedCourses3);
		subscribedCoursesList.add(subscribedCourses4);
		when(mockSubscribedCoursesRepository.findAll()).thenReturn(subscribedCoursesList);
	}

	@Test
	public void testLoginAdmin_forValidCredential_toGetAdmin() throws IncorrectResourceDetailException {
		Customer expectedCustomer = new Customer("Saiyam", "Lunia", "salunia", 8981273780L, "saiyam@gmail.com", "ADMIN",
				123789);
		Customer actualCustomer = adminService.loginAdmin("salunia", 123789);
		assertEquals(expectedCustomer, actualCustomer);
	}

	@Test(expected = IncorrectResourceDetailException.class)
	public void testLoginAdmin_forInvalidPassword_toGetException() throws IncorrectResourceDetailException {
		adminService.loginAdmin("salunia", 789456);
	}

	@Test(expected = IncorrectResourceDetailException.class)
	public void testLoginAdmin_forInvalidUsername_toGetException() throws IncorrectResourceDetailException {
		adminService.loginAdmin("fakename", 123789);
	}

	@Test
	public void testAddCourse_forUniqueCoursename_toGetCourseModel() throws ResourceAlreadyExistException {
		CourseModel expectedCourseModel = new CourseModel("Aws", "Online", 450);
		CourseModel actualCourseModel = adminService.addCourse(new CourseModel("Aws", "Online", 450));
		assertEquals(expectedCourseModel, actualCourseModel);
		verify(mockCourseRepository, times(1)).save(new Courses("Aws", "Online", 450, 1, true));
	}

	@Test(expected = ResourceAlreadyExistException.class)
	public void testAddCourse_forDuplicateCoursename_toGetException() throws ResourceAlreadyExistException {
		adminService.addCourse(new CourseModel("Angular", "Classroom", 450));
	}

	@Test
	public void testGetAllCourseDetails_toGetList() throws ResourceNotFoundException {
		List<Courses> actualCourseList = adminService.getAllCourseDetails();
		assertEquals(4, actualCourseList.size());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetAllCourseDetails_toGetException() throws ResourceNotFoundException {
		when(mockCourseRepository.findAll()).thenReturn(emptyCourseList);
		adminService.getAllCourseDetails();
	}

	@Test
	public void testGetCourseDetails_forValidCoursename_toGetCourse() throws ResourceNotFoundException {
		Courses expectedCourse = new Courses("Angular", "Classroom", 450, 1, true);
		Courses actualCourse = adminService.getCourseDetails("Angular");
		assertEquals(expectedCourse, actualCourse);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetCourseDetails_forInvalidCoursename_toGetException() throws ResourceNotFoundException {
		adminService.getCourseDetails("Some Course Name");
	}

	@Test
	public void testArchieveCourseDetails_forValidCoursename_toChangeCourseStatus() throws ResourceNotFoundException {
		adminService.archieveCourseDetails("Angular");
		verify(mockCourseRepository, times(1)).save(new Courses("Angular", "Classroom", 450, 1, false));
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testArchieveCourseDetails_forInvalidCoursename_toGetException() throws ResourceNotFoundException {
		adminService.archieveCourseDetails("Some Course Name");
	}

	@Test
	public void testUpdateCourseDetails_forExistingCoursename_toGetUpdatedCourse() throws ResourceNotFoundException {
		Courses expectedCourse = new Courses("Spring Boot", "Classroom", 500, 2, true);
		Courses actualCourse = adminService.updateCourseDetails(new CourseModel("Spring Boot", "Classroom", 500));
		assertEquals(expectedCourse, actualCourse);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateCourseDetails_forNewCoursename_toGetException() throws ResourceNotFoundException {
		adminService.updateCourseDetails(new CourseModel("Fake Name", "Online", 1000));
	}

	@Test
	public void testGetCustomerCourseSubscription_forValidEmailID_toGetList() throws ResourceNotFoundException {
		List<SubscribedCourses> actualSubscribedCourseList = adminService
				.getCustomerCourseSubscription("swastik@gmail.com");
		assertEquals(3, actualSubscribedCourseList.size());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetCustomerCourseSubscription_forInvalidEmailID_toGetException() throws ResourceNotFoundException {
		adminService.getCustomerCourseSubscription("fakeUser@gmail.com");
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetCustomerCourseSubscription_forValidEmailID_toGetEmptyListException()
			throws ResourceNotFoundException {
		adminService.getCustomerCourseSubscription("saptarshi@gmail.com");
	}

	@Test
	public void testGetCustomerCourseSubscription_forValidCoursename_toGetList() throws ResourceNotFoundException {
		List<SubscribedCourses> actualSubscribedCourseList = adminService.getCustomerCourseSubscription("Javascript");
		assertEquals(2, actualSubscribedCourseList.size());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetCustomerCourseSubscription_forInvalidCoursename_toGetException()
			throws ResourceNotFoundException {
		adminService.getCustomerCourseSubscription("Fake Name");
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetCustomerCourseSubscription_forValidCoursename_toGetEmptyListException()
			throws ResourceNotFoundException {
		adminService.getCustomerCourseSubscription("Postman");
	}
}

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

import com.cg.training.entity.Customer;
import com.cg.training.exceptions.IncorrectResourceDetailException;
import com.cg.training.exceptions.ResourceNotFoundException;
import com.cg.training.models.CustomerModel;
import com.cg.training.repository.CustomerRepository;
import com.cg.training.services.CustomerHandleService;
import com.cg.training.services.CustomerHandleServiceImpl;

public class CustomerHandleServiceTest {

	private static CustomerHandleService customerHandleService;
	private static CustomerRepository mockCustomerRepository = mock(CustomerRepository.class);
	List<Customer> customerList = new ArrayList<>();
	List<Customer> emptyCustomerList = new ArrayList<>();

	@BeforeClass
	public static void setUpTestEnv() {
		customerHandleService = new CustomerHandleServiceImpl(mockCustomerRepository);
	}

	@Before
	public void setUpTestData() {
		MockitoAnnotations.initMocks(this);
		Customer customer1 = new Customer("Saiyam", "Lunia", "salunia", 8981273780L, "saiyam@gmail.com", "PENDING", 0);
		Customer customer2 = new Customer("Swastik", "Bhatt", "swabha", 8981123654L, "swastik@gmail.com", "PENDING", 0);
		Customer customer3 = new Customer("Sayan", "Datta", "saydat", 7885273780L, "sayan@gmail.com", "ENABLE", 123654);
		Customer customer4 = new Customer("Saptarshi", "Das", "saptdas", 6542273870L, "saptarshi@gmail.com", "DISABLE",
				987456);
		Customer customer5 = new Customer("Harsimran", "Bedi", "harbedi", 7956232780L, "bedi@gmail.com", "REJECT", 0);
		customerList.add(customer1);
		customerList.add(customer2);
		customerList.add(customer3);
		customerList.add(customer4);
		customerList.add(customer5);
		when(mockCustomerRepository.findById("saiyam@gmail.com")).thenReturn(Optional.of(customer1));
		when(mockCustomerRepository.findById("saptarshi@gmail.com")).thenReturn(Optional.of(customer4));
		when(mockCustomerRepository.findById("sayan@gmail.com")).thenReturn(Optional.of(customer3));
	}

	@Test
	public void testGetAllCustomerRegistrationRequest_toGetList() throws ResourceNotFoundException {
		when(mockCustomerRepository.findAll()).thenReturn(customerList);
		List<CustomerModel> customerModelList = customerHandleService.getAllCustomerRegistrationRequest();
		assertEquals(2, customerModelList.size());
		verify(mockCustomerRepository, times(2)).findAll();
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetAllCustomerRegistrationRequest_toGetException() throws ResourceNotFoundException {
		when(mockCustomerRepository.findAll()).thenReturn(emptyCustomerList);
		customerHandleService.getAllCustomerRegistrationRequest();
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testActionOnCustomerAccount_forInvalidEmailID_toGetException()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		customerHandleService.actionOnCustomerAccount("APPROVE", "fakeEmail@gmail.com");
	}

	@Test
	public void testActionOnCustomerAccount_forActionApprove_toGetValidMessage()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		String expectedMessage = "Account is successfully approved.";
		String actualMessage = customerHandleService.actionOnCustomerAccount("APPROVE", "saiyam@gmail.com");
		assertEquals(expectedMessage, actualMessage);
	}

	@Test(expected = IncorrectResourceDetailException.class)
	public void testActionOnCustomerAccount_forActionApprove_toGetException()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		customerHandleService.actionOnCustomerAccount("APPROVE", "sayan@gmail.com");
	}

	@Test
	public void testActionOnCustomerAccount_forActionReject_toGetValidMessage()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		String expectedMessage = "Account will not be created.";
		String actualMessage = customerHandleService.actionOnCustomerAccount("REJECT", "saiyam@gmail.com");
		assertEquals(expectedMessage, actualMessage);
	}

	@Test(expected = IncorrectResourceDetailException.class)
	public void testActionOnCustomerAccount_forActionReject_toGetException()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		customerHandleService.actionOnCustomerAccount("REJECT", "sayan@gmail.com");
	}

	@Test
	public void testActionOnCustomerAccount_forActionEnable_toGetValidMessage()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		String expectedMessage = "Account successfully activated.";
		String actualMessage = customerHandleService.actionOnCustomerAccount("ENABLE", "saptarshi@gmail.com");
		assertEquals(expectedMessage, actualMessage);
	}

	@Test(expected = IncorrectResourceDetailException.class)
	public void testActionOnCustomerAccount_forActionEnable_toGetException()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		customerHandleService.actionOnCustomerAccount("ENABLEM", "saptarshi@gmail.com");
	}

	@Test
	public void testActionOnCustomerAccount_forActionDisable_toGetValidMessage()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		String expectedMessage = "Account successfully deactivated.";
		String actualMessage = customerHandleService.actionOnCustomerAccount("DISABLE", "sayan@gmail.com");
		assertEquals(expectedMessage, actualMessage);
	}

	@Test(expected = IncorrectResourceDetailException.class)
	public void testActionOnCustomerAccount_forActionDisable_toGetException()
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		customerHandleService.actionOnCustomerAccount("DISABLE", "saiyam@gmail.com");
	}
}

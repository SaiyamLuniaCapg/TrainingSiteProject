package com.cg.training.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.training.entity.Customer;
import com.cg.training.exceptions.IncorrectResourceDetailException;
import com.cg.training.exceptions.ResourceNotFoundException;
import com.cg.training.models.CustomerModel;
import com.cg.training.repository.CustomerRepository;

@Component("customerHandleService")
public class CustomerHandleServiceImpl implements CustomerHandleService {

	@Autowired
	private CustomerRepository customerRepository;

	public CustomerHandleServiceImpl(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerModel> getAllCustomerRegistrationRequest() throws ResourceNotFoundException {
		if (!isValidAdmin())
			throw new ResourceNotFoundException("Admin need to login first.");
		List<Customer> customerList = customerRepository.findAll().stream()
				.filter(customer -> customer.getCustomerAccountStatus().equals("PENDING")).collect(Collectors.toList());
		List<CustomerModel> customerModelList = new ArrayList<>();
		if (customerList.isEmpty())
			throw new ResourceNotFoundException("No new Customer Request available right now.");
		else {
			for (Customer customer : customerList) {
				customerModelList.add(new DozerBeanMapper().map(customer, CustomerModel.class));
			}
		}
		return customerModelList;

	}

	@Override
	public String actionOnCustomerAccount(String action, String email)
			throws ResourceNotFoundException, IncorrectResourceDetailException {
		if (!isValidAdmin())
			throw new ResourceNotFoundException("Admin need to login first.");
		Customer customer = customerRepository.findById(email)
				.orElseThrow(() -> new ResourceNotFoundException("Email Id is invalid."));
		String actionMessage = null;
		if (customer.getCustomerAccountStatus().equals("PENDING")) {
			if (action.equals("APPROVE")) {
				customer.setCustomerAccountStatus("APPROVE");
				customer.setUniqueCode((int) (Math.random() * 1000000));
				customerRepository.save(customer);
				actionMessage = "Account is successfully approved.";
			} else if (action.equals("REJECT")) {
				customer.setCustomerAccountStatus("REJECT");
				customerRepository.save(customer);
				actionMessage = "Account will not be created.";
			} else
				throw new IncorrectResourceDetailException("Action is invalid");
		} else if (customer.getCustomerAccountStatus().equals("ENABLE")) {
			if (action.equals("DISABLE")) {
				customer.setCustomerAccountStatus("DISABLE");
				customerRepository.save(customer);
				actionMessage = "Account successfully deactivated.";
			} else
				throw new IncorrectResourceDetailException("Action is invalid");
		} else if (customer.getCustomerAccountStatus().equals("DISABLE")) {
			if (action.equals("ENABLE")) {
				customer.setCustomerAccountStatus("ENABLE");
				customerRepository.save(customer);
				actionMessage = "Account successfully activated.";
			} else
				throw new IncorrectResourceDetailException("Action is invalid");
		}
		return actionMessage;
	}
	
	public boolean isValidAdmin() {
		Customer admin = customerRepository.findAll().stream()
				.filter(cust -> cust.getCustomerAccountStatus().equals("ADMIN")).findAny().orElse(null);
		if (admin.isAccountAccess())
			return true;
		return false;
	}

}

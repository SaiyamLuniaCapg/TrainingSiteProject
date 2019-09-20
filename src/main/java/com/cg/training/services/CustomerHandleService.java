package com.cg.training.services;

import java.util.List;

import com.cg.training.exceptions.IncorrectResourceDetailException;
import com.cg.training.exceptions.ResourceNotFoundException;
import com.cg.training.models.CustomerModel;

public interface CustomerHandleService {

	List<CustomerModel> getAllCustomerRegistrationRequest() throws ResourceNotFoundException;

	String actionOnCustomerAccount(String action, String email)
			throws ResourceNotFoundException, IncorrectResourceDetailException;
}

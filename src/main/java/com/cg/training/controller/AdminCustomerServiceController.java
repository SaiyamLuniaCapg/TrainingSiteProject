package com.cg.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.training.exceptions.IncorrectResourceDetailException;
import com.cg.training.exceptions.ResourceNotFoundException;
import com.cg.training.models.CustomerModel;
import com.cg.training.responses.CustomResponse;
import com.cg.training.services.CustomerHandleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "APIs for Admin to Control Customer Database", description = "Operations performed by admin to handle Customer.", tags = {
		"APIs for Admin to Control Customer Database" })
@RequestMapping("/admin")
public class AdminCustomerServiceController {

	@Autowired
	private CustomerHandleService customerHandleService;

	@ApiOperation(value = "All Customer Registration request will be displayed.")
	@GetMapping(value = "/customers", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<CustomerModel>> showCustomerRegistrationRequest() throws ResourceNotFoundException {
		return new ResponseEntity<>(customerHandleService.getAllCustomerRegistrationRequest(), HttpStatus.OK);
	}

	@ApiOperation(value = "Admin action on customer's account.")
	@PostMapping(value = "/customers/{action}", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomResponse> actionOnCustomerAccount(
			@ApiParam(value = "Action should be 'ENABLE', 'DISABLE', 'APPROVE', 'REJECT'", required = true) @PathVariable(value = "action") String action,
			@RequestParam String email) throws ResourceNotFoundException, IncorrectResourceDetailException {
		String actionMessage = customerHandleService.actionOnCustomerAccount(action, email);
		return new ResponseEntity<>(new CustomResponse(actionMessage, 123), HttpStatus.OK);
	}
}

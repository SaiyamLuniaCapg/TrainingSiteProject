package com.cg.training.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
@ApiModel(description = "All details about the Customer. ")
public class Customer {

	@NonNull
	@ApiModelProperty(notes = "First Name of Customer")
	private String firstName;
	@NonNull
	@ApiModelProperty(notes = "Last Name of Customer")
	private String lastName;
	@NonNull
	@ApiModelProperty(notes = "Username of Customer")
	private String userName;
	@NonNull
	@ApiModelProperty(notes = "Mobile Number of Customer")
	private long mobileNo;
	@Id
	@NonNull
	@Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "Please Enter Valid Email ID.")
	@ApiModelProperty(notes = "EmailID of Customer")
	private String email;
	@ApiModelProperty(notes = "Customer's Account Status")
	private String customerAccountStatus;
	@ApiModelProperty(notes = "Unique Code for Customer's account")
	private double uniqueCode;
	private boolean accountAccess;

	public Customer() {
	}

	public Customer(String firstName, String lastName, String userName, long mobileNo, String email,
			String customerAccountStatus, double uniqueCode, boolean accountAccess) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.mobileNo = mobileNo;
		this.email = email;
		this.customerAccountStatus = customerAccountStatus;
		this.uniqueCode = uniqueCode;
		this.accountAccess = accountAccess;
	}

	public Customer(String firstName, String lastName, String userName, long mobileNo, String email, String customerAccountStatus) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.mobileNo = mobileNo;
		this.email = email;
		this.customerAccountStatus = customerAccountStatus;
	}
}

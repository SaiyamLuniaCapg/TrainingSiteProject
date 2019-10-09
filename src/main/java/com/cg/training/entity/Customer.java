package com.cg.training.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor @RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(description = "All details about the Customer. ")
public class Customer {

	@NonNull
	@ApiModelProperty(notes = "First Name of Customer")
	String firstName;
	@NonNull
	@ApiModelProperty(notes = "Last Name of Customer")
	String lastName;
	@NonNull
	@ApiModelProperty(notes = "Username of Customer")
	String userName;
	@NonNull
	@ApiModelProperty(notes = "Mobile Number of Customer")
	long mobileNo;
	@Id
	@NonNull
	@Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "Please Enter Valid Email ID.")
	@ApiModelProperty(notes = "EmailID of Customer")
	String email;
	@NonNull
	@ApiModelProperty(notes = "Customer's Account Status")
	String customerAccountStatus;
	@ApiModelProperty(notes = "Unique Code for Customer's account")
	double uniqueCode;
	boolean accountAccess;
}

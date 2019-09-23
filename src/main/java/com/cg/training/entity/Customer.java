package com.cg.training.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "All details about the Customer. ")
public class Customer {

	@NotNull(message = "Please Enter FirstName")
	@ApiModelProperty(notes = "First Name of Customer")
	private String firstName;
	@NotNull(message = "Please Enter LastName")
	@ApiModelProperty(notes = "Last Name of Customer")
	private String lastName;
	@NotNull(message = "Please Enter UserName")
	@ApiModelProperty(notes = "Username of Customer")
	private String userName;
	@NotNull(message = "Please Enter Valid Mobile Number")
	@ApiModelProperty(notes = "Mobile Number of Customer")
	private long mobileNo;
	@Id
	@NotNull
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
			String customerAccountStatus, double uniqueCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.mobileNo = mobileNo;
		this.email = email;
		this.customerAccountStatus = customerAccountStatus;
		this.uniqueCode = uniqueCode;
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

	public boolean isAccountAccess() {
		return accountAccess;
	}

	public void setAccountAccess(boolean accountAccess) {
		this.accountAccess = accountAccess;
	}

	public double getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(double uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomerAccountStatus() {
		return customerAccountStatus;
	}

	public void setCustomerAccountStatus(String customerAccountStatus) {
		this.customerAccountStatus = customerAccountStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerAccountStatus == null) ? 0 : customerAccountStatus.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (int) (mobileNo ^ (mobileNo >>> 32));
		long temp;
		temp = Double.doubleToLongBits(uniqueCode);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (customerAccountStatus == null) {
			if (other.customerAccountStatus != null)
				return false;
		} else if (!customerAccountStatus.equals(other.customerAccountStatus))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (mobileNo != other.mobileNo)
			return false;
		if (Double.doubleToLongBits(uniqueCode) != Double.doubleToLongBits(other.uniqueCode))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName + ", mobileNo="
				+ mobileNo + ", email=" + email + ", customerAccountStatus=" + customerAccountStatus + ", uniqueCode="
				+ uniqueCode + "]";
	}
}

package com.cg.training.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;

@Entity
@ApiModel(description = "All details about the Subscribed Courses by Customer. ")
public class SubscribedCourses {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String email;
	private String courseName;

	public SubscribedCourses() {}

	public SubscribedCourses(String email, String courseName) {
		super();
		this.email = email;
		this.courseName = courseName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Override
	public String toString() {
		return "SubscribedCourses [courseName=" + courseName + "]";
	}

}

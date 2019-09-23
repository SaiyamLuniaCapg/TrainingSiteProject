package com.cg.training.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
@ApiModel(description = "All details about the Subscribed Courses by Customer. ")
public class SubscribedCourses {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NonNull
	private String email;
	@NonNull
	private String courseName;

	public SubscribedCourses() {
	}

	public SubscribedCourses(@NonNull String email, @NonNull String courseName) {
		this.email = email;
		this.courseName = courseName;
	}

}

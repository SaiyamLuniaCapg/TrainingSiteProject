package com.cg.training.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor @RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(description = "All details about the Subscribed Courses by Customer. ")
public class SubscribedCourses {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	@NonNull
	String email;
	@NonNull
	String courseName;
}

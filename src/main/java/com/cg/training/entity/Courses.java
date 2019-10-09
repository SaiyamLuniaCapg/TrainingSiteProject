package com.cg.training.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
@NoArgsConstructor @RequiredArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(description = "All details about the Course. ")
public class Courses {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int courseId;
	@NonNull
	@ApiModelProperty(notes = "Course Name")
	String courseName;
	@NonNull
	@ApiModelProperty(notes = "Course Platform")
	String coursePlatform;
	@NonNull
	@ApiModelProperty(notes = "Course Price")
	int coursePrice;
	@NonNull
	int courseVersion;
	@NonNull
	boolean courseStatus;
}

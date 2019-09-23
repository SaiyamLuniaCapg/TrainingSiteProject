package com.cg.training.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
@ApiModel(description = "All details about the Course. ")
public class Courses {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int courseId;
	@NonNull
	@ApiModelProperty(notes = "Course Name")
	private String courseName;
	@NonNull
	@ApiModelProperty(notes = "Course Platform")
	private String coursePlatform;
	@NonNull
	@ApiModelProperty(notes = "Course Price")
	private int coursePrice;
	private int courseVersion;
	private boolean courseStatus;

	public Courses() {
	}

	public Courses(String courseName, String coursePlatform, int coursePrice, int courseVersion, boolean courseStatus) {
		super();
		this.courseName = courseName;
		this.coursePlatform = coursePlatform;
		this.coursePrice = coursePrice;
		this.courseVersion = courseVersion;
		this.courseStatus = courseStatus;
	}

	public Courses(String courseName, String coursePlatform, int coursePrice) {
		this.courseName = courseName;
		this.coursePlatform = coursePlatform;
		this.coursePrice = coursePrice;
	}
}

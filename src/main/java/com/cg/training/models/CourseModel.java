package com.cg.training.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class CourseModel {
	@NonNull
	private String courseName;
	@NonNull
	private String coursePlatform;
	@NonNull
	private int coursePrice;

	public CourseModel(@NonNull String courseName, @NonNull String coursePlatform, @NonNull int coursePrice) {
		super();
		this.courseName = courseName;
		this.coursePlatform = coursePlatform;
		this.coursePrice = coursePrice;
	}

	public CourseModel() {
	}

}

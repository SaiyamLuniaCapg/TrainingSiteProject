package com.cg.training.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseModel {
	@NonNull
	String courseName;
	@NonNull
	String coursePlatform;
	@NonNull
	int coursePrice;
}

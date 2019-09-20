package com.cg.training.models;

import javax.validation.constraints.NotNull;

public class CourseModel {
	@NotNull
	private String courseName;
	@NotNull
	private String coursePlatform;
	@NotNull
	private int coursePrice;

	public CourseModel() {
	}

	public CourseModel(String courseName, String coursePlatform, int coursePrice) {
		this.courseName = courseName;
		this.coursePlatform = coursePlatform;
		this.coursePrice = coursePrice;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCoursePlatform() {
		return coursePlatform;
	}

	public void setCoursePlatform(String coursePlatform) {
		this.coursePlatform = coursePlatform;
	}

	public int getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(int coursePrice) {
		this.coursePrice = coursePrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((coursePlatform == null) ? 0 : coursePlatform.hashCode());
		result = prime * result + coursePrice;
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
		CourseModel other = (CourseModel) obj;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (coursePlatform == null) {
			if (other.coursePlatform != null)
				return false;
		} else if (!coursePlatform.equals(other.coursePlatform))
			return false;
		if (coursePrice != other.coursePrice)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Courses [courseName=" + courseName + ", coursePlatform=" + coursePlatform + ", coursePrice="
				+ coursePrice + "]";
	}

}

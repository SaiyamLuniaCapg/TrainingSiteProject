package com.cg.training.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "All details about the Course. ")
public class Courses {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int courseId;
	@NotNull(message = "Please Enter CourseName")
	@ApiModelProperty(notes = "Course Name")
	private String courseName;
	@NotNull(message = "Please Enter Course Platform")
	@ApiModelProperty(notes = "Course Platform")
	private String coursePlatform;
	@NotNull(message = "Please Enter Course Price")
	@ApiModelProperty(notes = "Course Price")
	private int coursePrice;
	private int courseVersion;
	private boolean courseStatus;

	public Courses() {
	}

	public Courses(String courseName, String coursePlatform, int coursePrice, int courseVersion,
			boolean courseStatus) {
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

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCourseVersion() {
		return courseVersion;
	}

	public void setCourseVersion(int courseVersion) {
		this.courseVersion = courseVersion;
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

	public boolean isCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(boolean courseStatus) {
		this.courseStatus = courseStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseId;
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((coursePlatform == null) ? 0 : coursePlatform.hashCode());
		result = prime * result + coursePrice;
		result = prime * result + courseVersion;
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
		Courses other = (Courses) obj;
		if (courseId != other.courseId)
			return false;
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
		if (courseVersion != other.courseVersion)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Courses [courseId=" + courseId + ", courseName=" + courseName + ", coursePlatform=" + coursePlatform
				+ ", coursePrice=" + coursePrice + ", courseVersion=" + courseVersion + "]";
	}

}

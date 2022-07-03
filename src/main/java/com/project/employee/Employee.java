package com.project.employee;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashMap;

@Entity
@Table(name = "Employees")
@NoArgsConstructor
public class Employee {
	private String employeeId;
	@Id
	@GeneratedValue
	private int id;
	@NonNull
	private java.lang.String name;
	@NonNull
	private java.lang.String email;
	@NonNull
	private java.lang.String mobile;
	private HashMap<String, Integer> skills;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public HashMap<String, Integer> getSkills() {
		return skills;
	}

	public void setSkills(HashMap<String, Integer> skills) {
		this.skills = skills;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
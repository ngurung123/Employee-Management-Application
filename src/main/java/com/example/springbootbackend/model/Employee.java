package com.example.springbootbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@jakarta.persistence.Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long empID;

	@Column(name = "first_name")
	private String empFirstName;

	@Column(name = "last_name")
	private String empLastName;

	@Column(name = "email_id")
	private String empEmailID;

	public Employee() {

	}

	public Employee(String empFirstName, String empLastName, String empEmailID) {
		super();
		this.empFirstName = empFirstName;
		this.empLastName = empLastName;
		this.empEmailID = empEmailID;
	}

	public long getEmpID() {
		return empID;
	}

	public void setEmpID(long empID) {
		this.empID = empID;
	}

	public String getEmpFirstName() {
		return empFirstName;
	}

	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public String getEmpLastName() {
		return empLastName;
	}

	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	public String getEmpEmailID() {
		return empEmailID;
	}

	public void setEmpEmailID(String empEmailID) {
		this.empEmailID = empEmailID;
	}

	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}

}

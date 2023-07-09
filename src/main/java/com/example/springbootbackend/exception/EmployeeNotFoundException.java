package com.example.springbootbackend.exception;

public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(Long id) {
		super("Employee with id " + id + " is not present");

	}

}

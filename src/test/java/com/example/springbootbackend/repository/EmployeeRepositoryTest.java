package com.example.springbootbackend.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import com.example.springbootbackend.SpringbootBackendApplication;
import com.example.springbootbackend.model.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(classes = SpringbootBackendApplication.class)
class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void EmployeeRepository_findEmployeesByID() {
		Employee employee = getEmployee();
		employeeRepository.save(employee);

		Employee employees = employeeRepository.findById(employee.getEmpID()).get();
		Assertions.assertThat(employees).isNotNull();

	}

	@Test
	public void EmployeeRepository_saveEmployees() {
		Employee employee = getEmployee();
		employeeRepository.save(employee);
		Assertions.assertThat(employee).isNotNull();
		Assertions.assertThat(employee.getEmpID()).isGreaterThan(0);

	}

	@Test
	public void EmployeeRepository_getAllEmployees() {

		Employee employee1 = getEmployee();
		employeeRepository.save(employee1);
		List<Employee> employees = employeeRepository.findAll();
		assertEquals(employees.size(), 1);
	}

	@Test
	public void EmployeeRepository_updateEmployeesByID() {
		Employee employee = getEmployee();

		Employee employee2 = employeeRepository.save(employee);

		Employee employeeSave = employeeRepository.findById(employee2.getEmpID())
				.orElseThrow(() -> new NoSuchElementException("No such id " + employee.getEmpID()));
		employeeSave.setEmpFirstName("Nishan");
		employeeSave.setEmpLastName("Gurung");
		employeeSave.setEmpEmailID("nishan@gmail.com");
		Employee updatedEmployee = employeeRepository.save(employeeSave);

		Assertions.assertThat(updatedEmployee.getEmpFirstName().compareTo("Nishan")).isEqualTo(0);

	}

	@Test
	public void EmployeeRepository_deleteEmployeesByID() {
		Employee employee = getEmployee();
		employeeRepository.deleteById(employee.getEmpID());
		Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getEmpID());
		Assertions.assertThat(deletedEmployee.isEmpty());
	}

	private Employee getEmployee() {
		Employee employee = new Employee();
		employee.setEmpID(1L);
		employee.setEmpFirstName("Merry");
		employee.setEmpLastName("Watson");
		employee.setEmpEmailID("merry@gmail.com");
		return employee;
	}

}

package com.example.springbootbackend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private EmployeeRepository employeeRepository;

	private List<Employee> employee = new ArrayList<>();

	@BeforeEach
	public void setup() {
		Employee employee1 = new Employee();
		employee1.setEmpID(1L);
		employee1.setEmpFirstName("Sams");
		employee1.setEmpLastName("Club");
		employee1.setEmpEmailID("sams@gmail.com");
		employee.add(employee1);
	}

	@SuppressWarnings({ "unlikely-arg-type", "unchecked" })
	@Test
	public void getAllEmployeesTest() throws Exception {
		employeeRepository.saveAll(employee);
		Mockito.when(employeeRepository.findAll()).thenReturn(employee);
		mockMvc.perform(get("/api/v1/employees").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(employee.size()));

	}

	@Test
	public void createEmployeeTest() throws JsonProcessingException, Exception {
		Employee employees = new Employee();
		employees.setEmpID(2);
		employees.setEmpFirstName("Nischal");
		employees.setEmpLastName("Gurung");
		employees.setEmpEmailID("nischal@gmail.com");

		mockMvc.perform(post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employees))).andExpect(status().isOk());
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void getEmployeeByIdTest() throws Exception {
		Employee employee1 = new Employee();
		employee1.setEmpID(2);
		employee1.setEmpFirstName("Nischal");
		employee1.setEmpLastName("Gurung");
		employee1.setEmpEmailID("nischal@gmail.com");
		Mockito.when(employeeRepository.findById(employee1.getEmpID())).thenReturn(Optional.of(employee1));

		ResultActions response = mockMvc.perform(get("/api/v1/employees/{empID}", employee1.getEmpID())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee1)))
				.andExpect(status().isOk());
		response.andExpect(jsonPath("$.empFirstName").value(employee1.getEmpFirstName()))
				.andExpect(jsonPath("$.empLastName").value(employee1.getEmpLastName()))
				.andExpect(jsonPath("$.empEmailID").value(employee1.getEmpEmailID()));

	}

	@Test
	public void updateEmployeeTest() throws JsonProcessingException, Exception {
		Employee employee = new Employee();
		employee.setEmpID(1);
		employee.setEmpFirstName("Keshar");
		employee.setEmpLastName("Gurung");
		employee.setEmpEmailID("keshar@gmail.com");

		Employee updatedEmployee = new Employee();
		updatedEmployee.setEmpFirstName("Nira");
		updatedEmployee.setEmpLastName("Thapa");
		updatedEmployee.setEmpEmailID("nira@gmail.com");

		Mockito.when(employeeRepository.findById(employee.getEmpID())).thenReturn(Optional.of(employee));
		Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

		ResultActions response = mockMvc.perform(put("/api/v1/employees/{empID}", employee.getEmpID())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEmployee)))
				.andExpect(status().isOk());

		response.andExpect(jsonPath("$.empFirstName").value(updatedEmployee.getEmpFirstName()))
				.andExpect(jsonPath("$.empLastName").value(updatedEmployee.getEmpLastName()))
				.andExpect(jsonPath("$.empEmailID").value(updatedEmployee.getEmpEmailID()));
	}

	@Test
	public void deleteEmployeeTest() throws Exception {
		Employee employee = new Employee();
		employee.setEmpID(1);
		employee.setEmpFirstName("Mira");
		employee.setEmpLastName("Miller");
		employee.setEmpEmailID("mira@gmail.com");
		employeeRepository.save(employee);

		doNothing().when(employeeRepository).deleteById(employee.getEmpID());

//		mockMvc.perform(delete("/api/v1/employees/1", employee.getEmpID())).andExpect(status().isNoContent())
//				.andDo(print());

	}

}

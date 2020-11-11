package com.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class EmployeeInMemoryRepositoryTest {

	private EmployeeInMemoryRepository employeeRepository;

	private List<Employee> employees;

	@Before
	public void setup() {
		employees = new ArrayList<>();
		employeeRepository = new EmployeeInMemoryRepository(employees);
	}

	@Test
	public void testFindAll() {
		Employee employee1 = new Employee("1", 1000);
		Employee employee2 = new Employee("2", 2000);
		employees.addAll(asList(employee1, employee2));
		assertThat(employeeRepository.findAll())
			.containsExactly(employee1, employee2);
	}

	@Test
	public void testSaveNewEmployee() {
		com.example.Employee saved = employeeRepository.save(new com.example.Employee("1", 1000));
		assertThat(employees)
			.containsExactly(saved);
	}

	@Test
	public void testSaveExistingEmployee() {
		com.example.Employee employee1 = new com.example.Employee("1", 1000);
		com.example.Employee employee2 = new com.example.Employee("2", 2000);
		employees.addAll(asList(employee1, employee2));
		com.example.Employee saved = employeeRepository.save(new com.example.Employee("2", 3000));
		assertThat(employees)
			.containsExactly(employee1, saved);
	}
}

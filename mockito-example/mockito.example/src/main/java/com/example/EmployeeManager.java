package com.example;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeManager {

	final private static Logger LOGGER = Logger.getLogger(EmployeeManager.class.getName());

	private com.example.EmployeeRepository employeeRepository;
	private BankService bankService;

	public EmployeeManager(com.example.EmployeeRepository employeeRepository, BankService bankService) {
		this.employeeRepository = employeeRepository;
		this.bankService = bankService;
	}

	public int payEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		int payments = 0;
		for (Employee employee : employees) {
			try {
				bankService.pay(employee.getId(), employee.getSalary());
				employee.setPaid(true);
				payments++;
			} catch (RuntimeException e) {
				LOGGER.log(Level.WARNING, "Failed payment of " + employee, e);
				employee.setPaid(false);
			}
		}
		return payments;
	}

}

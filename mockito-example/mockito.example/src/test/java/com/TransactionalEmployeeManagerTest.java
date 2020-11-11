
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.mockito.AdditionalAnswers.answer;
import static org.mockito.Mockito.*;

public class TransactionalEmployeeManagerTest {

	private com.example.TransactionalEmployeeManager employeeManager;

	private com.example.TransactionManager transactionManager;

	private com.example.EmployeeRepository employeeRepository;

	private com.example.BankService bankService;

	@Before
	public void setup() {
		employeeRepository = mock(com.example.EmployeeRepository.class);
		transactionManager = mock(com.example.TransactionManager.class);
		// make sure the lambda passed to the TransactionManager
		// is executed, using the mock repository
		when(transactionManager.doInTransaction(any()))
			.thenAnswer(
				answer((com.example.TransactionCode<?> code) -> code.apply(employeeRepository)));
		bankService = mock(com.example.BankService.class);
		employeeManager = new com.example.TransactionalEmployeeManager(transactionManager, bankService);
	}

	@Test
	public void testPayEmployeesWhenSeveralEmployeesArePresent() {
		com.example.Employee employee1 = new com.example.Employee("1", 1000);
		com.example.Employee employee2 = new com.example.Employee("2", 2000);
		when(employeeRepository.findAll())
			.thenReturn(asList(employee1, employee2));
		employeeManager.payEmployees();
		verify(bankService).pay("2", 2000);
		verify(bankService).pay("1", 1000);
		verify(employeeRepository).save(employee1);
		verify(employeeRepository).save(employee2);
		// also verify that a single transaction is executed
		verify(transactionManager, times(1)).doInTransaction(any());
	}

}
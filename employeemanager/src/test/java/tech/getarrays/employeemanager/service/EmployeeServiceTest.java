package tech.getarrays.employeemanager.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tech.getarrays.employeemanager.exception.UserNotFoundException;
import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.repo.EmployeeRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService service;

    @MockBean
    private EmployeeRepo repository;

    @Test
    void addEmployee() {
        Employee employee = new Employee("Wojtek", "w.maj@gmail.com", "Worker",
                "693693693", 2000L, 5L, "url/image", "12", 5.0,
                false, 2.0, LocalDate.of(2022, 9, 10), 6.5,
                LocalDate.of(2021, 10, 15));
        when(repository.save(employee)).thenReturn(employee);
        assertEquals(employee, service.addEmployee(employee));
    }

    @Test
    void findAllEmployees() {
        when(repository.findAll()).thenReturn(Stream.of(new Employee("Wojtek", "w.maj@gmail.com", "Worker",
                        "693693693", 2000L, 5L, "url/image", "12", 5.0,
                        false, 2.0, LocalDate.of(2022, 9, 10), 6.5,
                        LocalDate.of(2021, 10, 15)),
                new Employee("Antek", "a.zyc@gmail.com", "Worker2",
                        "321321321", 1000L, 3L, "url2/image", "13", 3.0,
                        false, 4.0, LocalDate.of(2022, 9, 8), 5.4,
                        LocalDate.of(2022, 1, 10))).collect(Collectors.toList()));
        assertEquals(2, service.findAllEmployees().size());
    }

    @Test
    void bonus() {
        Employee employee = new Employee("Wojtek", "w.maj@gmail.com", "Worker",
                "693693693", 2000L, 5L, "url/image", "12", 5.0,
                true, 2.0, LocalDate.of(2022, 9, 10), 6.5,
                LocalDate.of(2021, 10, 15));

        assertTrue(employee.getShouldHaveBonus());
        assertEquals(employee.getBonus(), 2.0);

        service.bonus(employee);
        assertFalse(employee.getShouldHaveBonus());
        assertEquals(employee.getBonus(), 0.0);

    }

    @Test
    void deleteEmployee() {
        Employee employee = new Employee("Wojtek", "w.maj@gmail.com", "Worker",
                "693693693", 2000L, 5L, "url/image", "12", 5.0,
                false, 2.0, LocalDate.of(2022, 9, 10), 6.5,
                LocalDate.of(2021, 10, 15));

        service.addEmployee(employee);
        when(repository.save(employee)).thenReturn(employee);
        assertEquals(employee, service.addEmployee(employee));

        assertEquals(0, doThrow(new UserNotFoundException("Brak pracownika")).when(repository).deleteEmployeeById(employee.getId()));
    }


    @Test
    void findEmployeeById() {
        Employee employee = new Employee("Wojtek", "w.maj@gmail.com", "Worker",
                "693693693", 2000L, 5L, "url/image", "12", 5.0,
                false, 2.0, LocalDate.of(2022, 9, 10), 6.5,
                LocalDate.of(2021, 10, 15));
        service.addEmployee(employee);
        when(repository.save(employee)).thenReturn(employee);
        assertEquals(employee, service.addEmployee(employee));

        when(repository.findEmployeeById(employee.getId())).thenReturn(Optional.of(employee));
        assertEquals(employee, service.findEmployeeById(employee.getId()));
    }
}
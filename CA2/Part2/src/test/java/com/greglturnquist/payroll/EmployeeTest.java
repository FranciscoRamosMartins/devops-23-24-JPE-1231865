package com.greglturnquist.payroll;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

    @Test
    void testEmployeeDefaultConstructor() {
        Employee employee = new Employee();
        assertNotNull(employee);
    }

    @Test
    void testEmployeeCreation() {
        Employee employee = new Employee("John", "Doe", "Description", "Developer", 5, "john@gmail.com");
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("Description", employee.getDescription());
        assertEquals("Developer", employee.getJobTitle());
        assertEquals(5, employee.getJobYears());
        assertEquals("john@gmail.com", employee.getEmail());
    }

    @Test
    void testExceptionThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Employee employee = new Employee(null, "Doe", "Description", "Developer", 5, "john@gmail.com");
        });
        assertEquals("Invalid arguments", exception.getMessage());
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            Employee employee = new Employee("John", null, "Description", "Developer", 5, "john@gmail.com");
        });
        assertEquals("Invalid arguments", exception2.getMessage());
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            Employee employee = new Employee("John", "Doe", null, "Developer", 5, "john@gmail.com");
        });
        assertEquals("Invalid arguments", exception3.getMessage());
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
            Employee employee = new Employee("John", "Doe", "Description", null, 5, "john@gmail.com");
        });
        assertEquals("Invalid arguments", exception4.getMessage());
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> {
            Employee employee = new Employee("John", "Doe", "Description", "Developer", -1, "john@gmail.com");
        });
        assertEquals("Invalid arguments", exception5.getMessage());
        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> {
            Employee employee = new Employee("John", "Doe", "Description", "Developer", 5, null);
        });
        assertEquals("Invalid arguments", exception6.getMessage());
    }

    @Test
    void testEmployeeValidation() {
        Employee employee = new Employee();
        assertFalse(employee.validateArguments(null, "Doe", "Description", "Developer", 5, "john@gmail.com"));
        assertFalse(employee.validateArguments("John", null, "Description", "Developer", 5, "john@gmail.com"));
        assertFalse(employee.validateArguments("John", "Doe", null, "Developer", 5, "john@gmail.com"));
        assertFalse(employee.validateArguments("John", "Doe", "Description", null, 5, "john@gmail.com"));
        assertFalse(employee.validateArguments("John", "Doe", "Description", "Developer", -1, "john@gmail.com"));
        assertTrue(employee.validateArguments("John", "Doe", "Description", "Developer", 5, "john@gmail.com"));
    }

    @Test
    void testInvalidEmail_shouldReturnFalse() {
        Employee employee = new Employee();
        assertFalse(employee.validateArguments("John", "Doe", "Description", "Developer", 5, "john"));
    }

    @Test
    void testEmployeeEquality() {
        Employee employee1 = new Employee("John", "Doe", "Description", "Developer", 5, "john@gmail.com");
        Employee employee2 = new Employee("John", "Doe", "Description", "Developer", 5, "john@gmail.com");
        assertEquals(employee1, employee2);
    }

    @Test
    void testEmployeeToString() {
        Employee employee = new Employee("John", "Doe", "Description", "Developer", 5, "john@gmail.com");
        String expectedString = "Employee{id=null, firstName='John', lastName='Doe', description='Description', jobTitle='Developer', jobYears=5, email='john@gmail.com'}";
        assertEquals(expectedString, employee.toString());
    }

    @Test
    void testEmployeeSettersAndGetters() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDescription("Description");
        employee.setJobTitle("Developer");
        employee.setJobYears(5);
        employee.setEmail("john@gmail.com");
        assertEquals(1L, employee.getId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("Description", employee.getDescription());
        assertEquals("Developer", employee.getJobTitle());
        assertEquals(5, employee.getJobYears());
        assertEquals("john@gmail.com", employee.getEmail());
    }

    @Test
    void testEmployeeHashCode() {
        Employee employee1 = new Employee("John", "Doe", "Description", "Developer", 5, "john@gmail.com");
        Employee employee2 = new Employee("John", "Doe", "Description", "Developer", 5, "john@gmail.com");
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }
}

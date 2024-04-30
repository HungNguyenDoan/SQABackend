package com.sqa.banking.repositories;

import com.sqa.banking.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer existingCustomer;

    @BeforeEach
    void setUp() {
        // Create and save an existing customer to the in-memory database
        existingCustomer = Customer.builder()
                .fullName("John Doe")
                .phoneNumber("1234567890")
                .identify("ID123456")
                .gender(1)
                .dob(new Date())
                .email("johndoe@example.com")
                .city("City")
                .province("Province")
                .district("District")
                .address("123 Main St")
                .currentAddress("456 Current St")
                .job("Developer")
                .build();
        customerRepository.save(existingCustomer);
    }

    @Test
    public void testFindByNameWhenCustomerExistsThenReturnListWithCustomer() {
        // Arrange
        String searchCriteria = "John";

        // Act
        List<Customer> result = customerRepository.findByName(searchCriteria);

        // Assert
        assertThat(result).contains(existingCustomer);
    }

    @Test
    public void testFindByNameWhenCustomerDoesNotExistThenReturnEmptyList() {
        // Arrange
        String searchCriteria = "Jane";

        // Act
        List<Customer> result = customerRepository.findByName(searchCriteria);

        // Assert
        assertThat(result).isEmpty();
    }
}

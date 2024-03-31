package com.sqa.banking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sqa.banking.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    @Query("SELECT c FROM Customer c WHERE c.fullName LIKE %:name%")
    List<Customer> findByName(@Param("name") String name);
}

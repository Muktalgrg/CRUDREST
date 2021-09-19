package com.simple.service.impl;

import com.simple.api.mapper.CustomerMapper;
import com.simple.api.model.CustomerDTO;
import com.simple.bootstrap.Bootstrap;
import com.simple.domain.Customer;
import com.simple.repository.CategoryRepository;
import com.simple.repository.CustomerRepository;
import com.simple.service.CustomerService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;


    @BeforeEach
    public void setUp() throws Exception{
        System.out.println("Loading customer data");
        System.out.println(customerRepository.findAll().size());
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void patch() throws Exception{
        // get customer from database
        String updatedFirstName = "David";
        String updatedLastName = "Beckham";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.findById(id).get();
        // capture first and last name!
        String firstName = originalCustomer.getFirstname();
        String lastName = originalCustomer.getLastname();
        System.out.println();
        System.out.println("1........original customer: "+originalCustomer);
        assertNotNull(originalCustomer);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedFirstName);
        customerDTO.setLastname(updatedLastName);

        CustomerDTO savedCustomerDto = customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(savedCustomerDto);
        assertEquals(updatedFirstName, savedCustomerDto.getFirstname());
        assertEquals(updatedLastName, savedCustomerDto.getLastname());

        System.out.println("original customer: "+originalCustomer);
        System.out.println("saved dto: "+savedCustomerDto);
        assertNotEquals(firstName, updatedCustomer.getFirstname());
        assertNotEquals(lastName, updatedCustomer.getLastname());
    }

    private Long getCustomerIdValue(){
        List<Customer> customerList = customerRepository.findAll();
        return customerList.get(0).getId();
    }



}

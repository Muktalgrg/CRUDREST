package com.simple.service.impl;



import com.simple.api.mapper.CustomerMapper;
import com.simple.api.model.CustomerDTO;
import com.simple.domain.Customer;
import com.simple.repository.CustomerRepository;
import com.simple.service.CustomerService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    CustomerService customerService;


    @BeforeEach
    void setUp() throws Exception{
        customerService = new CustomerServiceImpl( customerMapper, customerRepository);
    }

    @Test
    void getAllCustomers() throws Exception{
//        List<Customer> customerList = Arrays.asList(new Customer(1L, "Mukta", "Gurung"), new Customer(2L, "Micheal", "Phelps"));
        Customer customer1 = new Customer(1L, "Micheal", "Phelps");
        Customer customer2 = new Customer(2L, "LoLo", "Jones");
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<CustomerDTO> customerDTO = customerService.getAllCustomers();
        assertEquals(2, customerDTO.size());
    }

    @Test
    void getCustomerById() throws Exception{
        Customer customer1 = new Customer(1L, "Micheal", "Phelps");
        when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer1));

        CustomerDTO customerDTO = customerService.getCustomerById(1L);
        assertEquals("Micheal", customerDTO.getFirstname());
    }

    @Test
    void createNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");
        customerDTO.setLastname("Sim");

        Customer customer = new Customer(1L, customerDTO.getFirstname(), customerDTO.getLastname());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);
        assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
    }

    @Test
    void saveCustomerByDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Mike");
        customerDTO.setLastname("Tyson");

        Customer customer = new Customer(1L, customerDTO.getFirstname(), customerDTO.getLastname());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);
        assertEquals(savedDto.getFirstname(), customerDTO.getFirstname());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());

    }


    @Test
    void deleteCustomerById() {
        Customer customer = new Customer(1L, "Mike", "Tyson");
        customerRepository.deleteById(1L);
        verify(customerRepository, timeout(1)).deleteById(anyLong());
    }




}
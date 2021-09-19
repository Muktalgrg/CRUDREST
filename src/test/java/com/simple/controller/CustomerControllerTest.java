package com.simple.controller;

import com.simple.api.model.CustomerDTO;
import com.simple.exception.ResourceNotFoundException;
import com.simple.service.CustomerService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.simple.controller.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest extends Exception{

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(ResourceNotFoundException.class).build();
    }


    @Test
    void getAllCustomer() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("david");
        customerDTO1.setLastname("beckham");
        customerDTO1.setCustomerUrl(CustomerController.BASE_URL + "/1");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstname("paul");
        customerDTO2.setLastname("scholes");
        customerDTO2.setCustomerUrl(CustomerController.BASE_URL + "/2");
        List<CustomerDTO> customerDTOList = Arrays.asList(customerDTO1, customerDTO2);

        when(customerService.getAllCustomers()).thenReturn(customerDTOList);

//        mockMvc.perform(get(CustomerController.BASE_URL).contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(get(CustomerController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andDo(print())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("david");
        customerDTO1.setLastname("beckham");
        customerDTO1.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO1);

        mockMvc.perform(get(CustomerController.BASE_URL + "/4").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstname", is("david")));

    }

    @Test
    void createCustomer() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("david");
        customerDTO1.setLastname("beckham");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customerDTO1.getFirstname());
        returnDTO.setLastname(customerDTO1.getLastname());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.createNewCustomer(customerDTO1)).thenReturn(returnDTO);

        mockMvc.perform(post(CustomerController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("david")))
                .andExpect(jsonPath("$.customer_url", is(CustomerController.BASE_URL + "/1")));


    }

    @Test
    void updateCustomer() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("david");
        customerDTO1.setLastname("beckham");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customerDTO1.getFirstname());
        returnDTO.setLastname(customerDTO1.getLastname());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

//        when(customerService.saveCustomerByDTO(anyLong(),any())).thenReturn(returnDTO);

        when(customerService.saveCustomerByDTO(anyLong(), ArgumentMatchers.any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstname", equalTo("david")));
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }



    /*
    @Test
    public void NotfoundException() throws Exception {
        when(customerService.getCustomerById(222L)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                        .contentType(MediaType.APPLICATION_JSON))

//                .andDo(print());
//                .andExpect(status().isNotFound());
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
//                .andExpect(result -> assertEquals("resource not found", result.getResolvedException().getMessage()));
    }

    */

}
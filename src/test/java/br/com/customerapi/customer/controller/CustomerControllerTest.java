package br.com.customerapi.customer.controller;

import br.com.customerapi.customer.dto.CustomerRequest;
import br.com.customerapi.customer.dto.CustomerResponse;
import br.com.customerapi.customer.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    private static final String API_URI = "/api/customers";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService service;

    @Test
    public void save_shouldSaveANewCustomer() throws Exception {
        final var request = CustomerRequest.builder()
            .document("507.473.680-31")
            .address("Rua A")
            .name("Teste")
            .birthDate(LocalDate.of(1999, 1, 1))
            .build();
        mvc.perform(post(API_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isCreated());
    }

    @Test
    public void update_shouldUpdateACustomer() throws Exception {
        final var request = CustomerRequest.builder()
            .document("507.473.680-31")
            .address("Rua A")
            .name("Teste")
            .birthDate(LocalDate.of(1999, 1, 1))
            .build();
        mvc.perform(put(API_URI + "/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(request)))
            .andExpect(status().isNoContent());
    }

    @Test
    public void findAll_shouldReturnAllCustomers() throws Exception {
        final var customer1 = CustomerResponse.builder()
            .name("customer1")
            .birthDate(LocalDate.of(1900, 1, 1))
            .build();
        final var customer2 = CustomerResponse.builder()
            .name("customer2")
            .birthDate(LocalDate.of(1900, 1, 1))
            .build();
        final var customers = Lists.newArrayList(customer1, customer2);
        when(service.getAll(any(), any())).thenReturn(customers);

        mvc.perform(get(API_URI))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].name", is("customer1")))
            .andExpect(jsonPath("$[1].name", is("customer2")));
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws JsonProcessingException {
        return getMapper().writeValueAsBytes(object);
    }

    private static ObjectMapper getMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}

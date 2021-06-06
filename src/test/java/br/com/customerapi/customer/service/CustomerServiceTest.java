package br.com.customerapi.customer.service;

import br.com.customerapi.customer.dto.CustomerFilters;
import br.com.customerapi.customer.dto.CustomerRequest;
import br.com.customerapi.customer.dto.PageRequest;
import br.com.customerapi.customer.model.Customer;
import br.com.customerapi.customer.repository.CustomerRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService service;

    @Mock
    private CustomerRepository repository;

    @Test
    public void save_shouldSaveANewCustomer(){
        final var request = CustomerRequest.builder()
            .document("10101")
            .address("Rua A")
            .name("Teste")
            .birthDate(LocalDate.of(1999, 1, 1))
            .build();

        final ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        service.save(request);

        verify(repository, times(1)).save(captor.capture());

        assertThat(captor.getValue().getName()).isEqualTo("Teste");
        assertThat(captor.getValue().getAddress()).isEqualTo("Rua A");
        assertThat(captor.getValue().getDocument()).isEqualTo("10101");
        assertThat(captor.getValue().getBirthDate()).isEqualTo(LocalDate.of(1999, 1, 1));
    }

    @Test
    public void update_shouldUpdateACustomer(){
        when(repository.findById(1)).thenReturn(Optional.of( Customer.builder()
            .id(1)
            .name("old-teste")
            .build()));

        final var request = CustomerRequest.builder()
            .document("10101")
            .address("Rua A")
            .name("Teste")
            .birthDate(LocalDate.of(1999, 1, 1))
            .build();

        final ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        service.upadte(1, request);

        verify(repository, times(1)).save(captor.capture());

        assertThat(captor.getValue().getId()).isEqualTo(1);
        assertThat(captor.getValue().getName()).isEqualTo("Teste");
        assertThat(captor.getValue().getAddress()).isEqualTo("Rua A");
        assertThat(captor.getValue().getDocument()).isEqualTo("10101");
        assertThat(captor.getValue().getBirthDate()).isEqualTo(LocalDate.of(1999, 1, 1));
    }

    @Test
    public void update_notFoundException_whenNotFindACustomer(){
        assertThatExceptionOfType(ResponseStatusException.class)
            .isThrownBy(() ->  service.upadte(null, new CustomerRequest()))
            .withMessage("404 NOT_FOUND \"Customer not found.\"");

        verify(repository, never()).save(any());
    }

    @Test
    public void findAll_shouldReturnAllCustomers(){
        final var pageRequest = new PageRequest();
        final var customerFilters = new CustomerFilters();
        final var customer = Customer.builder().birthDate(LocalDate.of(1900, 1, 1)).build();
        final var customers = Lists.newArrayList(customer, customer);
        Page<Customer> pagedResponse = new PageImpl(customers);
        when(repository.findAll(customerFilters.toPredicate().build(), pageRequest)).thenReturn(pagedResponse);

        assertThat(service.getAll(pageRequest, customerFilters))
            .hasSize(2);
    }
}

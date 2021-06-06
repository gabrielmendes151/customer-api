package br.com.customerapi.customer.repository;

import br.com.customerapi.customer.dto.CustomerFilters;
import br.com.customerapi.customer.dto.CustomerResponse;
import br.com.customerapi.customer.dto.PageRequest;
import br.com.customerapi.customer.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
@ActiveProfiles("test")
@Sql("classpath:/scripts/tests_customer.sql")
@RunWith(SpringRunner.class)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void save_shouldSaveANewCustomer() {
        final var customer = Customer.builder()
            .name("TESTE1")
            .birthDate(LocalDate.of(200, 1, 10))
            .address("Rua TESTE")
            .document("868.343.810-40")
            .build();
        final var customerSaved = repository.save(customer);

        assertThat(customerSaved.getId()).isNotNull();
        assertThat(customerSaved.getCreateAt()).isNotNull();
        assertThat(customerSaved.getModifiedAt()).isNotNull();
        assertThat(customerSaved.getName()).isEqualTo("TESTE1");
        assertThat(customerSaved.getAddress()).isEqualTo("Rua TESTE");
        assertThat(customerSaved.getDocument()).isEqualTo("868.343.810-40");
        assertThat(customerSaved.getBirthDate()).isEqualTo(LocalDate.of(200, 1, 10));
    }

    @Test
    public void update_shouldUpdateACustomer() {
        repository.findById(1)
            .ifPresent(base -> {
                assertThat(base.getId()).isEqualTo(1);
                assertThat(base.getName()).isEqualTo("GABRIEL");
            });

        final var customer = Customer.builder()
            .id(1)
            .name("TESTE1")
            .build();
        final var customerSaved = repository.save(customer);

        assertThat(customerSaved.getId()).isEqualTo(1);
        assertThat(customerSaved.getName()).isEqualTo("TESTE1");
    }

    @Test
    public void findAll_shouldReturnByFilters() {
        final var pageRequest = new PageRequest();
        final var customerFilters = new CustomerFilters();

        customerFilters.setName("GAbriela");
        final var collect = repository.findAll(customerFilters.toPredicate().build(), pageRequest)
            .get()
            .map(CustomerResponse::toCustomerResponse)
            .collect(Collectors.toList());

        assertThat(collect)
            .hasSize(2)
            .extracting("id", "name", "document")
            .containsExactlyInAnyOrder(
                tuple(4, "GABRIELA", "394.606.370-51"),
                tuple(5, "GABRIELA", "625.901.300-07")
            );
    }

    @Test
    public void findAll_shouldReturnAllCustomers() {
        final var pageRequest = new PageRequest();
        final var collect = repository.findAll(new CustomerFilters().toPredicate().build(), pageRequest)
            .get()
            .map(CustomerResponse::toCustomerResponse)
            .collect(Collectors.toList());

        assertThat(collect)
            .hasSize(5)
            .extracting("id", "name", "document")
            .containsExactlyInAnyOrder(
                tuple(1, "GABRIEL", "757.971.640-27"),
                tuple(2, "JOSE", "470.610.260-06"),
                tuple(3, "MARCIA", "551.236.060-41"),
                tuple(4, "GABRIELA", "394.606.370-51"),
                tuple(5, "GABRIELA", "625.901.300-07")
            );
    }
}

package br.com.customerapi.customer.dto;

import br.com.customerapi.customer.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CustomerResponseTest {

    @Test
    public void toCustomerResponse_shouldConvertCustomerIntoCustomerResponse(){
        final var customer = Customer.builder()
            .name("gabriel")
            .id(1)
            .document("1010101")
            .address("RUA A")
            .birthDate(LocalDate.of(1996, 1, 29))
            .createAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .build();

        final var response = CustomerResponse.toCustomerResponse(customer);

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getName()).isEqualTo("gabriel");
        assertThat(response.getAddress()).isEqualTo("RUA A");
        assertThat(response.getDocument()).isEqualTo("1010101");
        assertThat(response.getBirthDate()).isEqualTo(LocalDate.of(1996, 1, 29));

        final var age = ChronoUnit.YEARS.between(customer.getBirthDate(), LocalDate.now());
        assertThat(response.getAge()).isEqualTo(age);
    }
}

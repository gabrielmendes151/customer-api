package br.com.customerapi.customer.model;

import br.com.customerapi.customer.dto.CustomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTest {

    @Test
    public void toCustomer_shouldConvertCustomerRequestIntoCustomer(){
        final var customer = CustomerRequest.builder()
            .name("gabriel")
            .document("1010101")
            .address("RUA A")
            .birthDate(LocalDate.of(1996, 1, 29))
            .build();

        final var response = Customer.toCustomer(customer);

        assertThat(response.getName()).isEqualTo("gabriel");
        assertThat(response.getAddress()).isEqualTo("RUA A");
        assertThat(response.getDocument()).isEqualTo("1010101");
        assertThat(response.getBirthDate()).isEqualTo(LocalDate.of(1996, 1, 29));
    }
}

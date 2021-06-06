package br.com.customerapi.customer.dto;

import br.com.customerapi.customer.model.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Integer id;
    private String name;
    private String document;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    private String address;
    private Long age;

    public static CustomerResponse toCustomerResponse(final Customer customer){
        final var customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(customer, customerResponse);
        customerResponse.setAge(ChronoUnit.YEARS.between(customer.getBirthDate(), LocalDate.now()));
        return customerResponse;
    }
}

package br.com.customerapi.customer.dto;

import br.com.customerapi.customer.predicate.CustomerPredicate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFilters {

    private Integer id;
    private String name;
    private String document;

    public CustomerPredicate toPredicate() {
        return new CustomerPredicate()
            .withId(id)
            .withName(name)
            .withDocument(document);
    }
}



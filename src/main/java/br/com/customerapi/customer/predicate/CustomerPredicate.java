package br.com.customerapi.customer.predicate;

import static br.com.customerapi.customer.model.QCustomer.customer;

public class CustomerPredicate extends PredicateBase {

    public CustomerPredicate withId(final Integer id) {
        if (isNotEmpty(id)) {
            builder.and(customer.id.eq(id));
        }
        return this;
    }

    public CustomerPredicate withName(final String name) {
        if (isNotEmpty(name)) {
            builder.and(customer.name.equalsIgnoreCase(name));
        }
        return this;
    }

    public CustomerPredicate withDocument(final String document) {
        if (isNotEmpty(document)) {
            builder.and(customer.document.eq(document));
        }
        return this;
    }
}

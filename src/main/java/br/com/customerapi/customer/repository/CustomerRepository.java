package br.com.customerapi.customer.repository;

import br.com.customerapi.customer.model.Customer;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer>,
    QuerydslPredicateExecutor<Customer> {
}

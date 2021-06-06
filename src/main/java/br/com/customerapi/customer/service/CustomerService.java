package br.com.customerapi.customer.service;

import br.com.customerapi.customer.dto.CustomerFilters;
import br.com.customerapi.customer.dto.CustomerRequest;
import br.com.customerapi.customer.dto.CustomerResponse;
import br.com.customerapi.customer.dto.PageRequest;
import br.com.customerapi.customer.model.Customer;
import br.com.customerapi.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public void save(final CustomerRequest request){
        repository.save(Customer.toCustomer(request));
    }

    public void upadte(final Integer customerId, final CustomerRequest request)  {
        final var customer = repository.findById(customerId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found."));
        BeanUtils.copyProperties(request, customer);
        repository.save(customer);
    }

    public List<CustomerResponse> getAll(final PageRequest pageable, final CustomerFilters filters){
        return repository.findAll(filters.toPredicate().build(), pageable)
            .map(CustomerResponse::toCustomerResponse)
            .get()
            .collect(Collectors.toList());
    }
}

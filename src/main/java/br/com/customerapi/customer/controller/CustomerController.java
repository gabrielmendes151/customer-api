package br.com.customerapi.customer.controller;

import br.com.customerapi.customer.dto.CustomerFilters;
import br.com.customerapi.customer.dto.CustomerRequest;
import br.com.customerapi.customer.dto.CustomerResponse;
import br.com.customerapi.customer.dto.PageRequest;
import br.com.customerapi.customer.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Buscar cliente paginados com filtros")
    @ApiResponse(code = 200, message = "Cliente criado com sucesso", response = CustomerResponse.class)
    public List<CustomerResponse> findAll(PageRequest pageable, CustomerFilters filters) {
        return service.getAll(pageable, filters);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Criar um cliente na base")
    @ApiResponse(code = 201, message = "Cliente criado com sucesso")
    public void save(@ApiParam(name = "request", value = "Informações do cliente", required = true)
                     @RequestBody @Valid CustomerRequest request) {
        service.save(request);
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Atualizar um cliente na base")
    @ApiResponse(code = 204, message = "Cliente atualizado com sucesso")
    public void update(@ApiParam(name = "customerId", value = "Id do cliente", required = true)
                       @PathVariable("customerId") Integer customerId,
                       @ApiParam(name = "request", value = "Informações do cliente", required = true)
                       @RequestBody @Valid CustomerRequest request) {
        service.upadte(customerId, request);
    }
}

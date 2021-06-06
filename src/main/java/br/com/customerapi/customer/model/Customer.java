package br.com.customerapi.customer.model;

import br.com.customerapi.customer.dto.CustomerRequest;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMERS")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @CreationTimestamp
    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DOCUMENT", nullable = false, length = 14, unique = true)
    private String document;

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate birthDate;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    public static Customer toCustomer(final CustomerRequest request) {
        final var customer = new Customer();
        BeanUtils.copyProperties(request, customer);
        return customer;
    }
}



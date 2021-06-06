package br.com.customerapi.customer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotNull(message = "Nome é obrigatório")
    @ApiModelProperty(value = "Nome", required = true)
    private String name;

    @NotNull(message = "Documento é obrigatório")
    @Size(max = 14, min = 14, message = "Informe um cpf válido com máscara")
    @ApiModelProperty(value = "Documento", example = "263.520.340-71", required = true)
    private String document;

    @NotNull(message = "Data de nascimento é obrigatório")
    @ApiModelProperty(value = "Data de nascimento", example = "10-01-1996", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @NotNull(message = "Endereço é obrigatório")
    @ApiModelProperty(value = "Endereço", required = true)
    private String address;
}

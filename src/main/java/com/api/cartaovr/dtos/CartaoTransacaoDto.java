package com.api.cartaovr.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CartaoTransacaoDto {

    @NotBlank
    private String numero;
    @NotBlank
    private String senha;
    @NotNull
    private BigDecimal valor;

}

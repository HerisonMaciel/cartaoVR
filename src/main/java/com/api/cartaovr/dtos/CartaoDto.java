package com.api.cartaovr.dtos;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class CartaoDto {

    @NotBlank
    private String numero;
    @NotBlank
    private String senha;

}

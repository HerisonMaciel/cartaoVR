package com.api.cartaovr.dtos;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class CartaoDto {

    @NotBlank
    private String numero;
    @NotBlank
    private String senha;

}

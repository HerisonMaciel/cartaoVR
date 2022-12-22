package com.api.cartaovr;

import com.api.cartaovr.dtos.CartaoDto;
import com.api.cartaovr.models.CartaoModel;
import com.api.cartaovr.repositories.CartaoRepository;
import com.api.cartaovr.services.CartaoService;
import com.api.cartaovr.util.ValidacaoUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("CartaoTeste")
public class CartaoTeste extends CartaoVrApplicationTests{

    @MockBean
    CartaoRepository cartaoRepository;

    @MockBean
    PasswordEncoder encoder;

    @MockBean
    ValidacaoUtil validacaoUtil;

    @Autowired
    CartaoService cartaoService;

    @Test
    @DisplayName("")
    public void deveSalvarUmCartao(){
        CartaoModel cartaoModel = Mockito.mock(CartaoModel.class);
        Mockito.when(cartaoRepository.save(cartaoModel)).thenReturn(new CartaoModel());

    }

}

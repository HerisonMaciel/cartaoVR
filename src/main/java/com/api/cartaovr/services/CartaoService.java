package com.api.cartaovr.services;

import com.api.cartaovr.dtos.CartaoDto;
import com.api.cartaovr.models.CartaoModel;
import com.api.cartaovr.repositories.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    public Object save(CartaoDto cartaoDto){
        cartaoDto.setSenha(encoder.encode(cartaoDto.getSenha()));
        return cartaoRepository.save(criacaoCartao(cartaoDto));
    }

    @Transactional
    public Optional<CartaoModel> findByNumero(String numero){
        return cartaoRepository.findByNumero(numero);
    }

    private CartaoModel criacaoCartao(CartaoDto cartaoDto){
        var cartaoModel = copiarPropriedades(cartaoDto);
        cartaoModel.setSaldo(BigDecimal.valueOf(500.00));
        cartaoModel.setDataDeCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return cartaoModel;
    }

    public boolean cartaoExiste(String numero) {
        return cartaoRepository.existsByNumero(numero.replace(" ", ""));
    }

    private CartaoModel copiarPropriedades(CartaoDto cartaoDto){
        var cartaoModel = new CartaoModel();
        cartaoModel.setNumero(cartaoDto.getNumero().replace(" ", ""));
        cartaoModel.setSenha(cartaoDto.getSenha());
        return cartaoModel;
    }


}

package com.api.cartaovr.services;

import com.api.cartaovr.dtos.CartaoDto;
import com.api.cartaovr.models.CartaoModel;
import com.api.cartaovr.repositories.CartaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class CartaoService {

    @Autowired
    CartaoRepository cartaoRepository;

    @Transactional
    public CartaoModel save(CartaoDto cartaoDto){
        //var cartaoModel = new CartaoModel();
        //BeanUtils.copyProperties(cartaoDto, cartaoModel);
        //cartaoModel.setDataDeCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        //System.out.println(cartaoModel.getDataDeCriacao());
        //criacaoCartao(cartaoDto);
        return cartaoRepository.save(criacaoCartao(cartaoDto));
    }

    private CartaoModel criacaoCartao(CartaoDto cartaoDto){
        var cartaoModel = new CartaoModel();
        BeanUtils.copyProperties(cartaoDto, cartaoModel);
        cartaoModel.setSaldo(BigDecimal.valueOf(500.00));
        cartaoModel.setDataDeCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return cartaoModel;
    }


}

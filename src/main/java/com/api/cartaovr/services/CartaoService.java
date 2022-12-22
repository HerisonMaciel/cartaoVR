package com.api.cartaovr.services;

import com.api.cartaovr.dtos.CartaoDto;
import com.api.cartaovr.dtos.CartaoTransacaoDto;
import com.api.cartaovr.models.CartaoModel;
import com.api.cartaovr.repositories.CartaoRepository;
import com.api.cartaovr.util.ValidacaoUtil;
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

    @Autowired
    ValidacaoUtil validacaoUtil;

    @Transactional
    public Object save(CartaoDto cartaoDto){
        cartaoDto.setSenha(encoder.encode(cartaoDto.getSenha()));
        return cartaoRepository.save(criacaoCartao(cartaoDto));
    }

    public Optional<CartaoModel> findByNumero(String numero){
        return cartaoRepository.findByNumero(numero);
    }

    @Transactional
    public Optional<CartaoModel> transacao(CartaoTransacaoDto cartaoTransacaoDto){
        Optional<CartaoModel> cartaoModelOptional = cartaoRepository.findByNumero(tiraEspaco(cartaoTransacaoDto.getNumero()));
        if(cartaoModelOptional.isEmpty()){
            return cartaoModelOptional;
        }

        BigDecimal valor = debitar(cartaoModelOptional.get().getSaldo(), cartaoTransacaoDto.getValor());
        if(valor.compareTo(new BigDecimal(0)) < 0){
            return cartaoModelOptional;
        }
        cartaoModelOptional.get().setSaldo(valor);
        cartaoRepository.save(cartaoModelOptional.get());
        return cartaoModelOptional;
    }

    public boolean cartaoExiste(String numero) {
        return cartaoRepository.existsByNumero(tiraEspaco(numero));
    }

    private CartaoModel criacaoCartao(CartaoDto cartaoDto){
        var cartaoModel = copiarPropriedadesCartao(cartaoDto);
        cartaoModel.setSaldo(BigDecimal.valueOf(500.00));
        cartaoModel.setDataDeCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return cartaoModel;
    }

    private CartaoModel copiarPropriedadesCartao(CartaoDto cartaoDto){
        var cartaoModel = new CartaoModel();
        cartaoModel.setNumero(tiraEspaco(cartaoDto.getNumero()));
        cartaoModel.setSenha(cartaoDto.getSenha());
        return cartaoModel;
    }

    private String tiraEspaco(String numero){
        return numero.replace(" ", "");
    }

    private BigDecimal debitar(BigDecimal atual, BigDecimal valor){
        BigDecimal resultado = atual.subtract(valor);
        return resultado;
    }

    private Optional<CartaoModel> copiarPropriedadesTransacao(CartaoTransacaoDto cartaoTransacaoDto, Optional<CartaoModel> cartaoModelOptional){
        BigDecimal valor = debitar(cartaoModelOptional.get().getSaldo(), cartaoTransacaoDto.getValor());
        if(valor.compareTo(new BigDecimal(0)) < 0){
            return null; //Transação não autorizada
        }
        cartaoModelOptional.get().setNumero(tiraEspaco(cartaoTransacaoDto.getNumero()));
        cartaoModelOptional.get().setSenha(cartaoTransacaoDto.getSenha());
        cartaoModelOptional.get().setSaldo(valor);
        return cartaoModelOptional;
    }

}

package com.api.cartaovr.controllers;

import com.api.cartaovr.dtos.CartaoDto;
import com.api.cartaovr.dtos.CartaoTransacaoDto;
import com.api.cartaovr.models.CartaoModel;
import com.api.cartaovr.services.CartaoService;
import com.api.cartaovr.util.ValidacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;


@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    CartaoService cartaoService;

    @Autowired
    ValidacaoUtil validacaoUtil;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<Object> salvarCartao(@Valid @RequestBody CartaoDto cartaoDto){
        if(!validacaoUtil.verificacaoNumeros(cartaoDto.getNumero()))
            return ResponseEntity.unprocessableEntity().body("Numero de cartão invalido!");
        if(cartaoService.cartaoExiste(cartaoDto.getNumero()))
            return ResponseEntity.unprocessableEntity().body("Cartão já cadastrado!");

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cartaoService.save(cartaoDto));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<Object> consultarSaldo(@PathVariable(value = "numeroCartao") String numeroCartao){
        Optional<CartaoModel> cartaoModelOptional = cartaoService.findByNumero(numeroCartao);
        if (!cartaoModelOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().body("CARTAO_INEXISTENTE");
        }
        return ResponseEntity.status(HttpStatus.OK).body(cartaoModelOptional.get().getSaldo());
    }

    @PostMapping("/transacoes")
    public ResponseEntity<Object> transacao(@Valid @RequestBody CartaoTransacaoDto cartaoTransacaoDto){
        if(!validacaoUtil.verificacaoNumeros(cartaoTransacaoDto.getNumero()))
            return ResponseEntity.unprocessableEntity().body("Numero de cartão invalido!");


        Optional<CartaoModel> cartaoModelOptional = cartaoService.transacao(cartaoTransacaoDto);
        if (!cartaoModelOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().body("CARTAO_INEXISTENTE");
        }
        if(!encoder.matches(cartaoTransacaoDto.getSenha(), cartaoModelOptional.get().getSenha())){
            return ResponseEntity.unprocessableEntity().body("SENHA_INVALIDA");
        }
        if(cartaoModelOptional.get().getSaldo().compareTo(cartaoTransacaoDto.getValor()) < 0){
            return ResponseEntity.unprocessableEntity().body("SALDO_INSUFICIENTE");
        }
        return ResponseEntity.status(HttpStatus.OK).build();

        /*Optional<CartaoModel> cartaoModelOptional = cartaoService.findByNumero(cartaoTransacaoDto.getNumero());
        if (cartaoModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }


        CartaoModel cartao = cartaoModelOptional.get();
        boolean valid = encoder.matches(password, usuario.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);*/

    }


}

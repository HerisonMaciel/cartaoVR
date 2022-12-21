package com.api.cartaovr.controllers;

import com.api.cartaovr.dtos.CartaoDto;
import com.api.cartaovr.models.CartaoModel;
import com.api.cartaovr.services.CartaoService;
import com.api.cartaovr.util.ValidacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    CartaoService cartaoService;

    @Autowired
    ValidacaoUtil validacaoUtil;

    @PostMapping
    public ResponseEntity<Object> salvarCartao(@Valid @RequestBody CartaoDto cartaoDto){
        if(!validacaoUtil.verificacaoNumeros(cartaoDto.getNumero()))
            return ResponseEntity.unprocessableEntity().body("Numero de cartão invalido!");
        if(cartaoService.cartaoExiste(cartaoDto.getNumero())){
            return ResponseEntity.unprocessableEntity().body("Cartão já cadastrado!");
        }

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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(cartaoModelOptional.get().getSaldo());
    }


}

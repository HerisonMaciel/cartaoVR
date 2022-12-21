package com.api.cartaovr.controllers;

import com.api.cartaovr.dtos.CartaoDto;
import com.api.cartaovr.models.CartaoModel;
import com.api.cartaovr.services.CartaoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<Object> salvarCartao(@Valid @RequestBody CartaoDto cartaoDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cartaoService.save(cartaoDto));
        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().build();
        }
    }


}

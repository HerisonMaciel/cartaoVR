package com.api.cartaovr.repositories;

import com.api.cartaovr.models.CartaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartaoRepository extends JpaRepository<CartaoModel, UUID> {

    public Optional<CartaoModel> findByNumero(String numero);

    public Boolean existsByNumero(String numero);
}

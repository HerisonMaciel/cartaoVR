package com.api.cartaovr.repositories;

import com.api.cartaovr.models.CartaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartaoRepository extends JpaRepository<CartaoModel, UUID> {


    public Optional<CartaoModel> findByNumero(String numero);

    //@Lock(LockModeType.PESSIMISTIC_READ)
    //public Optional<CartaoModel> save(Optional<CartaoModel> cartaoModel);

    public Boolean existsByNumero(String numero);
}

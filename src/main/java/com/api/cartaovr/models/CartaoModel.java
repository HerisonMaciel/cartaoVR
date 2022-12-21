package com.api.cartaovr.models;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name ="TB_CARTAO")
@Data
public class CartaoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 16)
    private String numero;

    @Column(nullable = false, length = 50)
    private String senha;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo;

    @Column(nullable = false)
    private LocalDateTime dataDeCriacao;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean bloqueado;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean cancelado;

}

package com.arterodevops.algafood.domain.model;

import com.arterodevops.algafood.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {

    @NotNull(groups = Groups.CidadeId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(nullable = false)
    private Estado estado;

}

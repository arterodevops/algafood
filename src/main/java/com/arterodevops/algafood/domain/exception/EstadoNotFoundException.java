package com.arterodevops.algafood.domain.exception;

public class EstadoNotFoundException extends EntityAlgafoodNotFoundException {

    private static final long serialVersionUID = 1L;

    public EstadoNotFoundException(String mensagem) {
        super(mensagem);
    }

    public EstadoNotFoundException(Long estadoId) {
        this(String.format("Não existe um cadastro de estado com código %d", estadoId));
    }
}

package com.arterodevops.algafood.domain.exception;

public class RestauranteNotFoundException extends EntityAlgafoodNotFoundException {

    private static final long serialVersionUID = 1L;

    public RestauranteNotFoundException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNotFoundException(Long restauranteId) {
        this(String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
    }
}

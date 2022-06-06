package com.arterodevops.algafood.domain.exception;

public class CidadeNotFoundException extends EntityAlgafoodNotFoundException {

    private static final long serialVersionUID = 1L;

    public CidadeNotFoundException(String mensagem) {
        super(mensagem);
    }

    public CidadeNotFoundException(Long cozinhaId) {
        this(String.format("Não existe um cadastro de cidade com código %d", cozinhaId));
    }
}

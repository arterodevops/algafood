package com.arterodevops.algafood.domain.exception;

public class CozinhaNotFoundException extends EntityAlgafoodNotFoundException {

    private static final long serialVersionUID = 1L;

    public CozinhaNotFoundException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNotFoundException(Long cozinhaId) {
        this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
    }
}

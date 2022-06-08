package com.arterodevops.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum AlgafoodExceptionType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    MENSAGEM_INCOMPREENSIVEL("mensagem-incompreensivel", "Mensagem incompreensível" );

    private String title;
    private String uri;

    AlgafoodExceptionType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
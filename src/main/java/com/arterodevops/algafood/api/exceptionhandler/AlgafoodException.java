package com.arterodevops.algafood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AlgafoodException {

    private LocalDateTime dataHora;
    private String mensagem;

}

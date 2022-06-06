package com.arterodevops.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract  class EntityAlgafoodNotFoundException extends NegocioException {
    public EntityAlgafoodNotFoundException(String reason) {
        super(reason);
    }
}

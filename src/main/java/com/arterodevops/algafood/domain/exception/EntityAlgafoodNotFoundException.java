package com.arterodevops.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityAlgafoodNotFoundException extends RuntimeException {
    public EntityAlgafoodNotFoundException(String reason) {
        super(reason);
    }
}

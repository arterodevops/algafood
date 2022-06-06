package com.arterodevops.algafood.api.exceptionhandler;

import com.arterodevops.algafood.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> negocioExceptionHandler(NegocioException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getAlgafoodException(e));

    }

    @ExceptionHandler(RestauranteNotFoundException.class)
    public ResponseEntity<?> restauranteNotFoundExceptionHandler(RestauranteNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(getAlgafoodException(e));

    }

    @ExceptionHandler(CozinhaNotFoundException.class)
    public ResponseEntity<?> cozinhaNotFoundExceptionHandler(CozinhaNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(getAlgafoodException(e));

    }

    @ExceptionHandler(CidadeNotFoundException.class)
    public ResponseEntity<?> cidadeNotFoundExceptionHandler(CidadeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(getAlgafoodException(e));

    }

    @ExceptionHandler(EntityAlgafoodNotFoundException.class)
    public ResponseEntity<?> entityAlgafoodNotFoundExceptionHandler(EntityAlgafoodNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(getAlgafoodException(e));

    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> entityInUseExceptionHandler(EntityInUseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(getAlgafoodException(e));

    }

    @ExceptionHandler(EstadoNotFoundException.class)
    public ResponseEntity<?> EstadoNotFoundExceptionHandler(EstadoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(getAlgafoodException(e));

    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> tratarHttpMediaTypeNotSupportedException() {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(getAlgafoodException("O tipo de mídia não é aceito."));
    }

    private AlgafoodException getAlgafoodException(Exception e) {
        return getAlgafoodException(e.getMessage());
    }

    private AlgafoodException getAlgafoodException(String message) {
        AlgafoodException exception = AlgafoodException.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(message)
                .build();
        return exception;
    }


}

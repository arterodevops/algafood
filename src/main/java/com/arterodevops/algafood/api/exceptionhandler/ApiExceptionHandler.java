package com.arterodevops.algafood.api.exceptionhandler;

import com.arterodevops.algafood.domain.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    WebRequest request;

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> negocioExceptionHandler(NegocioException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getAlgafoodException(e));

    }

    @ExceptionHandler(RestauranteNotFoundException.class)
    public ResponseEntity<?> restauranteNotFoundExceptionHandler(RestauranteNotFoundException e) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);

    }

    @ExceptionHandler(CozinhaNotFoundException.class)
    public ResponseEntity<?> cozinhaNotFoundExceptionHandler(CozinhaNotFoundException e) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);

    }

    @ExceptionHandler(CidadeNotFoundException.class)
    public ResponseEntity<?> cidadeNotFoundExceptionHandler(CidadeNotFoundException e) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EntityAlgafoodNotFoundException.class)
    public ResponseEntity<?> entityAlgafoodNotFoundExceptionHandler(EntityAlgafoodNotFoundException e) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> entityInUseExceptionHandler(EntityInUseException e) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(EstadoNotFoundException.class)
    public ResponseEntity<?> EstadoNotFoundExceptionHandler(EstadoNotFoundException e) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = getAlgafoodException(status.getReasonPhrase());
        } else if (body instanceof String) {
            body = getAlgafoodException((String) body);
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
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

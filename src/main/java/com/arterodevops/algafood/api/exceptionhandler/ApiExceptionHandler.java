package com.arterodevops.algafood.api.exceptionhandler;

import com.arterodevops.algafood.domain.exception.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    WebRequest request;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }

        AlgafoodExceptionType problemType = AlgafoodExceptionType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        AlgafoodException algafoodException = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, algafoodException, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        AlgafoodExceptionType problemType = AlgafoodExceptionType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        AlgafoodException algafoodException = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, algafoodException, headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> negocioExceptionHandler(NegocioException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        AlgafoodExceptionType algafoodExceptionType = AlgafoodExceptionType.ERRO_NEGOCIO;
        String detail = e.getMessage();

        AlgafoodException algafoodException = createProblemBuilder(status, algafoodExceptionType, detail).build();

        return handleExceptionInternal(e, algafoodException, new HttpHeaders(), status, request);

    }

    @ExceptionHandler(RestauranteNotFoundException.class)
    public ResponseEntity<?> restauranteNotFoundExceptionHandler(RestauranteNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        AlgafoodExceptionType algafoodExceptionType = AlgafoodExceptionType.ENTIDADE_NAO_ENCONTRADA;
        String detail = e.getMessage();

        AlgafoodException algafoodException = createProblemBuilder(status, algafoodExceptionType, detail).build();

        return handleExceptionInternal(e, algafoodException, new HttpHeaders(), status, request);

    }

    @ExceptionHandler(CozinhaNotFoundException.class)
    public ResponseEntity<?> cozinhaNotFoundExceptionHandler(CozinhaNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        AlgafoodExceptionType algafoodExceptionType = AlgafoodExceptionType.ENTIDADE_NAO_ENCONTRADA;
        String detail = e.getMessage();

        AlgafoodException algafoodException = createProblemBuilder(status, algafoodExceptionType, detail).build();

        return handleExceptionInternal(e, algafoodException, new HttpHeaders(), status, request);

    }

    @ExceptionHandler(CidadeNotFoundException.class)
    public ResponseEntity<?> cidadeNotFoundExceptionHandler(CidadeNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        AlgafoodExceptionType algafoodExceptionType = AlgafoodExceptionType.ENTIDADE_NAO_ENCONTRADA;
        String detail = e.getMessage();

        AlgafoodException algafoodException = createProblemBuilder(status, algafoodExceptionType, detail).build();

        return handleExceptionInternal(e, algafoodException, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityAlgafoodNotFoundException.class)
    public ResponseEntity<?> entityAlgafoodNotFoundExceptionHandler(EntityAlgafoodNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        AlgafoodExceptionType algafoodExceptionType = AlgafoodExceptionType.ENTIDADE_NAO_ENCONTRADA;
        String detail = e.getMessage();

        AlgafoodException algafoodException = createProblemBuilder(status, algafoodExceptionType, detail).build();

        return handleExceptionInternal(e, algafoodException, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> entityInUseExceptionHandler(EntityInUseException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        AlgafoodExceptionType algafoodExceptionType = AlgafoodExceptionType.ENTIDADE_EM_USO;
        String detail = e.getMessage();

        AlgafoodException algafoodException = createProblemBuilder(status, algafoodExceptionType, detail).build();

        return handleExceptionInternal(e, algafoodException, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EstadoNotFoundException.class)
    public ResponseEntity<?> EstadoNotFoundExceptionHandler(EstadoNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        AlgafoodExceptionType algafoodExceptionType = AlgafoodExceptionType.ENTIDADE_NAO_ENCONTRADA;
        String detail = e.getMessage();

        AlgafoodException algafoodException = createProblemBuilder(status, algafoodExceptionType, detail).build();

        return handleExceptionInternal(e, algafoodException, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = AlgafoodException.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = AlgafoodException.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private AlgafoodException.AlgafoodExceptionBuilder createProblemBuilder(HttpStatus status, AlgafoodExceptionType problemType, String detail) {

        return AlgafoodException.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }


}

package com.arterodevops.algafood.api.exceptionhandler;

import com.arterodevops.algafood.domain.exception.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    WebRequest request;

    @Autowired
    MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //return super.handleMethodArgumentNotValid(ex, headers, status, request);
        AlgafoodExceptionType problemType = AlgafoodExceptionType.MENSAGEM_INCOMPREENSIVEL;
        String detail = ex.getMessage();

        List<AlgafoodException.Field> fields = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {

                            String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                            return AlgafoodException.Field.builder()
                                    .name(fieldError.getField())
                                    .userMessage(message)
                                    .build();
                        }
                )
                .collect(Collectors.toList());

        AlgafoodException algafoodException = createProblemBuilder(status, problemType, detail)
                .userMessage("Um ou mais campos est??o inv??lidos. Fa??a o preenchimento correto e tente novamente.")
                .fields(fields)
                .build();

        return handleExceptionInternal(ex, algafoodException, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        AlgafoodExceptionType problemType = AlgafoodExceptionType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisi????o est?? inv??lido. Verifique erro de sintaxe.";

        AlgafoodException algafoodException = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, algafoodException, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        // Criei o m??todo joinPath para reaproveitar em todos os m??todos que precisam
        // concatenar os nomes das propriedades (separando por ".")
        String path = joinPath(ex.getPath());

        AlgafoodExceptionType algafoodExceptionType = AlgafoodExceptionType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' n??o existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        AlgafoodException algafoodException = createProblemBuilder(status, algafoodExceptionType, detail).build();

        return handleExceptionInternal(ex, algafoodException, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        AlgafoodExceptionType problemType = AlgafoodExceptionType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
                        + "que ?? de um tipo inv??lido. Corrija e informe um valor compat??vel com o tipo %s.",
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
    public ResponseEntity<?> estadoNotFoundExceptionHandler(EstadoNotFoundException e) {

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

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        AlgafoodExceptionType algafoodExceptionType = AlgafoodExceptionType.PARAMETRO_INVALIDO;

        String detail = String.format("O par??metro de URL '%s' recebeu o valor '%s', "
                        + "que ?? de um tipo inv??lido. Corrija e informe um valor compat??vel com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        AlgafoodException algafoodException = createProblemBuilder(status, algafoodExceptionType, detail).build();

        return handleExceptionInternal(ex, algafoodException, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        AlgafoodExceptionType algafoodExceptionType = AlgafoodExceptionType.ERRO_DE_SISTEMA;
        String detail = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";

        // Importante colocar o printStackTrace (pelo menos por enquanto, que n??o estamos
        // fazendo logging) para mostrar a stacktrace no console
        // Se n??o fizer isso, voc?? n??o vai ver a stacktrace de exceptions que seriam importantes
        // para voc?? durante, especialmente na fase de desenvolvimento
        ex.printStackTrace();

        AlgafoodException algafoodException = createProblemBuilder(status, algafoodExceptionType, detail).build();

        return handleExceptionInternal(ex, algafoodException, new HttpHeaders(), status, request);
    }


}

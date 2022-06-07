package com.arterodevops.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class AlgafoodException {

    private Integer status;
    private String type;
    private String title;
    private String detail;

}

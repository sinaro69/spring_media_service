package com.developerscambodia.devkhmediaservice.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasedResponse<T> {

    private Status status;
    private T payload;
    private Object errors;
    private Object metaData;

    public enum Status {
        OK, BAD_REQUEST, UNAUTHORIZED, VALIDATION_EXCEPTION, EXCEPTION, WRONG_CREDENTIALS,
        ACCESS_DENIED, METHOD_NOT_ALLOWED
    }

}
package com.faspix.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
class ExceptionResponse {

    private final String message;

}

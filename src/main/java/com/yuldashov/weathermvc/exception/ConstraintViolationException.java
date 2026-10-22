package com.yuldashov.weathermvc.exception;

public class ConstraintViolationException extends RuntimeException {
    public ConstraintViolationException() {
        super("Cookie session пустые");
    }
}

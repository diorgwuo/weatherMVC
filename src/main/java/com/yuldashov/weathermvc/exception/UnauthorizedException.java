package com.yuldashov.weathermvc.exception;

public class UnauthorizedException extends  RuntimeException{
    public UnauthorizedException() {
        super("Вы не авторизованы");
    }
}

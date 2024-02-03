package com.training.tinelli.sales.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("A senha é inválida.");
    }
}

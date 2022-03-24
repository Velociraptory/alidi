package ru.alidi.market.exception;

public class WrongInputDataException extends Exception {

    public WrongInputDataException(String message) {
        super(message);
    }

    public WrongInputDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

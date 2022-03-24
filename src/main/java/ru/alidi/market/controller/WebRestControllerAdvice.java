package ru.alidi.market.controller;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.alidi.market.dto.common.ApiResponse;
import ru.alidi.market.dto.common.ResponseCode;
import ru.alidi.market.exception.WrongInputDataException;

import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class WebRestControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(WebRestControllerAdvice.class);

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletResponse response) {
        String description =
                e.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(WebRestControllerAdvice::getErrorDescription)
                        .collect(Collectors.joining(", "));
        return getValidationErrorResponse(description, response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletResponse response) {
        String description = "Несоответствие передаваемого и принимаемого типов";
        return getValidationErrorResponse(description, response);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ApiResponse handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpServletResponse response) {
        logger.debug(e.getMessage());

        String description;

        Throwable rootException = Throwables.getRootCause(e);
        if (rootException instanceof DateTimeParseException)
            description = "Ошибка при чтении JSON: неверный формат даты";
        else
            description = "Ошибка при чтении JSON";

        return getValidationErrorResponse(description, response);
    }

    @ExceptionHandler(WrongInputDataException.class)
    public ApiResponse handleWrongInputDataException(WrongInputDataException ex, HttpServletResponse response) {
        return getValidationErrorResponse(ex.getMessage(), response);
    }

    @ExceptionHandler({Throwable.class})
    public ApiResponse handleThrowable(Throwable e, HttpServletResponse response) {
        return getInternalErrorResponse(e, response);
    }

    private static ApiResponse getValidationErrorResponse(String description, HttpServletResponse response) {
        logger.debug(description);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ApiResponse.failure(ResponseCode.VALIDATION_ERROR, description);
    }

    private static ApiResponse getInternalErrorResponse(Throwable e, HttpServletResponse response) {
        logger.error("Ошибка API", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ApiResponse.failure(ResponseCode.INTERNAL_ERROR, "Внутренняя ошибка сервера");
    }

    private static String getErrorDescription(ObjectError objectError) {
        if (objectError instanceof FieldError) {
            FieldError fieldError = (FieldError) objectError;
            return fieldError.getField() + ": " + fieldError.getDefaultMessage();
        } else {
            return objectError.getDefaultMessage();
        }
    }
}

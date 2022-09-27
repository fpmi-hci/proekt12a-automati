package com.readme.api.rest.handler;

import com.readme.api.service.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
@AllArgsConstructor
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage userAuthException(AccessDeniedException e, WebRequest webRequest) {
        LOGGER.error(e.getMessage(), e);
        int errorCode = HttpStatus.FORBIDDEN.value();
        String message = e.getLocalizedMessage();
        return new ErrorMessage(
                errorCode,
                LocalDateTime.now(),
                message,
                webRequest.getDescription(false)
        );
    }

    @ExceptionHandler(value = InternalAuthenticationServiceException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage authError(Exception e, WebRequest request) {
        String message = e.getCause().getMessage();
        LOGGER.error(message, e);
        int errorCode = HttpStatus.UNAUTHORIZED.value();
        return new ErrorMessage(
                errorCode,
                LocalDateTime.now(),
                message,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {ConstraintViolationException.class,})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<ErrorMessage> constraintViolationException(ConstraintViolationException e, WebRequest request) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        return constraintViolations.stream().map(constraintViolation -> {
            String message = e.getMessage();
            return new ErrorMessage(
                    HttpStatus.BAD_REQUEST.value(),
                    LocalDateTime.now(),
                    message,
                    request.getDescription(false)
            );
        }).collect(toList());
    }


    @ExceptionHandler(value = {BindException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<ErrorMessage> methodArgumentNotValidException(BindException e, WebRequest request) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        LOGGER.error("Exception: {}", allErrors, e);
        return allErrors.stream()
                .map(x -> {
                            Object[] arguments = x.getArguments();
                            String message = x.getDefaultMessage();
                            return new ErrorMessage(
                                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), message, request.getDescription(false));
                        }
                ).collect(toList());
    }


    @ExceptionHandler(value = {
            NotFoundException.class
    })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage objectNotFoundException(NotFoundException e, WebRequest request) {
        Object[] params = e.getMessageParams();
        LOGGER.error(e.getMessage(), e);
        String localizedMessage = e.getMessage();
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                localizedMessage,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage internalServerError(Exception e, WebRequest request) {
        LOGGER.error(e.getMessage(), e);
        int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String localizedMessage = e.getMessage();
        return new ErrorMessage(
                errorCode,
                LocalDateTime.now(),
                localizedMessage,
                request.getDescription(false)
        );
    }


}

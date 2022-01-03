package com.gl.userservice.exception.handler;

import com.gl.userservice.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserServiceExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errors = fieldErrors
                .stream()
                .map(err -> err.getField() + ":" + err.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = new ApiError();
        apiError.setErrors(errors);
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setPath(request.getDescription(false));

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
//
//    @ExceptionHandler({ConstraintViolationException.class})
//    public ResponseEntity<Object> handleConstraintViolation(
//            ConstraintViolationException ex, ServletWebRequest request) {
//
//        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
//        List<String> errors = constraintViolations
//                .stream()
//                .map(err -> err.getRootBeanClass().getName() + " " +
//                        err.getPropertyPath() + ": " + err.getMessage())
//                .collect(Collectors.toList());
//
//        ApiError apiError = new ApiError();
//        apiError.setErrors(errors);
//        apiError.setStatus(HttpStatus.BAD_REQUEST);
//        apiError.setPath(request.getRequest().getRequestURI());
//        return new ResponseEntity<>(
//                apiError, new HttpHeaders(), apiError.getStatus());
//    }
}

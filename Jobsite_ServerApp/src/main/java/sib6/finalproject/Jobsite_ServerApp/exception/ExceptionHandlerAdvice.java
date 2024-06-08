package sib6.finalproject.Jobsite_ServerApp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sib6.finalproject.Jobsite_ServerApp.model.response.ErrorResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .url(request.getDescription(false).replace("uri=", ""))
                .status(HttpStatus.BAD_REQUEST.toString())
                .details(details)
                .build();
        errorResponse.setTimestamp(new Date());

        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<Object> handleBadRequestException(ResponseStatusException ex, WebRequest request) {
        List<String> details = new ArrayList<>();

        String message = ex.getReason();
        if (message != null) {
            details.add(message);
        } else {
            details.add(ex.getMessage());
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .url(request.getDescription(false).replace("uri=", ""))
                .status(ex.getStatus().toString())
                .details(details)
                .build();
        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

}

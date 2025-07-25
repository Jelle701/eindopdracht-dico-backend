package com.example_jelle.backenddico.exceptions;

import com.example_jelle.backenddico.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * A central place for handling exceptions across the entire application.
 * This class intercepts exceptions thrown by controllers and translates them
 * into consistent, user-friendly HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles the specific exception when a user tries to register with an email that already exists.
     * @return A 409 Conflict response.
     */
    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<MessageResponse> handleEmailAlreadyExistsException(EmailAlreadyExists ex, WebRequest request) {
        MessageResponse messageResponse = new MessageResponse(ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles exceptions related to invalid or expired verification tokens/codes.
     * @return A 400 Bad Request response.
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<MessageResponse> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
        MessageResponse messageResponse = new MessageResponse(ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors from @Valid annotations on controller method arguments.
     * @return A 400 Bad Request response with a map of fields and their error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * A fallback handler for any other unhandled exceptions.
     * This prevents stack traces from being exposed to the client.
     * @return A 500 Internal Server Error response with a generic message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleAllOtherExceptions(Exception ex, WebRequest request) {
        // Log the full exception for debugging purposes.
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        MessageResponse messageResponse = new MessageResponse("An internal server error occurred. Please try again later.");
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
package com.nikesh.musicplaylistapi.exception;

import com.nikesh.musicplaylistapi.dto.response.error.ErrorResponse;
import com.nikesh.musicplaylistapi.dto.response.error.ValidationErrorDto;
import com.nikesh.musicplaylistapi.factory.ErrorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@RestControllerAdvice
public class RestExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Collection<ValidationErrorDto>> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return new ResponseEntity<>(processFieldErrors(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoRecordFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoRecordFoundException(NoRecordFoundException ex) {
        return new ResponseEntity<>(ErrorFactory.getErrorResponse(ex, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadDataException.class)
    public ResponseEntity<ErrorResponse> handleBadDataException(BadDataException ex) {
        return new ResponseEntity<>(ErrorFactory.getErrorResponse(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateDataException(DuplicateDataException ex) {
        return new ResponseEntity<>(ErrorFactory.getErrorResponse(ex, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    private Collection<ValidationErrorDto> processFieldErrors(List<FieldError> fieldErrors) {
        Collection<ValidationErrorDto> validationErrorDtos = new ArrayList<>();
        ValidationErrorDto dto;

        for (FieldError fieldError : fieldErrors) {
            dto = new ValidationErrorDto();
            // String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.setFieldError(fieldError.getField(), fieldError.getDefaultMessage());

            validationErrorDtos.add(dto);
        }

        return validationErrorDtos;
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }

        return localizedErrorMessage;
    }

}

package team.rode.supplymanagerrestapi.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.rode.supplymanagerrestapi.exceptionHandling.exceptions.DuplicateResourceException;
import team.rode.supplymanagerrestapi.exceptionHandling.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(ResourceNotFoundException e) {
        return new ExceptionBody(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleDuplicateResourceException(DuplicateResourceException e) {
        return new ExceptionBody(HttpStatus.CONFLICT.value(), e.getMessage());
    }

}

package Esercizio17112023.Esercizio17112023.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class Exceptions {

    @ExceptionHandler(BadRequest.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ListErrorsPayload badRequest(BadRequest e) {
            List<String> errors =e.getErrorsList().stream().map(elem->elem.getDefaultMessage()).toList();
            return new ListErrorsPayload(errors,new Date());
        }
    @ExceptionHandler(SingleBadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload singleBadRequest(SingleBadRequest e) {

        return new ErrorPayload(e.getMessage(),new Date());
    }
    @ExceptionHandler(EventoCompletoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload EventoCompleto(EventoCompletoException e){
        return new ErrorPayload(e.getMessage(),new Date());
    }
    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload notFound(NotFound e) {
        return new ErrorPayload(e.getMessage(),new Date());
    }
    @ExceptionHandler(Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorPayload unauthorized(Unauthorized e){
        return new ErrorPayload(e.getMessage(),new Date());
}
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorPayload handleUnauthorized(HttpClientErrorException.Unauthorized e){
        return new ErrorPayload(e.getMessage(), new Date());
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorPayload accessDenied(AccessDeniedException e){return new ErrorPayload(e.getMessage(), new Date());}

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ErrorPayload handleAccessDenied(org.springframework.security.access.AccessDeniedException e){
        return new ErrorPayload(e.getMessage(), new Date());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorPayload serverError(Exception e){
        e.printStackTrace();
        return new ErrorPayload("Internal errors",new Date());
    }
}

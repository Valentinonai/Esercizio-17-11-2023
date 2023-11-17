package Esercizio17112023.Esercizio17112023.exceptions;

import lombok.Getter;

@Getter
public class NotFound extends  RuntimeException{
    public NotFound(String message) {
        super(message);
    }
}

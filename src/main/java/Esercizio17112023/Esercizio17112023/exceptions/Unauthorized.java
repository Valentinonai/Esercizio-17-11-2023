package Esercizio17112023.Esercizio17112023.exceptions;

import lombok.Getter;

@Getter
public class Unauthorized extends RuntimeException{
    public Unauthorized(String message) {
        super(message);
    }
}

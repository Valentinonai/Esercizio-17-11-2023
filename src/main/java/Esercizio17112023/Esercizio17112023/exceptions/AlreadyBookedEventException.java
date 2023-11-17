package Esercizio17112023.Esercizio17112023.exceptions;

public class AlreadyBookedEventException extends RuntimeException{
    public AlreadyBookedEventException(String message) {
        super(message);
    }
}

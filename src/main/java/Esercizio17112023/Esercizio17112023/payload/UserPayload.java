package Esercizio17112023.Esercizio17112023.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserPayload(
        @NotEmpty(message = "il nome non può essere vuoto")
        String nome,
        @NotEmpty(message = "la mail non può essere vuoto")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "L'email inserita non è valida")
        String email,
        @NotEmpty(message = "la password non può essere vuoto")
        String password
) {
}

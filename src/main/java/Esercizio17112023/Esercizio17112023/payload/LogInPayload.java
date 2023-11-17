package Esercizio17112023.Esercizio17112023.payload;

import jakarta.validation.constraints.NotEmpty;

public record LogInPayload(
        @NotEmpty(message = "La mail è un campo obbligatorio")
        String email,
        @NotEmpty(message = "La password è un campo obbligatorio")
        String password
) {
}

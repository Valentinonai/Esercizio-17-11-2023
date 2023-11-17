package Esercizio17112023.Esercizio17112023.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EventoPayload(
        @NotEmpty(message = "il titolo non può essere vuoto")

        String titolo,

        String descrizione,
        @NotEmpty(message = "il luogo non può essere vuoto")
        String luogo,
        @NotEmpty(message = "la data non può essere vuoto")
        LocalDate data,
        @NotNull(message = "il numero posti non può essere vuoto")
        @Min(0)
        int posti_disponibili
) {
}

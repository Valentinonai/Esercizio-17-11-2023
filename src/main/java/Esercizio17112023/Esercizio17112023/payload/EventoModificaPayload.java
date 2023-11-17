package Esercizio17112023.Esercizio17112023.payload;

import jakarta.validation.constraints.Min;


import java.time.LocalDate;

public record EventoModificaPayload(


        String titolo,

        String descrizione,

        String luogo,

        LocalDate data,

        @Min(0)
        int posti_disponibili
) {
}

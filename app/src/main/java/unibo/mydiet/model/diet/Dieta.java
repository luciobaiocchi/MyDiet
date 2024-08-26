package unibo.mydiet.model.diet;

import java.util.Map;

public record Dieta(
        String CliUsername,
        String NutUsername,
        String prezzo,
        String dataFine,
        String dataInizio,
        Map<NomeGiorno, Giorno> giorni) {
}

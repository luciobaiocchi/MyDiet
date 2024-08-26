package unibo.mydiet.model.diet;


import java.util.Map;

public record Giorno(NomeGiorno nome,
                     Map<NomePasto, Ricetta> pasti) {
}

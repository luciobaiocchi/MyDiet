package unibo.mydiet.model.diet;

import java.util.List;

public record Ricetta(List<Alimento> ingredienti,
                       String nome,
                       String difficolta,
                       String procedimento,
                       String tempoPreparazione) {
}

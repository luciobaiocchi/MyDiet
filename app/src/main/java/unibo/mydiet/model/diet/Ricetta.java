package unibo.mydiet.model.diet;

import java.util.List;

public record Ricetta(List<Alimento> ingredienti,
                       String nome,
                       String procedimento,
                       String tempoPreparazione) {
}

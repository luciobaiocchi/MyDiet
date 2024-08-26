package unibo.mydiet.model.diet;

public record Alimento (String idAlimento,
                       int peso,
                       String nome,
                       ValoriNutrizionali valoriNutrizionali) {
}

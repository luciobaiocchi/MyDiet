package unibo.mydiet.model.diet;


import java.util.Map;

public record Giorno(String nome,
                     Map<NomePasto, Pasto> pasti) {
}

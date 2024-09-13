package unibo.mydiet.model.diet;

import java.util.ArrayList;
import java.util.List;

public class Ricetta {
    private List<Alimento> ingredienti = new ArrayList<>();
    private String nome;
    private String difficolta;
    private String procedimento;
    private String tempoPreparazione;

    public Ricetta(final List<Alimento> ingredienti,
                   final String nome,
                   final String difficolta,
                   final String procedimento,
                   final String tempoPreparazione) {
        this.ingredienti = ingredienti;
        this.nome = nome;
        this.difficolta = difficolta;
        this.procedimento = procedimento;
        this.tempoPreparazione = tempoPreparazione;
    }

    public void setAlimenti(List<Alimento> alimenti) {
        this.ingredienti = alimenti;
    }

    // Getters
    public List<Alimento> getIngredienti() {
        return ingredienti;
    }

    public String getNome() {
        return nome;
    }

    public String getDifficolta() {
        return difficolta;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public String getTempoPreparazione() {
        return tempoPreparazione;
    }

    public void addIngrediente(Alimento alimento) {
        ingredienti.add(alimento);
    }
}
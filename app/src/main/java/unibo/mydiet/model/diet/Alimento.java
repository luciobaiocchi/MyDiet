package unibo.mydiet.model.diet;

public class Alimento {
    private String idAlimento;
    private int peso;
    private String nome;
    private ValoriNutrizionali valoriNutrizionali;

    public Alimento(String idAlimento, int peso, String nome, ValoriNutrizionali valoriNutrizionali) {
        this.idAlimento = idAlimento;
        this.peso = peso;
        this.nome = nome;
        this.valoriNutrizionali = valoriNutrizionali;
    }

    public String getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(String idAlimento) {
        this.idAlimento = idAlimento;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ValoriNutrizionali getValoriNutrizionali() {
        return valoriNutrizionali;
    }

    public void setValoriNutrizionali(ValoriNutrizionali valoriNutrizionali) {
        this.valoriNutrizionali = valoriNutrizionali;
    }
}
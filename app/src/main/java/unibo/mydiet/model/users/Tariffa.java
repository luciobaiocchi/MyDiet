package unibo.mydiet.model.users;

public class Tariffa {
    private int durata; // durata in giorni
    private double prezzo; // prezzo in euro

    public Tariffa(int durata, double prezzo) {
        this.durata = durata;
        this.prezzo = prezzo;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}
package unibo.mydiet.model.diet;

import java.util.Map;

public class Dieta {
    private String cliUsername;
    private String nutUsername;
    private String prezzo;
    private String dataFine;
    private String dataInizio;
    private Map<NomeGiorno, Giorno> giorni;

    public Dieta(String cliUsername,
                 String nutUsername,
                 String prezzo,
                 String dataFine,
                 String dataInizio,
                 Map<NomeGiorno, Giorno> giorni) {
        this.cliUsername = cliUsername;
        this.nutUsername = nutUsername;
        this.prezzo = prezzo;
        this.dataFine = dataFine;
        this.dataInizio = dataInizio;
        this.giorni = giorni;
    }

    public void setGiorni(Map<NomeGiorno, Giorno> giorni){
        this.giorni = giorni;
    }

    // Getter methods
    public String getCliUsername() {
        return cliUsername;
    }

    public String getNutUsername() {
        return nutUsername;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public String getDataFine() {
        return dataFine;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public Map<NomeGiorno, Giorno> getGiorni() {
        return giorni;
    }
}
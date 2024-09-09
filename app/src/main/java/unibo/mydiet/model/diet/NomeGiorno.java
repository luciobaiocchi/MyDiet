package unibo.mydiet.model.diet;

public enum NomeGiorno {
    LUNEDI ("Lunedì"),
    MARTEDI ("Martedì"),
    MERCOLEDI ("Mercoledì"),
    GIOVEDI ("Giovedì"),
    VENERDI ("Venerdì"),
    SABATO ("Sabato"),
    DOMENICA ("Domenica");

    final String giorno;

    NomeGiorno(final String giorno){
        this.giorno = giorno;
    }

    public String getDay(){
        return this.giorno;
    }
}

package unibo.mydiet.model.diet;

public enum NomePasto {
    COLAZIONE ("Colazione"),
    //SPUNTINO_MATTINA ("Spuntino Mattina"),
    PRANZO ("Pranzo"),
    //SPUNTINO_POMERIGGIO ("Spuntino Pomeriggio"),
    CENA ("Cena");

    final String pasto;

    NomePasto(final String pasto){
        this.pasto = pasto;
    }

    public String getPasto(){
        return this.pasto;
    }
}

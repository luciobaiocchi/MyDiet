package unibo.mydiet.controller;

import unibo.mydiet.DB.MyDietDAO;

public class Controller {
    final MyDietDAO dao = new MyDietDAO();

    public Controller() {
    }

    public void registerClient(final String nome,
                      final String cognome,
                      final String username,
                      final String password,
                      final int numeroTelefono,
                      final int eta,
                      final String mail,
                      final char sesso) {
        if (dao.registerClient(nome, cognome, username, password, numeroTelefono, eta, mail, sesso)){
            System.out.println("Registrazione avvenuta con successo");
        } else {
            System.out.println("Registrazione fallita");
        };
    }
}

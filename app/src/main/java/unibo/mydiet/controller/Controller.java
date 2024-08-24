package unibo.mydiet.controller;

import unibo.mydiet.DB.MyDietDAO;
import unibo.mydiet.model.Client;
import unibo.mydiet.model.Nutrizionist;

import java.sql.SQLException;

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
                      final char sesso){
        if (dao.registerClient(nome, cognome, username, password, numeroTelefono, eta, mail, sesso)){
            System.out.println("Registrazione avvenuta con successo");
        } else {
            System.out.println("Registrazione fallita");
        };
    }

    public void registerNutrizionist (final Nutrizionist nutrizionist) throws SQLException {
        if (dao.registerNutrizionist(nutrizionist)){
            System.out.println("Registrazione avvenuta con successo");
        } else {
            System.out.println("Registrazione fallita");
        };
    }

    public boolean loginClient(final String username, final String password) {
        try {
            return dao.loginClient(username, password);
        } catch (SQLException e) {
            return false;
        }
    }

    public Client getClientInfo(final String username) {
        try {
            return dao.getClientInfo(username);
        } catch (SQLException e) {
            return null;
        }
    }
}

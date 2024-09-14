package unibo.mydiet.controller;

import unibo.mydiet.DB.MyDietDAO;
import unibo.mydiet.model.Aggiornamento;
import unibo.mydiet.model.DietBuilder;
import unibo.mydiet.model.Goal;
import unibo.mydiet.model.diet.*;
import unibo.mydiet.model.users.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Controller {
    private final MyDietDAO dao = new MyDietDAO();
    private UserLogged userLogged;
    boolean isLogged = false;
    private final DietBuilder dietBuilder = new DietBuilder(dao);

    public Controller() {
    }

    public Optional<UserLogged> getUserLogged() {
        return isLogged ? Optional.of(userLogged) : Optional.empty();
    }

    public void setUserLogged(final String username, UserType type) {
        if (type == UserType.NUTRIZIONIST) {
            final Nutrizionist nutrizionist = this.getNutrizionist(username);
            this.userLogged = new UserLogged(nutrizionist);
        } else if (type == UserType.CLIENT) {
            final User client = this.getClient(username);
            this.userLogged = new UserLogged(client);
        }
        isLogged = true;
    }

    public Client getClient(final String username) {
        try {
            return dao.getClientInfo(username);
        } catch (SQLException e) {
            return null;
        }
    }

    public String getUsrPsw (){
        if (userLogged != null){
            return userLogged.getCli().password();
        } else {
            return null;
        }
    }

    public Nutrizionist getNutrizionist(final String username) {
        try {
            Nutrizionist nut = dao.getNutInfo(username);
            List<Tariffa> tar = dao.getNutTarif(username);
            if (tar != null){
                nut.updateTariffa(tar);
            }
            return nut;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean registerClient(final String nome,
                                  final String cognome,
                                  final String username,
                                  final String password,
                                  final String numeroTelefono,
                                  final int eta,
                                  final String mail,
                                  final char sesso){
        if (dao.registerClient(nome, cognome, username, password, numeroTelefono, eta, mail, sesso)){
            System.out.println("Registrazione avvenuta con successo");
            return true;
        } else {
            System.out.println("Registrazione fallita");
        };
        return false;
    }

    public boolean registerNutrizionista(String nome, String cognome, String username, String password, String numeroTelefono, int eta, String mail, char sesso, String specializzazione) {
    Nutrizionist nutrizionist = new Nutrizionist(specializzazione, nome, cognome, username, password, numeroTelefono, mail, Character.toString(sesso), "0", "0");
        try {
            if (dao.registerNutrizionist(nutrizionist)) {
                System.out.println("Registrazione avvenuta con successo");
                return true;
            } else {
                System.out.println("Registrazione fallita");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean loginClient(final String username, final String password) {
        try {
            return dao.loginClient(username, password);
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean loginNut(final String username, final String password) {
        try {
            return dao.loginNut(username, password);
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Nutrizionist> getNutrizionists() {
        try {
            return dao.getNutList();
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Nutrizionist> getHigerratingList() {
        try {
            return dao.getHigerRating();
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Nutrizionist> getMoreSatisfiedlist() {
        try {
            return dao.getMoreSatisfied();
        } catch (SQLException e) {
            return null;
        }
    }

    public Goal getCLientGoal(final String username){
        try {
            return dao.getCliGoal(username);
        } catch (SQLException e) {
            return null;
        }
    }
    public List<Aggiornamento> getCLientAggiornamento(final String username){
        try {
            return dao.getUserAggiornamento(username);
        } catch (SQLException e) {
            return null;
        }
    }

    public Dieta getDiet() {
        try {
            if (dao.getRowDiet(userLogged.getCli().username()) != null){
                return dietBuilder.buildDiet(userLogged.getCli().username());
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }
    public Optional<List<PercorsoFormazione>> getNutFormation(){
        try {
            return Optional.of(dao.getNutFormation(userLogged.getNut().getUsername()));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public boolean updateNutPsw(final String newPsw){
        try {
            if(dao.setNutPsw(newPsw, userLogged.getNut().getUsername())){
                getUserLogged().get().getNut().setPassword(newPsw);
                return true;
            };
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public void updateNutTari(){
        try {
            final List<Tariffa> newTariffario = dao.getNutTarif(userLogged.getNut().getUsername());
            userLogged.getNut().updateTariffa(newTariffario);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateTariffaDB(Tariffa tariffa) {
        String username = userLogged.getNut().getUsername();
        double prezzo = tariffa.getPrezzo();
        int durata = tariffa.getDurata();
        return dao.updateNutTariff(username, prezzo, durata);
    }

    public boolean addPercorsoFormazione(PercorsoFormazione percorso) {
        return dao.addPercorsoFormazione(percorso, userLogged.getNut().getUsername());
    }

    public boolean isClientOfNutritionist(String clientUsername, String nutritionistUsername) {
        // Retrieve the list of clients for the given nutritionist
        List<String> clientUsernames = null;
        try {
            clientUsernames = dao.getClientUsernames(nutritionistUsername);
        } catch (SQLException e) {
            return false;
        }
        // Check if the client's username is in the list
        return clientUsernames.contains(clientUsername);
    }

    public List<String> getClientUsernames() {
        try {
            return dao.getClientUsernames(userLogged.getNut().getUsername());
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    public void updateRicetta(String clientUsername, NomeGiorno giorno, NomePasto pasto, Ricetta ricetta) {
        dao.updateRicetta(clientUsername, giorno, pasto, ricetta);
    }

    public Alimento getAlimentoById(String id) {
        return dao.getAlimentoById(id);
    }

    public List<Alimento> getAlimenti() {
        try {
            return dao.getAlimList();
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    public boolean saveClientUpdate(String username, String data, String descrizione, int peso, int circPuntoVita, int circBraccio, int circGambe) {
    return dao.saveClientUpdate(username, data, descrizione, peso, circPuntoVita, circBraccio, circGambe);
}

    public boolean saveReview(String clientUsername, String nutritionistUsername, String description, int stars) {
        Optional<String> optionalDescription = Optional.ofNullable(description);
        return dao.saveReview(clientUsername, nutritionistUsername, optionalDescription, stars);
    }


}

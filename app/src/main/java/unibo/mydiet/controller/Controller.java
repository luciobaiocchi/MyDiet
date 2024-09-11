package unibo.mydiet.controller;

import unibo.mydiet.DB.MyDietDAO;
import unibo.mydiet.model.DietBuilder;
import unibo.mydiet.model.Goal;
import unibo.mydiet.model.diet.Dieta;
import unibo.mydiet.model.users.*;
import unibo.mydiet.view.PanelChangeSubject;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
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
            return dao.getNutInfo(username);
        } catch (SQLException e) {
            return null;
        }
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

    public Goal getCLientGoal(){
        try {
            return dao.getCliGoal(userLogged.getCli().username());
        } catch (SQLException e) {
            return null;
        }
    }

    public Dieta getDiet() {
        try {
            System.out.println(dao.getRowDiet(userLogged.getCli().username()));
            return dietBuilder.buildDiet(userLogged.getCli().username());
        } catch (SQLException e) {
            return null;
        }
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

}

package unibo.mydiet.model;

import unibo.mydiet.DB.MyDietDAO;
import unibo.mydiet.model.diet.*;
import unibo.mydiet.view.HomePage;
import unibo.mydiet.view.PanelChangeSubject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DietBuilder {
    private final MyDietDAO dao;
    private Dieta diet;
    private Map<NomeGiorno, Giorno> days;

    public DietBuilder(final MyDietDAO dao) {
        this.dao = dao;
    }
    public Dieta buildDiet(final String username) throws SQLException {
        diet = dao.getRowDiet(username);
        addDays();
        System.out.println(diet.getGiorni().get(NomeGiorno.DOMENICA).pasti().get(NomePasto.COLAZIONE).getIngredienti());
        return diet;
    }

    private void addDays(){
        this.days = new HashMap<>();
        try {
            days.put(NomeGiorno.LUNEDI, buildDay(NomeGiorno.LUNEDI));
            days.put(NomeGiorno.MARTEDI, buildDay(NomeGiorno.MARTEDI));
            days.put(NomeGiorno.MERCOLEDI, buildDay(NomeGiorno.MERCOLEDI));
            days.put(NomeGiorno.GIOVEDI, buildDay(NomeGiorno.GIOVEDI));
            days.put(NomeGiorno.VENERDI, buildDay(NomeGiorno.VENERDI));
            days.put(NomeGiorno.SABATO, buildDay(NomeGiorno.SABATO));
            days.put(NomeGiorno.DOMENICA, buildDay(NomeGiorno.DOMENICA));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        diet.setGiorni(days);
    }

    private Giorno buildDay(final NomeGiorno giorno) throws SQLException {
        final Map<NomePasto, Ricetta> pasti = new HashMap<>();
        final Giorno giornoRet = new Giorno(giorno, pasti);
        pasti.put(NomePasto.COLAZIONE, buildRecipe(giorno, NomePasto.COLAZIONE));
        pasti.put(NomePasto.PRANZO, buildRecipe(giorno, NomePasto.PRANZO));
        pasti.put(NomePasto.CENA, buildRecipe(giorno, NomePasto.CENA));
        return giornoRet;
    }


    private Ricetta buildRecipe(final NomeGiorno giorno, final NomePasto pasto) throws SQLException {
        Ricetta recipe = dao.getRecipe(diet.getCliUsername(), diet.getDataInizio(), giorno.getDay(), pasto.getPasto());
        recipe.setAlimenti(dao.getFoodInRecipe(diet.getCliUsername(), diet.getDataInizio(), giorno.getDay(), pasto.getPasto()));
        return recipe;
    }
}

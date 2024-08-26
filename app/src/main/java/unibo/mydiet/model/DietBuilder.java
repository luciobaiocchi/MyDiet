package unibo.mydiet.model;

import unibo.mydiet.DB.MyDietDAO;
import unibo.mydiet.model.diet.Dieta;
import unibo.mydiet.model.diet.Giorno;
import unibo.mydiet.model.diet.NomeGiorno;
import unibo.mydiet.model.diet.Ricetta;
import unibo.mydiet.view.PanelChangeSubject;

import java.sql.SQLException;
import java.util.Map;

public class DietBuilder {
    private MyDietDAO dao;
    private Dieta diet;
    private String username;
    private String price;
    private String startDate;
    private String endDate;
    private String nutritionistUsername;
    private Map<NomeGiorno, Giorno> days;

    public DietBuilder(final MyDietDAO dao) {
        this.dao = dao;
    }
    public Dieta buildDiet(final String username) throws SQLException {
        Dieta rawdiet = dao.getRowDiet(username);
        getRecipe();
        //days.put(NomeGiorno.LUNEDI, new Giorno());
        return diet;
    }
    private Ricetta getRecipe() throws SQLException {
        System.out.println(dao.getRecipe("cliente4", "2024-10-01","Lunedì", "Colazione"));
        System.out.println(dao.getFoodInRecipe("cliente4", "2024-10-01","Lunedì", "Colazione"));
        return null;
    }

}

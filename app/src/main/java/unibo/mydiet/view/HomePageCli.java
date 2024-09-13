package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.UserType;

import javax.swing.*;
import java.awt.event.ActionListener;

public class HomePageCli extends HomePage{
    private final Controller controller;
    private enum ClientContext{
        HOME_PAGE,
        NUTRIZIONIST,
        DIET,
    }
    private ClientContext context = ClientContext.HOME_PAGE;

    public HomePageCli(Controller controller) {
        super(controller);
        this.controller = controller;
        setButtonTitle(4,"Visualizza Nutrizionisti");
        setButtonTitle(5,"Visualizza Profilo");
        setButtonTitle(3,"Dieta e Aggiornamenti ");
        setButtonsNames();
        setButtonsActions();

        super.setButtonAction(3, e -> {
            context = ClientContext.DIET;
            setButtonsNames();
            addDiet();
            System.out.println("Dieta e Aggiornamenti");
        });

        super.setButtonAction(4, e -> {
            context = ClientContext.NUTRIZIONIST;
            setButtonsNames();
            addNutList();
            System.out.println("Visualize Nutrizionisti");
        });

        setButtonTitle(5,"Visualizza Profilo");
        setButtonAction(5, e -> {
            context = ClientContext.HOME_PAGE;
            setButtonsNames();
            addTable();
            System.out.println("Visualizza Profilo");
        });
    }
    public void addTable() {
        if (controller.getUserLogged().isPresent() && controller.getUserLogged().get().getType() == UserType.CLIENT) {
            final Client client = controller.getUserLogged().get().getCli();
            System.out.println(client);
            JTable table = TableFactory.getCliProfile(client);
            addTable(table);
        }
    }
    private void addNutList() {
        System.out.println("addNutList");
        addTable(TableFactory.getNutList(controller.getNutrizionists()));
    }
    private void addNutHigerRating() {
        System.out.println("addNutHigerRating");
        addTable(TableFactory.getNutList(controller.getHigerratingList()));
    }
    private void addNutMoreSatisfied() {
        System.out.println("addNutHigerRating");
        super.addTable(TableFactory.getNutListMostSatisied(controller.getMoreSatisfiedlist()));
    }
    private void addGoal(){
        addTable(TableFactory.getGoalTable(controller.getCLientGoal(controller.getUserLogged().get().getCli().username())));
    }
    private void showPsw(){
        addTable(TableFactory.getPswTable(controller.getUsrPsw()));
    }
    private void addDiet(){
        DietPanel dietPanel = new DietPanel(controller.getDiet());
        setCenterPanel(dietPanel);
        dietPanel.revalidate(); // Ensure the layout manager recalculates the layout
        dietPanel.repaint();
    }



    private void setButtonsNames() {
        if (context == ClientContext.HOME_PAGE) {
            setButtonTitle(0, "Dati Personali");
            setButtonTitle(1, "Visualiza Obbiettivo");
            setButtonTitle(2, "Visualiza Password");
        } else if (context == ClientContext.NUTRIZIONIST) {
            setButtonTitle(0,"Lista completa");
            setButtonTitle(1,"Più stellati");
            setButtonTitle(2,"Più soddisfatti");
        }else if (context == ClientContext.DIET) {
            setButtonTitle(0,"Visualizza Dieta");
            setButtonTitle(1,"Carica aggiornamento");
            setButtonTitle(2,"visualizza aggiornamenti");
        }
    }

    private void setButtonsActions(){
        setButtonAction(0, e -> {
            if (context == ClientContext.HOME_PAGE) {
                addTable();
                System.out.println("Dati Personali");
            } else if (context == ClientContext.NUTRIZIONIST) {
                addNutList();
                System.out.println("Lista completa");
            } else if (context == ClientContext.DIET) {
                addDiet();
                System.out.println("Visualizza Dieta");
            }
        });
        setButtonAction(1, e -> {
            if (context == ClientContext.HOME_PAGE) {
                addGoal();
                System.out.println("Visualizza Obbiettivo");
            } else if (context == ClientContext.NUTRIZIONIST) {
                addNutHigerRating();
                System.out.println("Più stellati");
            } else if (context == ClientContext.DIET){
                System.out.println("carica aggiornamenti");
            }
        });
        setButtonAction(2, e -> {
            if (context == ClientContext.HOME_PAGE) {
                showPsw();
                System.out.println("visualizza psw");
            }else if (context == ClientContext.NUTRIZIONIST) {
                addNutMoreSatisfied();
                System.out.println("più soddisfatti");
            }else if (context == ClientContext.DIET){
                System.out.println("Visualizza Aggiornamenti");
            }
        });

    }

}

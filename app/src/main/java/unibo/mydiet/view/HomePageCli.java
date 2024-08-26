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
        super.setButtonTitle(4,"Visualizza Nutrizionisti");
        super.setButtonTitle(5,"Visualizza Profilo");
        super.setButtonTitle(3,"Dieta e Aggiornamenti ");
        setButtonsNames();
        setButtonsActions();

        super.setButtonAction(3, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                context = ClientContext.DIET;
                setButtonsNames();
                addTable();
                System.out.println("Dieta e Aggiornamenti");
            }
        });

        super.setButtonAction(4, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                context = ClientContext.NUTRIZIONIST;
                setButtonsNames();
                addNutList();
                System.out.println("Visualize Nutrizionisti");
            }
        });

        super.setButtonTitle(5,"Visualizza Profilo");
        super.setButtonAction(5, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                context = ClientContext.HOME_PAGE;
                setButtonsNames();
                addTable();
                System.out.println("Visualizza Profilo");
            }
        });
    }
    public void addTable() {
        if (controller.getUserLogged().isPresent() && controller.getUserLogged().get().getType() == UserType.CLIENT) {
            final Client client = controller.getUserLogged().get().getCli();
            System.out.println(client);
            JTable table = TableFactory.getCliProfile(client);
            super.addTable(table);
        }
    }
    private void addNutList() {
        System.out.println("addNutList");
        super.addTable(TableFactory.getNutList(controller.getNutrizionists()));
    }
    private void addNutHigerRating() {
        System.out.println("addNutHigerRating");
        super.addTable(TableFactory.getNutList(controller.getHigerratingList()));
    }
    private void addNutMoreSatisfied() {
        System.out.println("addNutHigerRating");
        super.addTable(TableFactory.getNutListMostSatisied(controller.getMoreSatisfiedlist()));
    }
    private void addGoal(){
        super.addTable(TableFactory.getGoalTable(controller.getCLientGoal()));
    }
    private void showPsw(){
        super.addTable(TableFactory.getPswTable(controller.getUsrPsw()));
    }



    private void setButtonsNames() {
        if (context == ClientContext.HOME_PAGE) {
            HomePageCli.super.setButtonTitle(0, "Dati Personali");
            HomePageCli.super.setButtonTitle(1, "Visualiza Obbiettivo");
            HomePageCli.super.setButtonTitle(2, "Visualiza Password");
        } else if (context == ClientContext.NUTRIZIONIST) {
            HomePageCli.super.setButtonTitle(0,"Lista completa");
            HomePageCli.super.setButtonTitle(1,"Più stellati");
            HomePageCli.super.setButtonTitle(2,"Più soddisfatti");
        }
    }

    private void setButtonsActions(){
        HomePageCli.super.setButtonAction(0, e -> {
            if (context == ClientContext.HOME_PAGE) {
                addTable();
                System.out.println("Dati Personali");
            } else if (context == ClientContext.NUTRIZIONIST) {
                addNutList();
                System.out.println("Lista completa");
            }
        });
        HomePageCli.super.setButtonAction(1, e -> {
            if (context == ClientContext.HOME_PAGE) {
                addGoal();
                System.out.println("Visualizza Obbiettivo");
            } else if (context == ClientContext.NUTRIZIONIST) {
                addNutHigerRating();
                System.out.println("Più stellati");
            }
        });
        HomePageCli.super.setButtonAction(2, e -> {
            if (context == ClientContext.HOME_PAGE) {
                showPsw();
                System.out.println("visualizza psw");
            } else if (context == ClientContext.NUTRIZIONIST) {
                addNutMoreSatisfied();
                System.out.println("più soddisfatti");
            }
        });

    }

}

package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.UserType;

import javax.swing.*;
import java.awt.event.ActionListener;

public class HomePageCli extends HomePage{
    private final Controller controller;

    public HomePageCli(Controller controller) {
        super(controller);
        this.controller = controller;
        super.setButtonTitle(4,"Visualizza Nutrizionisti");
        super.setButtonTitle(5,"Visualizza Profilo");
        super.setButtonTitle(3,"Dieta e Aggiornamenti ");
        setButtonProfile();

        super.setButtonAction(3, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                addTable();
                System.out.println("Dati Personali");
            }
        });

        super.setButtonAction(4, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                addNutList();
                System.out.println("Visualizza Nutrizionisti");

                HomePageCli.super.setButtonTitle(0,"Lista completa");
                HomePageCli.super.setButtonAction(0, new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        addNutList();
                        System.out.println("Lista completa");

                    }
                });

                HomePageCli.super.setButtonTitle(1,"Più stellati");
                HomePageCli.super.setButtonAction(1, new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        addNutHigerRating();
                        System.out.println("Più stellati");

                    }
                });

                HomePageCli.super.setButtonTitle(2,"Più soddisfatti");
                HomePageCli.super.setButtonAction(2, new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        addNutMoreSatisfied();
                        System.out.println("Più Soddisfatti");

                    }
                });
            }
        });

        super.setButtonTitle(5,"Visualizza Profilo");
        super.setButtonAction(5, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                addTable();
                System.out.println("Visualizza Profilo");
                setButtonProfile();
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
    public void addNutList() {
        System.out.println("addNutList");
        super.addTable(TableFactory.getNutList(controller.getNutrizionists()));
    }
    public void addNutHigerRating() {
        System.out.println("addNutHigerRating");
        super.addTable(TableFactory.getNutList(controller.getHigerratingList()));
    }
    public void addNutMoreSatisfied() {
        System.out.println("addNutHigerRating");
        super.addTable(TableFactory.getNutListMostSatisied(controller.getMoreSatisfiedlist()));
    }

    private void setButtonProfile() {
        super.setButtonTitle(0,"Dati Personali");
        super.setButtonTitle(1,"Visualiza Obbiettivo");
        super.setButtonTitle(2,"Visualiza Password");
    }

}

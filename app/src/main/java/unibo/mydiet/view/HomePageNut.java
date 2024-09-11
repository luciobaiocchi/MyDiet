package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.UserType;

import javax.swing.*;

public class HomePageNut extends HomePage {
    private final Controller controller;
    private enum NutContext {
        HOME_PAGE,
        VIEW_CLIENT,
        MODIFY_PROFILE,
    }
    private NutContext context = NutContext.HOME_PAGE;

    public HomePageNut(Controller controller) {
        super(controller);
        this.controller = controller;
        setButtonTitle(4,"Visualizza Clienti");
        setButtonTitle(5,"Visualizza Profilo");
        setButtonTitle(3,"Modifica profilo");
        setButtonsActions();
        setButtonsNames();
        setButtonAction(3, e -> {
            context = NutContext.MODIFY_PROFILE;
            setButtonsNames();
            System.out.println("Modifica profilo");
        });
        setButtonAction(4, e -> {
            context = NutContext.VIEW_CLIENT;
            setButtonsNames();
            System.out.println("Visualizza Clienti");
        });
        setButtonAction(5, e -> {
            context = NutContext.HOME_PAGE;
            setButtonsNames();
            System.out.println("Visualizza Profilo");
        });
    }

    @Override
    public void addTable() {
        if (controller.getUserLogged().isPresent() && controller.getUserLogged().get().getType() == UserType.NUTRIZIONIST) {
            final Nutrizionist nutrizionist = controller.getUserLogged().get().getNut();
            System.out.println(nutrizionist);
            JTable table = TableFactory.getNutProfile(nutrizionist);
            super.addTable(table);
        }
    }

    private void setButtonsNames() {
        if (context == NutContext.HOME_PAGE) {
            super.setButtonTitle(0, "Dati Personali");
            super.setButtonTitle(1, "Percorsi Formazione");
            super.setButtonTitle(2, "Visualiza Password");
        } else if (context == NutContext.VIEW_CLIENT) {
            super.setButtonTitle(0,"Lista completa");
            super.setButtonTitle(1,"Obbiettivo/Aggiornamenti");
            super.setButtonTitle(2,"Modifica Dieta");
        }else if (context == NutContext.MODIFY_PROFILE) {
            super.setButtonTitle(0,"Modifica Password");
            super.setButtonTitle(1,"Modifica Tariffario");
            super.setButtonTitle(2,"Aggiungi Percorso");
        }
    }

    private void setButtonsActions(){
        super.setButtonAction(0, e -> {
            if (context == NutContext.HOME_PAGE) {
                System.out.println("Dati Personali");
            } else if (context == NutContext.VIEW_CLIENT) {
                System.out.println("Lista completa");
            } else if (context == NutContext.MODIFY_PROFILE) {;
                System.out.println("Modifica Password");
            }
        });
        super.setButtonAction(1, e -> {
            if (context == NutContext.HOME_PAGE) {
                addFormation();
                System.out.println("Percorsi Formazione");
            } else if (context == NutContext.VIEW_CLIENT) {
                System.out.println("Obbiettivo/Aggiornamenti");
            } else if (context == NutContext.MODIFY_PROFILE){
                System.out.println("Modifica Tariffario");
            }
        });
        super.setButtonAction(2, e -> {
            if (context == NutContext.HOME_PAGE) {
                System.out.println("Visualiza Password");
            }else if (context == NutContext.VIEW_CLIENT) {
                System.out.println("Modifica Dieta");
            }else if (context == NutContext.MODIFY_PROFILE){
                System.out.println("Aggiungi Percorso");
            }
        });

    }

    private void addFormation(){
        if (controller.getNutFormation().isPresent()){
            JTable table = TableFactory.getFormationTable(controller.getNutFormation().get());
            addTable(table);
        }
    }
}

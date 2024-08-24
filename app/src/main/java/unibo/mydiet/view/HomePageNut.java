package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.UserType;

import javax.swing.*;

public class HomePageNut extends HomePage {
    private final Controller controller;

    public HomePageNut(Controller controller) {
        super(controller);
        this.controller = controller;
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
}

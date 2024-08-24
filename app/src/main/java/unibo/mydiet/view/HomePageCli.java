package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.UserType;

import javax.swing.*;
import java.awt.*;

public class HomePageCli extends HomePage{
    private final Controller controller;

    public HomePageCli(Controller controller) {
        super(controller);
        this.controller = controller;
    }
    public void addTable() {
        if (controller.getUserLogged().isPresent() && controller.getUserLogged().get().getType() == UserType.CLIENT) {
            final Client client = controller.getUserLogged().get().getCli();
            System.out.println(client);
            JTable table = TableFactory.getCliProfile(client);
            super.addTable(table);
        }
    }
}

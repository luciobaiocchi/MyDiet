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
        System.out.println(controller.getUserLogged().get().getCli());
        //se logagto e cliente
        if (controller.getUserLogged().isPresent() && controller.getUserLogged().get().getType() == UserType.CLIENT) {
            final Client client = controller.getUserLogged().get().getCli();
            System.out.println(client);
            final String[][] data = {
                    {" ", " nome", client.name(), " "},
                    {" ", "cognome", client.surname(), " "},
                    {" ", "username", client.Username(), " "},
                    {" ", "numeroTelefono", String.valueOf(client.phoneNumber()), " "},
                    {" ", "eta", String.valueOf(client.age()), " "},
                    {" ", "mail", client.email(), " "},
                    {" ", "sesso", client.sex(), " "},
                    {" ", "password", client.password(), " "}
            };
            JTable table = new JTable(data, new String[]{"","","",""});
            table.setFont(Constants.appFont);
            table.setRowHeight(60);
            table.setForeground(Constants.BTNTXT_COLOR);
            table.setGridColor(Constants.BG_COLOR);
            table.setBackground(Constants.BG_COLOR);
            table.setVisible(true);
            table.setOpaque(false);
            super.addTable(table);
        }
    }
}

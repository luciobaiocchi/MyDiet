package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.UserType;

import javax.swing.*;
import java.awt.*;

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
            final String[][] data = {
                    {" ", "Specializzazione", nutrizionist.specializzazione(), " "},
                    {" ", "nome", nutrizionist.nome(), " "},
                    {" ", "cognome", nutrizionist.cognome(), " "},
                    {" ", "username", nutrizionist.username(), " "},
                    {" ", "numeroTelefono", nutrizionist.numeroTelefono(), " "},
                    {" ", "mail", nutrizionist.mail(), " "},
                    {" ", "sesso", nutrizionist.sesso(), " "},
                    {" ", "password", nutrizionist.password(), " "},
                    {" ", "media Stelle", nutrizionist.mediaStelle(), " "},
                    {" ", "Clienti a obbiettivo", nutrizionist.percentualeSoddisfatti() + "%", " "},
            };
            JTable table = new JTable(data, new String[]{"","","",""});
            table.setFont(Constants.appFont);
            table.setRowHeight(60);
            table.setForeground(Constants.BTNTXT_COLOR);
            table.setGridColor(Constants.BG_COLOR);
            table.setBackground(Constants.BG_COLOR);
            table.setVisible(true);
            // Aggiunta della tabella al pannello centrale
            table.setOpaque(false);
            super.addTable(table);
        }
    }
}

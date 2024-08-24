package unibo.mydiet.view;



import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.Nutrizionist;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;


public class TableFactory {

    public static JTable getNutProfile(final Nutrizionist nutrizionist) {
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
        table.setOpaque(false);
        return table;
    }

    public static JTable getCliProfile(final Client cliente) {
        final String[][] data = {
                {" ", "nome", cliente.nome(), " "},
                {" ", "cognome", cliente.cognome(), " "},
                {" ", "username", cliente.username(), " "},
                {" ", "numeroTelefono", cliente.numeroTelefono(), " "},
                {" ", "mail", cliente.mail(), " "},
                {" ", "sesso", cliente.sesso(), " "},
                {" ", "password", cliente.password(), " "},
                {" ", "eta", String.valueOf(cliente.eta()), " "},
        };
        JTable table = new JTable(data, new String[]{"","","",""});
        table.setFont(Constants.appFont);
        table.setRowHeight(60);
        table.setForeground(Constants.BTNTXT_COLOR);
        table.setGridColor(Constants.BG_COLOR);
        table.setBackground(Constants.BG_COLOR);
        table.setVisible(true);
        table.setOpaque(false);
        return table;
    }

    public static JTable getNutList (final List<Nutrizionist> nutrizionists) {
        final String[][] data = new String[nutrizionists.size()][4];
        for (int i = 0; i < nutrizionists.size(); i++) {
            data[i][0] = " ";
            data[i][1] = nutrizionists.get(i).nome();
            data[i][2] = nutrizionists.get(i).cognome();
            data[i][3] = nutrizionists.get(i).specializzazione();
        }
        JTable table = new JTable(data, new String[]{"","","",""});
        table.setFont(Constants.appFont);
        table.setRowHeight(40);
        table.setForeground(Constants.BTNTXT_COLOR);
        table.setGridColor(Constants.BG_COLOR);
        table.setBackground(Constants.BG_COLOR);
        table.setVisible(true);
        table.setOpaque(false);
        return table;
    }

}

package unibo.mydiet.view;



import unibo.mydiet.model.Goal;
import unibo.mydiet.model.diet.Dieta;
import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.PercorsoFormazione;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class TableFactory {

    public static JTable getNutProfile(final Nutrizionist nutrizionist) {
        final String[][] data = {
                {" ", "Specializzazione", nutrizionist.getSpecializzazione(), " "},
                {" ", "nome", nutrizionist.getNome(), " "},
                {" ", "cognome", nutrizionist.getCognome(), " "},
                {" ", "CliUsername", nutrizionist.getUsername(), " "},
                {" ", "numeroTelefono", nutrizionist.getNumeroTelefono(), " "},
                {" ", "mail", nutrizionist.getMail(), " "},
                {" ", "sesso", nutrizionist.getSesso(), " "},
                {" ", "password", nutrizionist.getPassword(), " "},
                {" ", "media Stelle", nutrizionist.getMediaStelle(), " "},
                {" ", "Clienti a obbiettivo", nutrizionist.getPercentualeSoddisfatti() + "%", " "},
        };
        JTable table = new JTable(data, new String[]{"","","",""});
        loadTable(table, 60);
        return table;
    }

    public static JTable getCliProfile(final Client cliente) {
        final String[][] data = {
                {" ", " nome", " " + cliente.nome(), " "},
                {" ", " cognome", " " + cliente.cognome(), " "},
                {" ", " CliUsername", " " + cliente.username(), " "},
                {" ", " numeroTelefono", " " + cliente.numeroTelefono(), " "},
                {" ", " mail", " " + cliente.mail(), " "},
                {" ", " sesso", " " + cliente.sesso(), " "},
                {" ", " eta", " " + cliente.eta(), " "},
        };
        JTable table = new JTable(data, new String[]{" "," "," "," "});
        table.setDragEnabled(true);
        loadTable(table, 60);
        return table;
    }

    public static JTable getNutList (final List<Nutrizionist> nutrizionists) {
        final String[][] data = new String[nutrizionists.size()][4];
        for (int i = 0; i < nutrizionists.size(); i++) {
            data[i][0] = " " + nutrizionists.get(i).getNome();
            data[i][1] = " " + nutrizionists.get(i).getCognome();
            data[i][2] = " " + nutrizionists.get(i).getSpecializzazione();
            data[i][3] = " " + nutrizionists.get(i).getMediaStelle();
        }
        String [] columns = {"Nome", "Cognome", "Specializzazione", "Media Stelle"};
        JTable table = new JTable(data, columns);
        table.setDragEnabled(true);
        loadTable(table, 40);
        return table;
    }

    public static JTable getNutListMostSatisied (final List<Nutrizionist> nutrizionists) {
        final String[][] data = new String[nutrizionists.size()][4];
        for (int i = 0; i < nutrizionists.size(); i++) {
            data[i][0] = " " + nutrizionists.get(i).getNome();
            data[i][1] = " " + nutrizionists.get(i).getCognome();
            data[i][2] = " " + nutrizionists.get(i).getSpecializzazione();
            data[i][3] = " " + nutrizionists.get(i).getPercentualeSoddisfatti();
        }
        String [] columns = {"Nome", "Cognome", "Specializzazione", "Percentuale Soddisfatti"};
        JTable table = new JTable(data, columns);
        table.setDragEnabled(true);
        loadTable(table, 40);
        return table;
    }

    public static JTable getGoalTable (final Goal goal){
        final String[][] data = {
                {" ", " Data Raggiungimento", " " + goal.dataRaggiungimento(), " "},
                {" ", " Stato obbiettivo", " " + (Objects.equals(goal.raggiunto(), "S") ? "raggiunto" : "non raggiunto"), " "},
                {" ", " Descrizione", " " + goal.descrizione(), " "},
                {" ", " Peso", " " + goal.peso(), " "},
                {" ", " Circonferenza Braccio", " " + goal.circBraccio(), " "},
                {" ", " Circonferenza Gambe", " " + goal.circGambe(), " "},
                {" ", " Circonferenza Punto Vita", " " + goal.circVita(), " "},
        };
        JTable table = new JTable(data, new String[]{" "," "," "," "});
        table.setDragEnabled(true);
        loadTable(table, 60);
        return table;
    }

    public static JTable getPswTable (final String  psw){
        final String[][] data = {
                {" ", " Password", " " + psw, " "},
        };
        JTable table = new JTable(data, new String[]{" "," "," "," "});
        table.setDragEnabled(true);
        loadTable(table, 60);
        return table;
    }

    public static JTable getFormationTable(final List<PercorsoFormazione> formations) {
        final String[][] data = new String[formations.size()][4];
        for (int i = 0; i < formations.size(); i++) {
            data[i][0] = " " + formations.get(i).nome();
            data[i][1] = " " + formations.get(i).dataInizio();
            data[i][2] = " " + formations.get(i).dataFine();
            data[i][3] = " " + formations.get(i).voto();
        }
        String [] columns = {"Nome", "Data Inizio", "Data Fine", "voto"};
        JTable table = new JTable(data, columns);
        table.setDragEnabled(true);
        loadTable(table, 40);
        return table;
    }

    private static void  loadTable(JTable table, final int rowHeight) {
        JTableHeader header = table.getTableHeader();
        header.setFont(Constants.appFont);
        table.setFont(Constants.appFont);
        table.setRowHeight(rowHeight);
        table.setForeground(Constants.TXT_COLOR);
        table.setGridColor(Constants.TXT_COLOR);
        table.setBackground(Constants.BG_COLOR);
        table.setVisible(true);
        table.setOpaque(true);
    }
}

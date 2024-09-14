package unibo.mydiet.view;
import unibo.mydiet.model.users.Tariffa;
import unibo.mydiet.model.Aggiornamento;
import unibo.mydiet.model.Goal;
import unibo.mydiet.model.diet.Alimento;
import unibo.mydiet.model.diet.Ricetta;
import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.PercorsoFormazione;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class TableFactory {

    private static final float DEFAULT_FONT_SIZE = 15f;


    private static class NonEditableTableModel extends DefaultTableModel {
        public NonEditableTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    public static JTable getTariffTable(Nutrizionist nutritionist) {
        String[] columnNames = {"Durata (mesi)", "Prezzo (€)"};
        DefaultTableModel tableModel = new NonEditableTableModel(new Object[0][0], columnNames);

        // Load tariffs from the nutritionist
        List<Tariffa> tariffe = nutritionist.getTariffe();
        for (Tariffa tariffa : tariffe) {
            Object[] row = {tariffa.getDurata(), tariffa.getPrezzo()};
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        loadTable(table, 40, DEFAULT_FONT_SIZE);
        return table;
    }
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
        JTable table = new JTable(new NonEditableTableModel(data, new String[]{"", "", "", ""}));
        loadTable(table, 60, DEFAULT_FONT_SIZE);
        resizeColumnWidth(table);
        return table;
    }

    public static JTable getNutProfileNopsw(final Nutrizionist nutrizionist) {
        final String[][] data = {
                {" ", "Specializzazione", nutrizionist.getSpecializzazione(), " "},
                {" ", "nome", nutrizionist.getNome(), " "},
                {" ", "cognome", nutrizionist.getCognome(), " "},
                {" ", "CliUsername", nutrizionist.getUsername(), " "},
                {" ", "numeroTelefono", nutrizionist.getNumeroTelefono(), " "},
                {" ", "mail", nutrizionist.getMail(), " "},
                {" ", "sesso", nutrizionist.getSesso(), " "},
                {" ", "media Stelle", nutrizionist.getMediaStelle(), " "},
                {" ", "Clienti a obbiettivo", nutrizionist.getPercentualeSoddisfatti() + "%", " "},
        };
        JTable table = new JTable(new NonEditableTableModel(data, new String[]{"", "", "", ""}));
        loadTable(table, 60, DEFAULT_FONT_SIZE);
        resizeColumnWidth(table);
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
        JTable table = new JTable(new NonEditableTableModel(data, new String[]{" ", " ", " ", " "}));
        table.setDragEnabled(true);
        loadTable(table, 60, DEFAULT_FONT_SIZE);
        resizeColumnWidth(table);
        return table;
    }

    public static JTable getNutList(final List<Nutrizionist> nutrizionists) {
    final String[][] data = new String[nutrizionists.size()][5];
    for (int i = 0; i < nutrizionists.size(); i++) {
        data[i][0] = nutrizionists.get(i).getUsername();
        data[i][1] = " " + nutrizionists.get(i).getNome();
        data[i][2] = " " + nutrizionists.get(i).getCognome();
        data[i][3] = " " + nutrizionists.get(i).getSpecializzazione();
        data[i][4] = " " + nutrizionists.get(i).getMediaStelle();
    }
    String[] columns = {"Username", "Nome", "Cognome", "Specializzazione", "Media Stelle"};
    JTable table = new JTable(new NonEditableTableModel(data, columns));
    table.setDragEnabled(true);
    loadTable(table, 40, DEFAULT_FONT_SIZE);
    resizeColumnWidth(table);
    return table;
}

    public static JTable getNutListMostSatisied(final List<Nutrizionist> nutrizionists) {
        final String[][] data = new String[nutrizionists.size()][4];
        for (int i = 0; i < nutrizionists.size(); i++) {
            data[i][0] = " " + nutrizionists.get(i).getNome();
            data[i][1] = " " + nutrizionists.get(i).getCognome();
            data[i][2] = " " + nutrizionists.get(i).getSpecializzazione();
            data[i][3] = " " + nutrizionists.get(i).getPercentualeSoddisfatti();
        }
        String[] columns = {"Nome", "Cognome", "Specializzazione", "Percentuale Soddisfatti"};
        JTable table = new JTable(new NonEditableTableModel(data, columns));
        table.setDragEnabled(true);
        loadTable(table, 40, DEFAULT_FONT_SIZE);
        resizeColumnWidth(table);
        return table;
    }

    public static JTable getGoalTable(final Goal goal) {
        final String[][] data = {
                {" ", " Data Raggiungimento", " " + goal.dataRaggiungimento(), " "},
                {" ", " Stato obbiettivo", " " + (Objects.equals(goal.raggiunto(), "S") ? "raggiunto" : "non raggiunto"), " "},
                {" ", " Descrizione", " " + goal.descrizione(), " "},
                {" ", " Peso", " " + goal.peso(), " "},
                {" ", " Circonferenza Braccio", " " + goal.circBraccio(), " "},
                {" ", " Circonferenza Gambe", " " + goal.circGambe(), " "},
                {" ", " Circonferenza Punto Vita", " " + goal.circVita(), " "},
        };
        JTable table = new JTable(new NonEditableTableModel(data, new String[]{" ", " ", " ", " "}));
        table.setDragEnabled(true);
        loadTable(table, 60, DEFAULT_FONT_SIZE);
        resizeColumnWidth(table);
        return table;
    }

    public static JTable getPswTable(final String psw) {
        final String[][] data = {
                {" ", " Password", " " + psw, " "},
        };
        JTable table = new JTable(new NonEditableTableModel(data, new String[]{" ", " ", " ", " "}));
        table.setDragEnabled(true);
        loadTable(table, 60, DEFAULT_FONT_SIZE);
        resizeColumnWidth(table);
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
        String[] columns = {"Nome", "Data Inizio", "Data Fine", "voto"};
        JTable table = new JTable(new NonEditableTableModel(data, columns));
        table.setDragEnabled(true);
        loadTable(table, 40, DEFAULT_FONT_SIZE);
        resizeColumnWidth(table);
        return table;
    }

    public static JTable getClientListTable(List<String> clientUsernames) {
        final String[][] data = new String[clientUsernames.size()][1];
        String[] columnNames = {"Client Username"};
        for (int i = 0; i < clientUsernames.size(); i++) {
            data[i][0] = " " + clientUsernames.get(i);
        }
        JTable table = new JTable(new NonEditableTableModel(data, columnNames));
        table.setDragEnabled(true);
        loadTable(table, 40, DEFAULT_FONT_SIZE);
        resizeColumnWidth(table);
        return table;
    }

    public static JTable getAggiornamentiTable(List<Aggiornamento> aggiornamenti) {
        final String[][] data = new String[aggiornamenti.size()][6];
        for (int i = 0; i < aggiornamenti.size(); i++) {
            Aggiornamento agg = aggiornamenti.get(i);
            data[i][0] = agg.data();
            data[i][1] = agg.descrizione();
            data[i][2] = agg.peso();
            data[i][3] = agg.circPuntoVita();
            data[i][4] = agg.circBraccio();
            data[i][5] = agg.circGambe();
        }
        String[] columnNames = {"Data", "Descrizione", "Peso", "Circ. Punto Vita", "Circ. Braccio", "Circ. Gambe"};
        JTable table = new JTable(new NonEditableTableModel(data, columnNames));
        loadTable(table, 40, DEFAULT_FONT_SIZE);
        resizeColumnWidth(table);
        return table;
    }

    public static JTable buildRecipeTable(final Ricetta ricetta) {
        final String[][] data = {
                {"Nome Ricetta", " " + ricetta.getNome()},
                {"Difficoltà", " " + ricetta.getDifficolta()},
                {"Tempo Preparazione", " " + ricetta.getTempoPreparazione()},
        };
        JTable table = new JTable(data, new String[]{" ", " "});
        loadTable(table, 20, DEFAULT_FONT_SIZE);
        return table;
    }


    public static JTable buildFoodTable(final List<Alimento> alimenti) {
        String[] columnNames = {"Nome", "Quantità", "Proteine (g)", "Grassi (g)", "Carboidrati (g)", "Totale Calorie (kcal)"};
        String[][] data = new String[alimenti.size()][6];

        for (int i = 0; i < alimenti.size(); i++) {
    Alimento alimento = alimenti.get(i);
    data[i][0] = alimento.getNome();
    data[i][1] = String.valueOf(alimento.getPeso());
    data[i][2] = String.valueOf(alimento.getValoriNutrizionali().proteine());
    data[i][3] = String.valueOf(alimento.getValoriNutrizionali().grassi());
    data[i][4] = String.valueOf(alimento.getValoriNutrizionali().carboidrati());
    data[i][5] = String.valueOf(alimento.getValoriNutrizionali().calorieTotali());
}

        JTable table = new JTable(data, columnNames);
        loadTable(table, 20, DEFAULT_FONT_SIZE);
        return table;
    }

    private static void loadTable(JTable table, final int rowHeight, final float fontSize) {
        JTableHeader header = table.getTableHeader();
        header.setFont(Constants.appFont.deriveFont(fontSize));
        table.setFont(Constants.appFont.deriveFont(fontSize));
        table.setRowHeight(rowHeight);
        table.setForeground(Constants.TXT_COLOR);
        table.setGridColor(Constants.TXT_COLOR);
        table.setBackground(Constants.BG_COLOR);
        table.setVisible(true);
        table.setOpaque(true);
    }

    private static void resizeColumnWidth(JTable table) {
        TableColumn column;
        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
            column = table.getColumnModel().getColumn(columnIndex);
            int width = 15; // Min width
            // Include header width
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, columnIndex);
            width = Math.max(headerComp.getPreferredSize().width + 1, width);
            // Include cell width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, columnIndex);
                Component comp = table.prepareRenderer(renderer, row, columnIndex);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            column.setPreferredWidth(width);
        }
    }
}
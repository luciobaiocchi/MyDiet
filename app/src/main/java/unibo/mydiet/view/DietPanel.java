package unibo.mydiet.view;

import unibo.mydiet.model.diet.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class DietPanel extends JPanel {
    private final Dieta diet;
    private final JPanel tablePanel;
    private final float fontSize = 16f;

    public DietPanel(final Dieta diet) {
        this.diet = diet;
        this.tablePanel = new JPanel(new BorderLayout());
        buildPanel();
    }

    private void buildPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JComboBox<NomeGiorno> sceltaGiorno = new JComboBox<>();
        sceltaGiorno.setToolTipText("Scegli un giorno");
        for (NomeGiorno giorno : NomeGiorno.values()) {
            sceltaGiorno.addItem(giorno);
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(sceltaGiorno, gbc);

        JComboBox<NomePasto> sceltaPasto = new JComboBox<>();
        sceltaPasto.setToolTipText("Scegli un pasto");
        for (NomePasto pasto : NomePasto.values()) {
            sceltaPasto.addItem(pasto);
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(sceltaPasto, gbc);

        JButton viewDiet = new JButton("Visualizza Dati");
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(viewDiet, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(tablePanel, gbc);

        viewDiet.addActionListener(e -> {
            NomeGiorno giorno = (NomeGiorno) sceltaGiorno.getSelectedItem();
            NomePasto pasto = (NomePasto) sceltaPasto.getSelectedItem();
            Ricetta ricetta = diet.getGiorni().get(giorno).pasti().get(pasto);
            displayRecipeData(ricetta);
        });
    }

    private void displayRecipeData(final Ricetta ricetta) {
        JTable recipeTable = buildRecipeTable(ricetta);
        JTable foodTable = buildFoodTable(ricetta.getIngredienti());
        JPanel pnl1 = new JPanel();
        JPanel pnl2 = new JPanel();
        JLabel txtLabel = new JLabel("Descrizione :\n" + ricetta.getProcedimento());
        txtLabel.setPreferredSize(new Dimension(200,40));
        JScrollPane foodScrollPane = new JScrollPane(foodTable);

        txtLabel.setFont(Constants.appFont.deriveFont(fontSize));
        pnl1.setLayout(new BorderLayout());
        pnl2.setLayout(new BorderLayout());
        pnl1.add(recipeTable, BorderLayout.CENTER);
        pnl1.add(txtLabel, BorderLayout.SOUTH);
        pnl2.add(foodScrollPane, BorderLayout.CENTER);

        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(pnl1, BorderLayout.NORTH);

        combinedPanel.add(pnl2, BorderLayout.CENTER);

        tablePanel.removeAll();
        tablePanel.add(combinedPanel, BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private JTable buildRecipeTable(final Ricetta ricetta) {
        final String[][] data = {
                {"Nome Ricetta", " " + ricetta.getNome()},
                {"Difficoltà", " " + ricetta.getDifficolta()},
                {"Tempo Preparazione", " " + ricetta.getTempoPreparazione()},
        };
        JTable table = new JTable(data, new String[]{" ", " "});
        loadTable(table, 20, fontSize);
        return table;
    }

    private JTable buildFoodTable(final List<Alimento> alimenti) {
        String[] columnNames = {"Nome", "Quantità", "Proteine (g)", "Grassi (g)", "Carboidrati (g)", "Totale Calorie (kcal)"};
        String[][] data = new String[alimenti.size()][6];

        for (int i = 0; i < alimenti.size(); i++) {
            Alimento alimento = alimenti.get(i);
            data[i][0] = alimento.nome();
            data[i][1] = String.valueOf(alimento.peso());
            data[i][2] = String.valueOf(alimento.valoriNutrizionali().proteine());
            data[i][3] = String.valueOf(alimento.valoriNutrizionali().grassi());
            data[i][4] = String.valueOf(alimento.valoriNutrizionali().carboidrati());
            data[i][5] = String.valueOf(alimento.valoriNutrizionali().calorieTotali());
        }

        JTable table = new JTable(data, columnNames);
        loadTable(table, 20, fontSize);
        return table;
    }

    private static void loadTable(JTable table, final int rowHeight, final float fontSize) {
        JTableHeader header = table.getTableHeader();
        header.setFont(Constants.appFont.deriveFont(fontSize));
        table.setDragEnabled(true);
        table.setFont(Constants.appFont.deriveFont(fontSize));
        table.setRowHeight(rowHeight);
        table.setForeground(Constants.TXT_COLOR);
        table.setGridColor(Constants.TXT_COLOR);
        table.setBackground(Constants.BG_COLOR);
        table.setVisible(true);
        table.setOpaque(false);
    }
}
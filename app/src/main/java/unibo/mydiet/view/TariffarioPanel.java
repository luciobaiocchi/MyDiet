package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.Tariffa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TariffarioPanel extends JPanel {
    private JTable tariffTable;
    private JComboBox<Integer> durataComboBox;
    private JTextField prezzoField;
    private JButton saveButton;
    private JButton updateButton;
    private Controller controller;
    private DefaultTableModel tableModel;

    public TariffarioPanel(Controller controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        // Create table model and set up table
        tableModel = new DefaultTableModel(new Object[]{"Durata", "Prezzo"}, 0);
        tariffTable = new JTable(tableModel);

        // Initialize components
        durataComboBox = createComboBox();
        prezzoField = createTextField();
        saveButton = createButton("Salva");
        updateButton = createButton("Aggiorna");

        // Load tariffe
        loadTariffe();

        // Set font for table
        tariffTable.setFont(Constants.appFont);
        tariffTable.getTableHeader().setFont(Constants.appFont);

        // Create and add components
        add(new JScrollPane(tariffTable), BorderLayout.CENTER);

        JPanel editPanel = new JPanel(new GridLayout(3, 2));
        editPanel.add(createLabel("Durata:"));
        editPanel.add(durataComboBox);

        editPanel.add(createLabel("Prezzo:"));
        editPanel.add(prezzoField);

        editPanel.add(saveButton);
        editPanel.add(updateButton);

        add(editPanel, BorderLayout.SOUTH);

        // Add action listener to save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTariffa();
            }
        });

        // Add action listener to update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTariffa();
            }
        });
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Constants.appFont);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(Constants.appFont);
        return textField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Constants.appFont);
        return button;
    }

    private JComboBox<Integer> createComboBox() {
        JComboBox<Integer> comboBox = new JComboBox<>();
        comboBox.setFont(Constants.appFont);
        return comboBox;
    }

    private void loadTariffe() {
        Nutrizionist nutrizionist = controller.getUserLogged().get().getNut();
        if (nutrizionist != null) {
            List<Tariffa> tariffe = nutrizionist.getTariffe();
            for (Tariffa tariffa : tariffe) {
                tableModel.addRow(new Object[]{tariffa.getDurata(), tariffa.getPrezzo()});
                durataComboBox.addItem(tariffa.getDurata());
            }
        } else {
            System.out.println("Nutrizionist object is null");
        }
    }

    private void saveTariffa() {
        try {
            int durata = (int) durataComboBox.getSelectedItem();
            double prezzo = Double.parseDouble(prezzoField.getText());
            Tariffa newTariffa = new Tariffa(durata, prezzo);
            Nutrizionist nutrizionist = controller.getUserLogged().get().getNut();
            nutrizionist.addTariffa(newTariffa);
            tableModel.addRow(new Object[]{durata, prezzo});
            JOptionPane.showMessageDialog(this, "Tariffa salvata con successo!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Inserisci valori validi per durata e prezzo.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTariffa() {
        try {
            int durata = (int) durataComboBox.getSelectedItem();
            double prezzo = Double.parseDouble(prezzoField.getText());
            Tariffa updatedTariffa = new Tariffa(durata, prezzo);
            boolean success = controller.updateTariffaDB(updatedTariffa);
            if (success) {
                JOptionPane.showMessageDialog(this, "Tariffa aggiornata con successo!");
                // Optionally, refresh the table or UI components here
            } else {
                JOptionPane.showMessageDialog(this, "Errore nell'aggiornamento della tariffa.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Inserisci valori validi per durata e prezzo.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
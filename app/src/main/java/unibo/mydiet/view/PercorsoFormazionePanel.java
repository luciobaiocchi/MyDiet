package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.users.PercorsoFormazione;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PercorsoFormazionePanel extends JPanel {
    private JTextField nomeField;
    private JTextField dataInizioField;
    private JTextField dataFineField;
    private JTextField votoField;
    private JButton saveButton;
    private Controller controller;

    public PercorsoFormazionePanel(Controller controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        // Initialize components
        nomeField = createTextField();
        dataInizioField = createTextField();
        dataFineField = createTextField();
        votoField = createTextField();
        saveButton = createButton("Salva");

        // Create and add components
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(createLabel("Nome:"));
        formPanel.add(nomeField);

        formPanel.add(createLabel("Data Inizio:"));
        formPanel.add(dataInizioField);

        formPanel.add(createLabel("Data Fine:"));
        formPanel.add(dataFineField);

        formPanel.add(createLabel("Voto:"));
        formPanel.add(votoField);

        formPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);

        // Add action listener to save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePercorsoFormazione();
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

    private void savePercorsoFormazione() {
        try {
            String nome = nomeField.getText();
            String dataInizio = dataInizioField.getText();
            String dataFine = dataFineField.getText();
            String voto = votoField.getText();
            PercorsoFormazione newPercorso = new PercorsoFormazione(nome, dataInizio, dataFine, voto);
            boolean success = controller.addPercorsoFormazione(newPercorso);
            if (success) {
                JOptionPane.showMessageDialog(this, "Percorso di formazione salvato con successo!");
                // Optionally, clear the fields or update the UI
            } else {
                JOptionPane.showMessageDialog(this, "Errore nel salvataggio del percorso di formazione.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Inserisci valori validi per i campi.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
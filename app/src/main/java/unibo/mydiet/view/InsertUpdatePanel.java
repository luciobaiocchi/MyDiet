package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertUpdatePanel extends JPanel {
    private final Controller controller;
    private final JTextField usernameField;
    private final JTextField dataField;
    private final JTextField descrizioneField;
    private final JTextField pesoField;
    private final JTextField circPuntoVitaField;
    private final JTextField circBraccioField;
    private final JTextField circGambeField;
    private final JButton saveButton;

    public InsertUpdatePanel(Controller controller) {
        this.controller = controller;
        this.setLayout(new GridLayout(8, 1));

        // Create input fields for the update
        usernameField = new JTextField();
        dataField = new JTextField();
        descrizioneField = new JTextField();
        pesoField = new JTextField();
        circPuntoVitaField = new JTextField();
        circBraccioField = new JTextField();
        circGambeField = new JTextField();

        // Create save button
        saveButton = new JButton("Salva Aggiornamento");
        saveButton.setFont(saveButton.getFont().deriveFont(16f));
        saveButton.addActionListener(new SaveButtonListener());

        // Add components to the panel
        this.add(new JLabel("Username:"));
        this.add(usernameField);
        this.add(new JLabel("Data:"));
        this.add(dataField);
        this.add(new JLabel("Descrizione:"));
        this.add(descrizioneField);
        this.add(new JLabel("Peso:"));
        this.add(pesoField);
        this.add(new JLabel("Circonferenza Punto Vita:"));
        this.add(circPuntoVitaField);
        this.add(new JLabel("Circonferenza Braccio:"));
        this.add(circBraccioField);
        this.add(new JLabel("Circonferenza Gambe:"));
        this.add(circGambeField);
        this.add(saveButton);
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String data = dataField.getText();
            String descrizione = descrizioneField.getText();
            int peso = Integer.parseInt(pesoField.getText());
            int circPuntoVita = Integer.parseInt(circPuntoVitaField.getText());
            int circBraccio = Integer.parseInt(circBraccioField.getText());
            int circGambe = Integer.parseInt(circGambeField.getText());

            if (!username.isEmpty() && !data.isEmpty() && !descrizione.isEmpty()) {
                // Call the controller method to save the update
                boolean success = controller.saveClientUpdate(username, data, descrizione, peso, circPuntoVita, circBraccio, circGambe);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Aggiornamento salvato con successo");
                } else {
                    JOptionPane.showMessageDialog(null, "Errore nel salvataggio dell'aggiornamento");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tutti i campi devono essere compilati");
            }
        }
    }
}
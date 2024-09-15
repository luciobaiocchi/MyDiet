package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InsertUpdatePanel extends JPanel {
    private final Controller controller;
    private final JTextField dataField;
    private final JTextField descrizioneField;
    private final JTextField pesoField;
    private final JTextField circPuntoVitaField;
    private final JTextField circBraccioField;
    private final JTextField circGambeField;
    private final JButton saveButton;

    public InsertUpdatePanel(Controller controller) {
        this.controller = controller;
        this.setLayout(new GridLayout(7, 1));

        // Create input fields for the update
        dataField = new JTextField();
        descrizioneField = new JTextField();
        pesoField = new JTextField();
        circPuntoVitaField = new JTextField();
        circBraccioField = new JTextField();
        circGambeField = new JTextField();

        // Set font for input fields
        Font appFont = Constants.appFont;
        dataField.setFont(appFont);
        descrizioneField.setFont(appFont);
        pesoField.setFont(appFont);
        circPuntoVitaField.setFont(appFont);
        circBraccioField.setFont(appFont);
        circGambeField.setFont(appFont);

        // Set current date in dataField
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dataField.setText(LocalDate.now().format(formatter));

        // Create save button
        saveButton = new JButton("Salva Aggiornamento");
        saveButton.setFont(appFont);
        saveButton.addActionListener(new SaveButtonListener());

        // Add components to the panel
        this.add(createStyledLabel("Data:"));
        this.add(dataField);
        this.add(createStyledLabel("Descrizione:"));
        this.add(descrizioneField);
        this.add(createStyledLabel("Peso:"));
        this.add(pesoField);
        this.add(createStyledLabel("Circonferenza Punto Vita:"));
        this.add(circPuntoVitaField);
        this.add(createStyledLabel("Circonferenza Braccio:"));
        this.add(circBraccioField);
        this.add(createStyledLabel("Circonferenza Gambe:"));
        this.add(circGambeField);
        this.add(saveButton);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Constants.appFont);
        return label;
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = controller.getUserLogged().get().getCli().username();
            String data = dataField.getText();
            String descrizione = descrizioneField.getText();
            int peso = Integer.parseInt(pesoField.getText());
            int circPuntoVita = Integer.parseInt(circPuntoVitaField.getText());
            int circBraccio = Integer.parseInt(circBraccioField.getText());
            int circGambe = Integer.parseInt(circGambeField.getText());

            if (!data.isEmpty() && !descrizione.isEmpty()) {
                if (isValidDate(data)) {
                    // Call the controller method to save the update
                    boolean success = controller.saveClientUpdate(username, data, descrizione, peso, circPuntoVita, circBraccio, circGambe);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Aggiornamento salvato con successo");
                    } else {
                        JOptionPane.showMessageDialog(null, "Errore nel salvataggio dell'aggiornamento");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Inserisci una data valida nel formato dd/MM/yyyy", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tutti i campi devono essere compilati");
            }
        }

        private boolean isValidDate(String date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                sdf.parse(date);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
    }
}
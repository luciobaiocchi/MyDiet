package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReviewPanel extends JPanel {
    private final Controller controller;
    private final String nutritionistUsername;
    private final JTextArea descriptionField;
    private final JComboBox<Integer> starRating;
    private final JButton submitButton;

    public ReviewPanel(Controller controller, String nutritionistUsername) {
        this.controller = controller;
        this.nutritionistUsername = nutritionistUsername;
        this.setLayout(new GridLayout(4, 1));

        // Create input fields for the review
        descriptionField = new JTextArea(5, 20);
        starRating = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        submitButton = new JButton("Invia Recensione");

        // Set font for input fields
        Font appFont = Constants.appFont;
        descriptionField.setFont(appFont);
        starRating.setFont(appFont);
        submitButton.setFont(appFont);

        // Add components to the panel
        this.add(createStyledLabel("Descrizione (opzionale):"));
        this.add(new JScrollPane(descriptionField));
        this.add(createStyledLabel("Stelle:"));
        this.add(starRating);
        this.add(submitButton);

        // Add action listener to the submit button
        submitButton.addActionListener(new SubmitButtonListener());
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Constants.appFont);
        return label;
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = descriptionField.getText();
            int stars = (int) starRating.getSelectedItem();
            String clientUsername = controller.getUserLogged().get().getCli().username();

            // Call the controller method to save the review
            boolean success = controller.saveReview(clientUsername, nutritionistUsername, description, stars);
            if (success) {
                JOptionPane.showMessageDialog(null, "Recensione inviata con successo");
            } else {
                JOptionPane.showMessageDialog(null, "Errore nell'invio della recensione");
            }
        }
    }
}
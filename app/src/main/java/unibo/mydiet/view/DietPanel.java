package unibo.mydiet.view;

import unibo.mydiet.model.diet.Dieta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DietPanel extends JPanel {
    private final Dieta diet;

    public DietPanel(final Dieta diet) {
        this.diet = diet;
        buildPanel();
    }

    private void buildPanel() {
        GridLayout layout = new GridLayout(2, 2);
        this.setLayout(layout);

        JLabel label1 = new JLabel("Day:");
        JLabel label2 = new JLabel("2");
        JLabel label3 = new JLabel("3");
        JLabel label4 = new JLabel("4");

        JComboBox<String> sceltaGiorno = new JComboBox<>();
        sceltaGiorno.setToolTipText("Choose Day");
        sceltaGiorno.addItem("Monday");
        sceltaGiorno.addItem("Tuesday");
        sceltaGiorno.addItem("Wednesday");
        sceltaGiorno.addItem("Thursday");
        sceltaGiorno.addItem("Friday");
        sceltaGiorno.addItem("Saturday");
        sceltaGiorno.addItem("Sunday");

        // Add ActionListener to handle day selection
        sceltaGiorno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDay = (String) sceltaGiorno.getSelectedItem();
                System.out.println("Selected day: " + selectedDay);
                // Update panel based on selected day, if needed
            }
        });

        // Add components to the panel
        this.add(label1);
        this.add(sceltaGiorno); // Add combo box directly to the panel
        this.add(label2);
        this.add(label3);
        this.add(label4);
    }
}
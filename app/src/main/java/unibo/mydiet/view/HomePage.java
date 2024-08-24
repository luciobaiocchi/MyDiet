package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.UserType;

import javax.swing.*;
import java.awt.*;

public abstract class HomePage extends JPanel {
    final JPanel centerPanel = new JPanel();

    public abstract void addTable();

    public HomePage(final Controller controller) {

        // Imposta il layout del pannello HomePage
        this.setLayout(new BorderLayout());

        // Pannello principale con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Pannello superiore (North) per il titolo
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel northLabel = new JLabel("MyDiet", SwingConstants.CENTER);
        northPanel.setBackground(Constants.BG_COLOR);
        northLabel.setOpaque(false);
        northLabel.setForeground(Color.white);
        northLabel.setFont(Constants.appFont.deriveFont(Font.PLAIN, 50));
        northPanel.add(northLabel, BorderLayout.CENTER);
        mainPanel.add(northPanel, BorderLayout.NORTH);


        // Pannello centrale (Center) per i dati personali utente
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Constants.BG_COLOR);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Pannello sinistro (West) per i pulsanti di navigazione
        JPanel westPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        westPanel.setBackground(Constants.BG_COLOR);
        JButton datiPersonaliButton = createStyledButton("Dati personali");
        datiPersonaliButton.setFont(Constants.appFont);
        JButton visualizzaObiettivoButton = createStyledButton("Visualizza obiettivo");
        visualizzaObiettivoButton.setFont(Constants.appFont);
        JButton modificaPasswordButton = createStyledButton("Modifica password");
        modificaPasswordButton.setFont(Constants.appFont);

        westPanel.add(datiPersonaliButton);
        westPanel.add(visualizzaObiettivoButton);
        westPanel.add(modificaPasswordButton);
        mainPanel.add(westPanel, BorderLayout.WEST);

        // Pannello inferiore (South) per le opzioni di visualizzazione
        JPanel southPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        southPanel.setBackground(Constants.BG_COLOR);
        JButton aggiornamentiDietaButton = createStyledButton("Aggiornamenti e dieta");
        JButton visualizzaMiglioriNutrizionistiButton = createStyledButton("Visualizza migliori nutrizionisti");
        JButton visualizzaProfiloButton = createStyledButton("Visualizza profilo");
        southPanel.add(aggiornamentiDietaButton);
        southPanel.add(visualizzaMiglioriNutrizionistiButton);
        southPanel.add(visualizzaProfiloButton);
        southPanel.setPreferredSize(new Dimension(1200, 120)); // Altezza di 80 pixel
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Aggiungi il pannello principale al pannello HomePage
        this.add(mainPanel, BorderLayout.CENTER);
    }


    // Metodo per stilizzare un JButton
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Constants.BTN_COLOR);
        button.setFocusPainted(false);
        button.setFont(Constants.appFont);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public void addTable(JTable table) {
        centerPanel.add(table, BorderLayout.CENTER);
    }
}

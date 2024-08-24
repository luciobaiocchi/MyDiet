package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class HomePageCli extends JPanel {

    public HomePageCli(final Controller controller) {
        // Imposta il layout del pannello HomePageCli
        this.setLayout(new BorderLayout());

        // Pannello principale con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Pannello superiore (North) per il titolo
        JLabel titleLabel = new JLabel("MyDiet", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Pannello centrale (Center) per i dati personali utente
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(242, 204, 91));
        JLabel centerLabel = new JLabel("Dati personali utente", SwingConstants.CENTER);
        centerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        centerPanel.add(centerLabel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Pannello sinistro (West) per i pulsanti di navigazione
        JPanel westPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        westPanel.setBackground(new Color(255, 255, 255));
        JButton datiPersonaliButton = createButton("Dati personali");
        JButton visualizzaObiettivoButton = createButton("Visualizza obiettivo");
        JButton modificaPasswordButton = createButton("Modifica password");
        westPanel.add(datiPersonaliButton);
        westPanel.add(visualizzaObiettivoButton);
        westPanel.add(modificaPasswordButton);
        mainPanel.add(westPanel, BorderLayout.WEST);

        // Pannello inferiore (South) per le opzioni di visualizzazione
        JPanel southPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton aggiornamentiDietaButton = createButton("Aggiornamenti e dieta");
        JButton visualizzaMiglioriNutrizionistiButton = createButton("Visualizza migliori nutrizionisti");
        JButton visualizzaProfiloButton = createButton("Visualizza profilo");
        southPanel.add(aggiornamentiDietaButton);
        southPanel.add(visualizzaMiglioriNutrizionistiButton);
        southPanel.add(visualizzaProfiloButton);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Aggiungi il pannello principale al pannello HomePageCli
        this.add(mainPanel, BorderLayout.CENTER);
    }

    // Metodo per creare bottoni con stile coerente
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(242, 204, 91));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
        return button;
    }
}

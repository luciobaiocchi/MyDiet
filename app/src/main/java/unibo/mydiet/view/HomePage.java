package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.UserType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public abstract class HomePage extends JPanel {
    final JPanel centerPanel = new JPanel();
    private final JButton button1 = createStyledButton("bottone 1");
    private final JButton button2 = createStyledButton("bottone 2");
    private final JButton button3 = createStyledButton("bottone 3");
    private final JButton button4 = createStyledButton("bottone 4");
    private final JButton button5 = createStyledButton("bottone 5");
    private final JButton button6 = createStyledButton("bottone 6");
    private final List<JButton> buttons = List.of(button1, button2, button3, button4, button5, button6);

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
        centerPanel.setOpaque(true);

        // Wrap the centerPanel with JScrollPane
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setBackground(Constants.BG_COLOR);
        scrollPane.getViewport().setBackground(Constants.BG_COLOR);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Pannello sinistro (West) per i pulsanti di navigazione
        JPanel westPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        westPanel.setBackground(Constants.BG_COLOR);

        button1.setFont(Constants.appFont);
        button2.setFont(Constants.appFont);
        button3.setFont(Constants.appFont);

        westPanel.add(button1);
        westPanel.add(button2);
        westPanel.add(button3);

        mainPanel.add(westPanel, BorderLayout.WEST);

        // Pannello inferiore (South) per le opzioni di visualizzazione
        JPanel southPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        southPanel.setBackground(Constants.BG_COLOR);

        southPanel.add(button4);
        southPanel.add(button5);
        southPanel.add(button6);

        southPanel.setPreferredSize(new Dimension(1200, 120)); // Altezza di 120 pixel
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

    public void addTable(final JTable table) {
        table.setBackground(Constants.BG_COLOR); // Set the background for the table
        table.setForeground(Color.ORANGE); // Example text color

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Constants.BG_COLOR);
        scrollPane.setBackground(Constants.BG_COLOR);

        centerPanel.removeAll();
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public void setButtonAction(int index, ActionListener action) {
        buttons.get(index).addActionListener(action);
    }

    public void setButtonTitle(int index, String title) {
        buttons.get(index).setText(title);
    }
}

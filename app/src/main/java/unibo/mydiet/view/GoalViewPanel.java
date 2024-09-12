package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GoalViewPanel extends JPanel {
    private final Controller controller;
    private final JTextField searchField;
    private final JButton searchButton;
    private final JPanel resultPanel;

    public GoalViewPanel(Controller controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());

        // Create search panel
        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("Inserisci Username Cliente:");
        searchField = new JTextField(20);
        searchButton = new JButton("Cerca");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Create result panel
        resultPanel = new JPanel(new BorderLayout());

        // Add components to main panel
        this.add(searchPanel, BorderLayout.NORTH);
        this.add(resultPanel, BorderLayout.CENTER);

        // Add action listener to search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchClient();
            }
        });
    }

    private void searchClient() {
        String username = searchField.getText();
    }
}
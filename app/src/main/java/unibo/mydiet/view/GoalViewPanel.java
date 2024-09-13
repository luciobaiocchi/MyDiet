package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.Goal;
import unibo.mydiet.model.Aggiornamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GoalViewPanel extends JPanel {
    private final Controller controller;
    private final JTextField searchField;
    private final JButton searchButton;
    private final JComboBox<String> viewOptions;
    private final JPanel resultPanel;

    public GoalViewPanel(Controller controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());

        // Create search panel
        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("Inserisci Username Cliente:");
        searchField = new JTextField(20);
        searchButton = new JButton("Cerca");
        viewOptions = new JComboBox<>(new String[]{"Visualizza Obiettivo", "Visualizza Aggiornamenti"});

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(viewOptions);
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
                String selectedOption = (String) viewOptions.getSelectedItem();
                String client = searchField.getText();
                if ("Visualizza Obiettivo".equals(selectedOption)) {
                    getClientGoal(client);
                } else if ("Visualizza Aggiornamenti".equals(selectedOption)) {
                    getClientAggiornamenti(client);
                }
            }
        });
    }

    private void getClientGoal(final String client) {
        if (controller.getClientUsernames().contains(client)) {
            Goal goal = controller.getCLientGoal(client);
            if (goal != null) {
                JTable goalTable = TableFactory.getGoalTable(goal);
                resultPanel.removeAll();
                resultPanel.add(new JScrollPane(goalTable), BorderLayout.CENTER);
                resultPanel.revalidate();
                resultPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Nessun obiettivo trovato per questo cliente.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cliente non trovato.");
        }
    }

    private void getClientAggiornamenti(final String client) {
        if (controller.getClientUsernames().contains(client)) {
            List<Aggiornamento> aggiornamenti = controller.getCLientAggiornamento(client);
            if (aggiornamenti != null && !aggiornamenti.isEmpty()) {
                JTable aggiornamentiTable = TableFactory.getAggiornamentiTable(aggiornamenti);
                resultPanel.removeAll();
                resultPanel.add(new JScrollPane(aggiornamentiTable), BorderLayout.CENTER);
                resultPanel.revalidate();
                resultPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Nessun aggiornamento trovato per questo cliente.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cliente non trovato.");
        }
   }
}
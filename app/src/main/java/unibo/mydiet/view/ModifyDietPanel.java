package unibo.mydiet.view;
import java.util.ArrayList;
import java.util.List;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.Pair;
import unibo.mydiet.model.diet.*;
import unibo.mydiet.model.users.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ModifyDietPanel extends JPanel {
    private final Controller controller;
    private JComboBox<String> clientComboBox;
    private JComboBox<NomeGiorno> giornoComboBox;
    private JComboBox<NomePasto> pastoComboBox;
    private JTextField nomeRicettaField;
    private JComboBox<String> difficoltaComboBox;
    private JTextField tempoPreparazioneField;
    private JTextArea descrizioneArea;
    private final List<Pair<JComboBox<String>, JTextField>> listaAlimenti = new ArrayList<>();
    private JButton saveButton;
    private JPanel alimentiPanel;
    private JButton addAlimentoButton;

    // Liste per memorizzare gli alimenti e le quantità
    private final List<String> alimentiList = new ArrayList<>();

    public ModifyDietPanel(Controller controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        initializeComponents();
        buildPanel();
    }

    private void initializeComponents() {
        clientComboBox = new JComboBox<>();
        giornoComboBox = new JComboBox<>(NomeGiorno.values());
        pastoComboBox = new JComboBox<>(NomePasto.values());
        nomeRicettaField = new JTextField();
        difficoltaComboBox = new JComboBox<>(new String[]{"facile", "medio", "difficile"});
        tempoPreparazioneField = new JTextField();
        descrizioneArea = new JTextArea(5, 20);
        saveButton = new JButton("Salva");
        alimentiPanel = new JPanel();
        alimentiPanel.setLayout(new BoxLayout(alimentiPanel, BoxLayout.Y_AXIS));
        addAlimentoButton = new JButton("Aggiungi Alimento");

        // Load clients into the combo box
        List<String> clients = controller.getClientUsernames();
        for (String client : clients) {
            clientComboBox.addItem(client);
        }

        // Add initial alimento selector
        addAlimentoSelector();

        // Add action listener to addAlimentoButton
        addAlimentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAlimentoSelector();
            }
        });
    }

    private void buildPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(clientComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Giorno:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(giornoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Pasto:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(pastoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Nome Ricetta:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nomeRicettaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Difficoltà:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(difficoltaComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Tempo Preparazione:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tempoPreparazioneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(new JLabel("Descrizione:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        inputPanel.add(new JScrollPane(descrizioneArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        inputPanel.add(alimentiPanel, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addAlimentoButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRecipe();
            }
        });
    }

    private void addAlimentoSelector() {
        JPanel alimentoSelectorPanel = new JPanel(new FlowLayout());
        JComboBox<String> alimentoComboBox = new JComboBox<>();
        JTextField quantitaField = new JTextField(5);

        // Load alimenti into the combo box
        List<Alimento> alimenti = controller.getAlimenti();
        for (Alimento alimento : alimenti) {
            alimentoComboBox.addItem(alimento.getIdAlimento() + " - " + alimento.getNome());
        }

        alimentoSelectorPanel.add(new JLabel("Alimento:"));
        alimentoSelectorPanel.add(alimentoComboBox);

        alimentoSelectorPanel.add(new JLabel("Quantità:"));
        alimentoSelectorPanel.add(quantitaField);

        listaAlimenti.add(new Pair<>(alimentoComboBox, quantitaField));
        alimentiPanel.add(alimentoSelectorPanel);
        alimentiPanel.revalidate();
        alimentiPanel.repaint();
    }

    private void saveRecipe() {
        String clientUsername = (String) clientComboBox.getSelectedItem();
        NomeGiorno giorno = (NomeGiorno) giornoComboBox.getSelectedItem();
        NomePasto pasto = (NomePasto) pastoComboBox.getSelectedItem();
        String nomeRicetta = nomeRicettaField.getText();
        String difficolta = (String) difficoltaComboBox.getSelectedItem();
        String tempoPreparazione = tempoPreparazioneField.getText();
        String descrizione = descrizioneArea.getText();

        Ricetta ricetta = new Ricetta(new ArrayList<>(), nomeRicetta, difficolta, tempoPreparazione, descrizione);

        // Clear the lists before populating them
        alimentiList.clear();

        // Itera attraverso la listaAlimenti
        for (Pair<JComboBox<String>, JTextField> pair : listaAlimenti) {
            JComboBox<String> alimentoComboBox = pair.getX();
            JTextField quantitaField = pair.getY();

            String alimentoInfo = (String) alimentoComboBox.getSelectedItem();
            // Estrai la parte dell'ID dalla stringa selezionata
            String idPart = alimentoInfo.split(" - ")[0];
            try {
                int quantita = Integer.parseInt(quantitaField.getText());

                Alimento alimento = controller.getAlimentoById(idPart);
                alimento.setPeso(quantita);
                ricetta.addIngrediente(alimento);


                // Aggiungi l'alimento alla liste
                alimentiList.add(alimentoInfo);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Errore nel formato dell'ID o della quantità dell'alimento: " + alimentoInfo, "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        controller.updateRicetta(clientUsername, giorno, pasto, ricetta);
        System.out.println(ricetta.getIngredienti().stream()
                .map(alimento -> alimento.getNome() + " - " + alimento.getPeso())
                .reduce("", (acc, s) -> acc + s + "\n"));
        JOptionPane.showMessageDialog(this, "Ricetta salvata con successo!");
    }
}
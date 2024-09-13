package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
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
    private JTextField difficoltaField;
    private JTextField tempoPreparazioneField;
    private JTextArea descrizioneArea;
    private JTable alimentiTable;
    private DefaultTableModel alimentiTableModel;
    private JButton saveButton;

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
        difficoltaField = new JTextField();
        tempoPreparazioneField = new JTextField();
        descrizioneArea = new JTextArea(5, 20);
        alimentiTableModel = new DefaultTableModel(new Object[]{"ID", "Quantità"}, 0);
        alimentiTable = new JTable(alimentiTableModel);
        saveButton = new JButton("Salva");

        // Load clients into the combo box
        List<String> clients = controller.getClientUsernames();
        for (String client : clients) {
            clientComboBox.addItem(client);
        }
    }

    private void buildPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("Cliente:"));
        inputPanel.add(clientComboBox);
        inputPanel.add(new JLabel("Giorno:"));
        inputPanel.add(giornoComboBox);
        inputPanel.add(new JLabel("Pasto:"));
        inputPanel.add(pastoComboBox);
        inputPanel.add(new JLabel("Nome Ricetta:"));
        inputPanel.add(nomeRicettaField);
        inputPanel.add(new JLabel("Difficoltà:"));
        inputPanel.add(difficoltaField);
        inputPanel.add(new JLabel("Tempo Preparazione:"));
        inputPanel.add(tempoPreparazioneField);
        inputPanel.add(new JLabel("Descrizione:"));
        inputPanel.add(new JScrollPane(descrizioneArea));

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(alimentiTable), BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRecipe();
            }
        });
    }

    private void saveRecipe() {
        String clientUsername = (String) clientComboBox.getSelectedItem();
        NomeGiorno giorno = (NomeGiorno) giornoComboBox.getSelectedItem();
        NomePasto pasto = (NomePasto) pastoComboBox.getSelectedItem();
        String nomeRicetta = nomeRicettaField.getText();
        String difficolta = difficoltaField.getText();
        String tempoPreparazione = tempoPreparazioneField.getText();
        String descrizione = descrizioneArea.getText();

        Ricetta ricetta = new Ricetta(null, nomeRicetta, difficolta, tempoPreparazione, descrizione);

        for (int i = 0; i < alimentiTableModel.getRowCount(); i++) {
            int id = Integer.parseInt(alimentiTableModel.getValueAt(i, 0).toString());
            int quantita = Integer.parseInt(alimentiTableModel.getValueAt(i, 1).toString());
            Alimento alimento = controller.getAlimentoById(id);
            alimento.setPeso(quantita);
            ricetta.addIngrediente(alimento);
        }

        controller.updateRicetta(clientUsername, giorno, pasto, ricetta);
        JOptionPane.showMessageDialog(this, "Ricetta salvata con successo!");
    }
}
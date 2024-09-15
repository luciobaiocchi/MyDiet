package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.diet.Dieta;
import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.UserType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomePageCli extends HomePage{
    private final Controller controller;
    private enum ClientContext{
        HOME_PAGE,
        NUTRIZIONIST,
        DIET,
    }
    private ClientContext context = ClientContext.HOME_PAGE;

    public HomePageCli(Controller controller) {
        super(controller);
        this.controller = controller;
        setButtonTitle(4,"Visualizza Nutrizionisti");
        setButtonTitle(5,"Visualizza Profilo");
        setButtonTitle(3,"Dieta e Aggiornamenti ");
        setButtonsNames();
        setButtonsActions();

        super.setButtonAction(3, e -> {
            context = ClientContext.DIET;
            setButtonsNames();
            addDiet();
            System.out.println("Dieta e Aggiornamenti");
        });

        super.setButtonAction(4, e -> {
            context = ClientContext.NUTRIZIONIST;
            setButtonsNames();
            addNutList();
            System.out.println("Visualize Nutrizionisti");
        });

        setButtonTitle(5,"Visualizza Profilo");
        setButtonAction(5, e -> {
            context = ClientContext.HOME_PAGE;
            setButtonsNames();
            addTable();
            System.out.println("Visualizza Profilo");
        });
    }
    public void addTable() {
        if (controller.getUserLogged().isPresent() && controller.getUserLogged().get().getType() == UserType.CLIENT) {
            final Client client = controller.getUserLogged().get().getCli();
            System.out.println(client);
            JTable table = TableFactory.getCliProfile(client);
            addTable(table);
        }
    }



    private void setButtonsNames() {
        if (context == ClientContext.HOME_PAGE) {
            setButtonTitle(0, "Dati Personali");
            setButtonTitle(1, "Visualiza Obbiettivo");
            setButtonTitle(2, "Visualiza Password");
        } else if (context == ClientContext.NUTRIZIONIST) {
            setButtonTitle(0,"Lista completa");
            setButtonTitle(1,"Più stellati");
            setButtonTitle(2,"Più soddisfatti");
        }else if (context == ClientContext.DIET) {
            setButtonTitle(0,"Visualizza Dieta");
            setButtonTitle(1,"Carica aggiornamento");
            setButtonTitle(2,"visualizza aggiornamenti");
        }
    }

    private void setButtonsActions(){
        setButtonAction(0, e -> {
            if (context == ClientContext.HOME_PAGE) {
                System.out.println("Dati Personali");
                addTable();
            } else if (context == ClientContext.NUTRIZIONIST) {
                System.out.println("Lista completa");
                addNutList();
            } else if (context == ClientContext.DIET) {
                System.out.println("Visualizza Dieta");
                addDiet();
            }
        });
        setButtonAction(1, e -> {
            if (context == ClientContext.HOME_PAGE) {
                System.out.println("Visualizza Obbiettivo");
                addGoal();
            } else if (context == ClientContext.NUTRIZIONIST) {
                System.out.println("Più stellati");
                addNutHigerRating();
            } else if (context == ClientContext.DIET){
                System.out.println("carica aggiornamenti");
                addNuewUpdate();
            }
        });
        setButtonAction(2, e -> {
            if (context == ClientContext.HOME_PAGE) {
                System.out.println("visualizza psw");
                showPsw();
            }else if (context == ClientContext.NUTRIZIONIST) {
                System.out.println("più soddisfatti");
                addNutMoreSatisfied();
            }else if (context == ClientContext.DIET){
                System.out.println("Visualizza Aggiornamenti");
                addClientUpdates();
            }
        });

    }

    private void addNutList() {
        System.out.println("addNutList");
        JTable table = TableFactory.getNutList(controller.getNutrizionists());
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String nutUsername = table.getValueAt(row, 0).toString(); // Assuming the username is in the first column
                        System.out.println("Selected nutritionist:" + nutUsername);
                        showNutProfile(nutUsername);
                    }
                }
            }
        });
        addTable(table);
    }




    private void showNutProfile(String username) {
    // Retrieve the nutritionist details from the controller
    Nutrizionist nutritionist = controller.getNutrizionist(username);
    if (nutritionist != null) {
        // Create a panel to hold the profile table, tariff table, and action panel
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());

        // Display the nutritionist profile
        JTable nutTable = TableFactory.getNutProfileNopsw(nutritionist);
        profilePanel.add(new JScrollPane(nutTable), BorderLayout.NORTH);

        // Add tariff table
        JTable tariffTable = TableFactory.getTariffTable(nutritionist);
        JScrollPane tariffScrollPane = new JScrollPane(tariffTable);
        tariffScrollPane.setPreferredSize(tariffTable.getPreferredSize());
        profilePanel.add(tariffScrollPane, BorderLayout.CENTER);

        // Check if the client is already a client of the nutritionist
        Client client = controller.getUserLogged().get().getCli();
        boolean isClientOfNutritionist = controller.isClientOfNutritionist(client.username(), username);

        if (!isClientOfNutritionist) {
            // Add duration selection and start path button
            JPanel actionPanel = new JPanel();
            JLabel durationLabel = new JLabel("Durata (mesi):");
            JComboBox<Integer> durationComboBox = new JComboBox<>(new Integer[]{1, 3, 6, 12});
            JButton startPathButton = new JButton("Inizia Percorso");

            actionPanel.add(durationLabel);
            actionPanel.add(durationComboBox);
            actionPanel.add(startPathButton);

            profilePanel.add(actionPanel, BorderLayout.SOUTH);
        } else {
            // Add review panel
            ReviewPanel reviewPanel = new ReviewPanel(controller, username);
            profilePanel.add(reviewPanel, BorderLayout.NORTH);
        }

        // Set the profile panel as the center panel
        setCenterPanel(profilePanel);
        profilePanel.revalidate();
        profilePanel.repaint();
    } else {
        JOptionPane.showMessageDialog(null, "Nutrizionista non trovato");
    }
}


    private void addNutHigerRating() {
        System.out.println("addNutHigerRating");
        addTable(TableFactory.getNutList(controller.getHigerratingList()));
    }
    private void addNutMoreSatisfied() {
        System.out.println("addNutHigerRating");
        super.addTable(TableFactory.getNutListMostSatisied(controller.getMoreSatisfiedlist()));
    }
    private void addGoal(){
        addTable(TableFactory.getGoalTable(controller.getCLientGoal(controller.getUserLogged().get().getCli().username())));
    }
    private void showPsw(){
        addTable(TableFactory.getPswTable(controller.getUsrPsw()));
    }
    private void addDiet() {
        Dieta diet = controller.getDiet();
        if (diet != null) {
            DietPanel dietPanel = new DietPanel(diet);
            setCenterPanel(dietPanel);
            dietPanel.revalidate(); // Ensure the layout manager recalculates the layout
            dietPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Dieta non trovata");
        }
    }
    private void addClientUpdates() {
        System.out.println("Visualizza Aggiornamenti");
        addTable(TableFactory.getAggiornamentiTable(controller.getCLientAggiornamento(controller.getUserLogged()
                .get().getCli().username())));
    }
    private void addNuewUpdate(){
        InsertUpdatePanel insertUpdatePanel = new InsertUpdatePanel(controller);
        setCenterPanel(insertUpdatePanel);
        insertUpdatePanel.revalidate(); // Ensure the layout manager recalculates the layout
        insertUpdatePanel.repaint();
        System.out.println("Carica aggiornamenti");
    }

}

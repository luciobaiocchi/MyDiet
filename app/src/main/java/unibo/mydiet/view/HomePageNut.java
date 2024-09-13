package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.UserType;

import javax.swing.*;
import java.awt.*;

public class HomePageNut extends HomePage {
    private final Controller controller;
    private enum NutContext {
        HOME_PAGE,
        VIEW_CLIENT,
        MODIFY_PROFILE,
    }
    private NutContext context = NutContext.HOME_PAGE;

    public HomePageNut(Controller controller) {
        super(controller);
        this.controller = controller;
        setButtonTitle(4,"Visualizza Clienti");
        setButtonTitle(5,"Visualizza Profilo");
        setButtonTitle(3,"Modifica profilo");
        setButtonsActions();
        setButtonsNames();
        setButtonAction(3, e -> {
            context = NutContext.MODIFY_PROFILE;
            System.out.println("Modifica profilo");
            setCenterPanel(getPswPanel());
            setButtonsNames();
        });
        setButtonAction(4, e -> {
            context = NutContext.VIEW_CLIENT;
            System.out.println("Visualizza Clienti");
            visualizeClientList();
            setButtonsNames();
        });
        setButtonAction(5, e -> {
            context = NutContext.HOME_PAGE;
            System.out.println("Visualizza Profilo");
            addTable();
            setButtonsNames();
        });
    }

    private void setButtonsNames() {
        if (context == NutContext.HOME_PAGE) {
            super.setButtonTitle(0, "Dati Personali");
            super.setButtonTitle(1, "Percorsi Formazione");
            super.setButtonTitle(2, "Visualiza Password");
        } else if (context == NutContext.VIEW_CLIENT) {
            super.setButtonTitle(0,"Lista completa");
            super.setButtonTitle(1,"Obbiettivo/Aggiornamenti");
            super.setButtonTitle(2,"Modifica Dieta");
        }else if (context == NutContext.MODIFY_PROFILE) {
            super.setButtonTitle(0,"Modifica Password");
            super.setButtonTitle(1,"Modifica Tariffario");
            super.setButtonTitle(2,"Aggiungi Percorso");
        }
    }

    private void setButtonsActions(){
        super.setButtonAction(0, e -> {
            if (context == NutContext.HOME_PAGE) {
                System.out.println("Dati Personali");
                addTable();
            } else if (context == NutContext.VIEW_CLIENT) {
                System.out.println("Lista completa");
                visualizeClientList();
            } else if (context == NutContext.MODIFY_PROFILE) {;
                System.out.println("Modifica Password");
                setCenterPanel(getPswPanel());
            }
        });
        super.setButtonAction(1, e -> {
            if (context == NutContext.HOME_PAGE) {
                System.out.println("Percorsi Formazione");
                addFormation();
            } else if (context == NutContext.VIEW_CLIENT) {
                System.out.println("Obbiettivo/Aggiornamenti");
                createSearchPanel();
            } else if (context == NutContext.MODIFY_PROFILE){
                System.out.println("Modifica Tariffario");
                visualizeTarifPanel();
            }
        });
        super.setButtonAction(2, e -> {
            if (context == NutContext.HOME_PAGE) {
                System.out.println("Visualiza Password");
                showPsw();
            }else if (context == NutContext.VIEW_CLIENT) {
                System.out.println("Modifica Dieta");
                createModifyDietPanel();
            }else if (context == NutContext.MODIFY_PROFILE){
                System.out.println("Aggiungi Percorso");
                addPercorsoFormazionePanel();
            }
        });

    }

    private void addFormation(){
        if (controller.getNutFormation().isPresent()){
            JTable table = TableFactory.getFormationTable(controller.getNutFormation().get());
            addTable(table);
        }
    }
    private void showPsw(){
        addTable(TableFactory.getPswTable(controller.getUserLogged().get().getNut().getPassword()));
    }

    private JPanel getPswPanel(){
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Inserisci nuova password");
        JTextField psw = new JTextField();
        JButton save = new JButton("Salva");

        label.setFont(label.getFont().deriveFont(16f));
        psw.setFont(psw.getFont().deriveFont(16f));
        save.setFont(save.getFont().deriveFont(16f));
        psw.setPreferredSize(new Dimension(200, 30));
        save.addActionListener(e -> {
            System.out.println(psw.getText());
            if (controller.updateNutPsw(psw.getText())){
                JOptionPane.showMessageDialog(null, "Password aggiornata con successo");
            };
        });
        panel.add(label);
        panel.add(psw);
        panel.add(save);
        return panel;
    }

    private void visualizeTarifPanel() {
        controller.updateNutTari();
        TariffarioPanel tariffarioPanel = new TariffarioPanel(controller);
        setCenterPanel(tariffarioPanel);
    }

    private void addPercorsoFormazionePanel() {
        PercorsoFormazionePanel percorsoFormazionePanel = new PercorsoFormazionePanel(controller);
        setCenterPanel(percorsoFormazionePanel);
    }

    private void visualizeClientList() {
        JTable table = TableFactory.getClientListTable(controller.getClientUsernames());
        super.addTable(table);
    }

    private void createSearchPanel() {
        JPanel searchPanel = new GoalViewPanel(controller);
        setCenterPanel(searchPanel);
    }

    private void createModifyDietPanel() {
        JPanel searchPanel = new ModifyDietPanel(controller);
        setCenterPanel(searchPanel);
    }


    @Override
    public void addTable() {
        if (controller.getUserLogged().isPresent() && controller.getUserLogged().get().getType() == UserType.NUTRIZIONIST) {
            final Nutrizionist nutrizionist = controller.getUserLogged().get().getNut();
            System.out.println(nutrizionist);
            JTable table = TableFactory.getNutProfile(nutrizionist);
            super.addTable(table);
        }
    }
}
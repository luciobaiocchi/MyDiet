package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements unibo.mydiet.view.api.MainFrame {
    final Controller controller = new Controller();
    final CardLayout mainLayout = new CardLayout();
    final JPanel contentPane = new JPanel(mainLayout);  // Usa un JPanel per ospitare i pannelli con CardLayout
    final HomePageCli homePageCli = new HomePageCli(controller);
    final HomePageNut homePageNut = new HomePageNut(controller);
    final LoginPanel loginPanel = new LoginPanel(controller);

    public MainFrame() {
        this.setTitle("MyDiet");
        this.setContentPane(contentPane);
        loginPanel.addObserver(this);
        contentPane.add(loginPanel, "Login");
        contentPane.add(homePageCli, "HomePageCli");
        contentPane.add(homePageNut, "HomePageNut");

        this.setSize(1400, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300, 100);
        this.setVisible(true);

        loadLoginPanel();
    }

    @Override
    public void loadLoginPanel() {
        mainLayout.show(contentPane, "Login");
    }

    @Override
    public void onPanelChange(String panelName) {
        if (panelName.equals("HomePageCli")) {
            homePageCli.addTable();
        } else if (panelName.equals("HomePageNut")) {
            homePageNut.addTable();
        }
        mainLayout.show(contentPane, panelName);
        homePageNut.addTable();
        System.out.println("Panel changed to: " + panelName);
    }
}

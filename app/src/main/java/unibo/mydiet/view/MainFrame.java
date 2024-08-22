package unibo.mydiet.view;

import unibo.mydiet.controller.Controller;

import javax.swing.*;

public class MainFrame extends JFrame  implements unibo.mydiet.view.api.MainFrame {
    final Controller controller = new Controller();
    final LoginPanel loginPanel = new LoginPanel();

    public MainFrame() {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadLoginPanel();
        this.setVisible(true);
    }

    @Override
    public void loadLoginPanel() {
        this.add(loginPanel);
        loginPanel.setVisible(true);
    }
}

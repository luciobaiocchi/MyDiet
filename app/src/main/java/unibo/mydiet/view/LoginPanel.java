package unibo.mydiet.view;
import unibo.mydiet.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoginPanel extends JPanel implements PanelChangeSubject{

    private final Controller controller;
    // Componenti per il login
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    // Componenti per la registrazione
    private JTextField newUsernameField;
    private JPasswordField newPasswordField;
    private JButton createAccountButton;
    private JButton backButton;

    // Colorazione moderna
    private final Color backgroundColor = new Color(34, 40, 49);
    private final Color panelColor = new Color(0, 15, 40);
    private final Color textColor = new Color(200, 200, 200);
    private final Color buttonColor = new Color(136, 105, 136);
    private final Color buttonTextColor = new Color(237, 193, 141, 255);
    private List<PanelChangeObserver> observers = new ArrayList<>();

    public LoginPanel(final Controller controller) {
        this.controller = controller;
        setLayout(new CardLayout());
        setBackground(backgroundColor);

        // Pannello di login
        JPanel loginPanel = createLoginPanel();

        // Pannello di registrazione
        final JPanel registerPanel = createRegisterPanel();


        // Aggiungiamo i pannelli al CardLayout
        add(loginPanel, "Login");
        add(registerPanel, "Register");

        // Mostriamo il pannello di login come predefinito
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Login");

    }

    // Metodo per creare il pannello di login
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(panelColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Aggiungi immagine
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("app/src/main/resources/unibo/mydiet/images/logo_MyDiet_dark.jpeg"); // Path dell'immagine
        Image image = imageIcon.getImage(); // Ottieni l'immagine
        Image scaledImage = image.getScaledInstance(400, 400, Image.SCALE_SMOOTH); // Scala l'immagine
        imageIcon = new ImageIcon(scaledImage); // Converti l'immagine scalata in ImageIcon
        imageLabel.setIcon(imageIcon);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(imageLabel, gbc);

        // Campi di testo
        usernameField = new JTextField(15);
        usernameField.setFont(Constants.appFont);
        passwordField = new JPasswordField(15);
        passwordField.setFont(Constants.appFont);
        loginButton = createStyledButton("Login");
        loginButton.setFont(Constants.appFont);
        loginButton.setOpaque(true);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.copyValueOf(passwordField.getPassword());

            if (authenticateCli(username, password)) {
                notifyObservers("HomePageCli");
                controller.getClientInfo(username);
                System.out.println("Login riuscito");
            } else {
                System.out.println("Login fallito");
            }
        });

        registerButton = createStyledButton("Register");
        registerButton.setOpaque(true);
        registerButton.setFont(Constants.appFont);
        registerButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) getLayout();
            cl.show(this, "Register");
        });
        styleTextField(usernameField);
        styleTextField(passwordField);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel usernameLabel = createStyledLabel("Username:");
        usernameLabel.setFont(Constants.appFont);
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = createStyledLabel("Password:");
        passwordLabel.setFont(Constants.appFont);
        panel.add(passwordLabel, gbc);


        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        return panel;
    }

    // Metodo per creare il pannello di registrazione
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(panelColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campi di testo per la registrazione
        newUsernameField = new JTextField(15);
        newPasswordField = new JPasswordField(15);
        createAccountButton = createStyledButton("Create Account");
        backButton = createStyledButton("Back to Login");

        styleTextField(newUsernameField);
        styleTextField(newPasswordField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(createStyledLabel("New Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(newUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createStyledLabel("New Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(newPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(createAccountButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(backButton, gbc);

        return panel;
    }

    // Metodo di autenticazione
    private boolean authenticateCli(final String username, final String password) {
        return controller.loginClient(username, password);
    }
    // Metodo di registrazione
    private boolean registerCli(final String username, final String password) {
        return false;
    }

    // Metodo di autenticazione
    private boolean authenticateNut(final String username, final String password) {
        return false;
    }
    // Metodo di registrazione
    private boolean registerNut(final String username, final String password) {
        return false;
    }

    // Metodo per stilizzare un JLabel
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        return label;
    }

    // Metodo per stilizzare un JTextField
    private void styleTextField(JTextField textField) {
        textField.setBackground(new Color(34, 40, 49));
        textField.setForeground(textColor);
        textField.setCaretColor(textColor);
        textField.setBorder(BorderFactory.createLineBorder(buttonColor, 2));
    }

    // Metodo per stilizzare un JButton
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(buttonColor);
        button.setForeground(buttonTextColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(buttonColor.darker(), 1));
        return button;
    }

    @Override
    public void addObserver(PanelChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(PanelChangeObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String panelName) {
        for (PanelChangeObserver observer : observers) {
            observer.onPanelChange(panelName);
        }
    }
}

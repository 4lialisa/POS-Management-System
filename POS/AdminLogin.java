import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminLogin extends JFrame {
    JPanel IDPanel, passwdPanel, LoginPanel, LoginBPanel;
    JTextField IDField;
    JPasswordField passwdField;
    JLabel IDLabel, passwdLabel;
    JButton LoginButton, ReturnButton;
    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font textFieldFont = new Font("Arial", Font.PLAIN, 16);

    static final String adminFile = ("admin.txt");

    public void adminLogin() {
        IDPanel = new JPanel();
        passwdPanel = new JPanel();
        IDField = new JTextField(10);
        passwdLabel = new JLabel("Password : ");
        passwdField = new JPasswordField(10);
        IDLabel = new JLabel("Admin ID : ");
        LoginPanel = new JPanel();
        LoginButton = new JButton("Log In");
        ReturnButton = new JButton("Return to Menu");

        IDLabel.setFont(labelFont);
        IDField.setFont(textFieldFont);
        passwdField.setFont(textFieldFont);
        passwdLabel.setFont(labelFont);
        LoginButton.setFont(textFieldFont);
        ReturnButton.setFont(textFieldFont);

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminLoginAction();
            }
        });

        ReturnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFrame();
                dispose();
                MainMenu mainMenu = new MainMenu();
                mainMenu.mainmenu();
            }
        });

        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    adminLoginAction();
                }
            }
        };

        IDField.addKeyListener(enterKeyListener);
        passwdField.addKeyListener(enterKeyListener);

        IDPanel.add(IDLabel);
        IDPanel.add(IDField);
        passwdPanel.add(passwdLabel);
        passwdPanel.add(passwdField);
        LoginPanel.add(LoginButton);
        LoginPanel.add(ReturnButton);

        setLayout(new GridLayout(3, 1));
        add(IDPanel);
        add(passwdPanel);
        add(LoginPanel);

        setTitle("Admin Login");
        setSize(500, 200);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clearFrame();
                dispose();
                MainMenu mainMenu = new MainMenu();
                mainMenu.mainmenu();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void adminLoginAction() {
        String adminID = IDField.getText();
        char[] passwdChars = passwdField.getPassword();
        String adminPasswd = new String(passwdChars);

        if (adminID.isEmpty() || adminPasswd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Admin ID and Password.");
        } else {
            // Add your logic to check the admin credentials
            if (checkAdminCredentials(adminID, adminPasswd)) {
                // Successful login, open the AdminMenu
                JOptionPane.showMessageDialog(this, "Admin Login Successful.");
                clearFrame();
                dispose();
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.adminMenu();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin ID or Password.");
            }
        }
    }

    private boolean checkAdminCredentials(String adminID, String adminPasswd) {
       
        String presetAdminID = "admin";
        String presetAdminPasswd = "admin123";

        return presetAdminID.equals(adminID) && presetAdminPasswd.equals(adminPasswd);
    }

    private void clearFrame() {
        getContentPane().removeAll();
        revalidate();
        repaint();
    }
}

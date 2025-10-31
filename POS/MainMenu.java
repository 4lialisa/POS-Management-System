import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    JButton cashierLoginButton, adminLoginButton, exitButton;
    JLabel subTitle;
    JPanel buttonPanel;
    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font textFieldFont = new Font("Arial", Font.PLAIN, 16);

    public void mainmenu() {
        cashierLoginButton = new JButton("Cashier Login");
        adminLoginButton = new JButton("Admin Login");
        exitButton = new JButton("Exit");

        buttonPanel = new JPanel();
        subTitle = new JLabel("Select an option:");
        buttonPanel.add(cashierLoginButton);
        buttonPanel.add(adminLoginButton);
        buttonPanel.add(exitButton);
        buttonPanel.setLayout(new FlowLayout());

        cashierLoginButton.setFont(textFieldFont);
        adminLoginButton.setFont(textFieldFont);
        exitButton.setFont(textFieldFont);
        subTitle.setFont(labelFont);
        subTitle.setHorizontalAlignment(SwingConstants.CENTER);

        cashierLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CashierLogin cashierLogin = new CashierLogin();
                clearFrame();
                dispose();
                cashierLogin.getID();
            }
        });

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLogin adminLogin = new AdminLogin();
                clearFrame();
                dispose();
                adminLogin.adminLogin();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);
        add(subTitle, BorderLayout.NORTH);
        setTitle("Menu");
        setSize(500, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void clearFrame() {
        getContentPane().removeAll();
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.mainmenu();
        });
    }
}

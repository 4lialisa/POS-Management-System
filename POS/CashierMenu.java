import javax.swing.*;
import java.awt.*;

public class CashierMenu extends JFrame {
    private JButton displayProductsButton, salesProcessingButton, logoutButton;

    public void cashierMenu() {
        displayProductsButton = new JButton("Display Available Products");
        salesProcessingButton = new JButton("Sales Processing");
        logoutButton = new JButton("Logout");

        configureButton(displayProductsButton);
        configureButton(salesProcessingButton);
        configureButton(logoutButton);

        setLayout(new GridLayout(3, 1, 10, 10));
        add(displayProductsButton);
        add(salesProcessingButton);
        add(logoutButton);

        displayProductsButton.addActionListener(e -> displayAvailableProducts());
        salesProcessingButton.addActionListener(e -> openSalesProcessingFrame());
        logoutButton.addActionListener(e -> logout());

        setTitle("Cashier Menu");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void configureButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false); 
    }

    private void displayAvailableProducts() {
        ProductDisplay productDisplay = new ProductDisplay();
        productDisplay.displayAllProducts();
    }

    private void openSalesProcessingFrame() {
        SwingUtilities.invokeLater(() -> {
            SalesProcessingFrame salesProcessingFrame = new SalesProcessingFrame();
            salesProcessingFrame.setVisible(true);
        });
    }

    private void logout() {
        dispose();
        MainMenu mainMenu = new MainMenu();
        mainMenu.mainmenu();
    }
}

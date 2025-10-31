import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminMenu extends JFrame {
    private JButton regCashierButton, displayCashierButton, editCashierButton, deleteCashierButton,
            productManagementButton, salesProcessingButton, transactionHistoryButton, logoutButton;
    private JLabel subTitle;
    private JPanel buttonPanel;
    private Font labelFont = new Font("Arial", Font.BOLD, 20);
    private Font buttonFont = new Font("Arial", Font.PLAIN, 16);

    private String[] columnNames = {"ID", "Name", "Role", "Password"};

    public void adminMenu() {
        regCashierButton = new JButton("Cashier Registration");
        displayCashierButton = new JButton("Display Cashier Information");
        editCashierButton = new JButton("Edit Cashier Information");
        deleteCashierButton = new JButton("Delete Cashier Information");
        productManagementButton = new JButton("Product Management");
        salesProcessingButton = new JButton("Sales Processing");
        transactionHistoryButton = new JButton("Transaction History");
        logoutButton = new JButton("Logout");

        buttonPanel = new JPanel();
        subTitle = new JLabel("Admin Menu");
        buttonPanel.setLayout(new GridLayout(4, 2, 10, 10)); 

        configureButton(regCashierButton);
        configureButton(displayCashierButton);
        configureButton(editCashierButton);
        configureButton(deleteCashierButton);
        configureButton(productManagementButton);
        configureButton(salesProcessingButton);
        configureButton(transactionHistoryButton);
        configureButton(logoutButton);

        subTitle.setFont(labelFont);
        subTitle.setHorizontalAlignment(SwingConstants.CENTER);

        buttonPanel.add(regCashierButton);
        buttonPanel.add(displayCashierButton);
        buttonPanel.add(editCashierButton);
        buttonPanel.add(deleteCashierButton);
        buttonPanel.add(productManagementButton);
        buttonPanel.add(salesProcessingButton);
        buttonPanel.add(transactionHistoryButton);
        buttonPanel.add(logoutButton);

        regCashierButton.addActionListener(e -> openCashierRegister());
        displayCashierButton.addActionListener(e -> displayAllCashierInformation());
        editCashierButton.addActionListener(e -> openEditCashier());
        deleteCashierButton.addActionListener(e -> openDeleteCashier());
        productManagementButton.addActionListener(e -> openProductManagement());
        salesProcessingButton.addActionListener(e -> openSalesProcessingFrame());
        transactionHistoryButton.addActionListener(e -> openTransactionHistory());
        logoutButton.addActionListener(e -> logout());

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);
        add(subTitle, BorderLayout.NORTH);
        setTitle("Admin Menu");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void configureButton(JButton button) {
        button.setFont(buttonFont);
        button.setFocusPainted(false); 
    }

    private void openCashierRegister() {
        clearFrame();
        dispose();
        CashierRegister cashierRegister = new CashierRegister();
        cashierRegister.newID();
    }

    private void openEditCashier() {
        EditCashier editCashier = new EditCashier();
        editCashier.editCashier();
    }

    private void openDeleteCashier() {
        DeleteCashier deleteCashier = new DeleteCashier();
        deleteCashier.deleteCashier();
    }

    private void openProductManagement() {
        new ProductManagement();
    }

    private void logout() {
        clearFrame();
        dispose();
        MainMenu mainMenu = new MainMenu();
        mainMenu.mainmenu();
    }

    private void clearFrame() {
        getContentPane().removeAll();
        revalidate();
        repaint();
    }

    private List<String[]> readCashierInformation() {
        List<String[]> cashierData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("CashierInformation.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                cashierData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();  
        }

        return cashierData;
    }

    private void displayAllCashierInformation() {
        
        List<String[]> cashierData = readCashierInformation();
       
        JTable table = new JTable(new DefaultTableModel(cashierData.toArray(new Object[][]{}), columnNames));
        JScrollPane scrollPane = new JScrollPane(table);

        JOptionPane.showMessageDialog(this, scrollPane, "Cashier Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openSalesProcessingFrame() {
        SwingUtilities.invokeLater(() -> {
            SalesProcessingFrame salesProcessingFrame = new SalesProcessingFrame();
            salesProcessingFrame.setVisible(true);
        });
    }
    private void openTransactionHistory() {
        SwingUtilities.invokeLater(Transaction::new);
    }
}

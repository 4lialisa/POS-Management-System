import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditCashier extends JFrame {
    private List<String[]> cashierData = new ArrayList<>();
    private String[] columnNames = {"ID", "Name", "Role", "Password"};

    public void editCashier() {
        displayAllCashierInformation();

        String inputID = JOptionPane.showInputDialog("Enter Cashier ID to edit:");
        if (inputID != null && !inputID.isEmpty()) {
            if (isCashierIDExist(inputID)) {
                String newPassword = JOptionPane.showInputDialog("Enter new password for Cashier ID " + inputID + ":");
                if (newPassword != null && !newPassword.isEmpty()) {
                    editCashierPassword(inputID, newPassword);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid password. Edit operation canceled.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cashier ID doesn't exist. Please try another.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input. Edit operation canceled.");
        }

        dispose();
    }

    private void displayAllCashierInformation() {

        cashierData.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("CashierInformation.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                cashierData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }

        JTable table = new JTable(new DefaultTableModel(cashierData.toArray(new Object[][]{}), columnNames));
        JScrollPane scrollPane = new JScrollPane(table);

        JOptionPane.showMessageDialog(this, scrollPane, "Cashier Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean isCashierIDExist(String cashierID) {
        for (String[] data : cashierData) {
            if (data.length > 0 && data[0].equals(cashierID)) {
                return true;
            }
        }
        return false;
    }

    private void editCashierPassword(String cashierID, String newPassword) {
        try {
            List<String> updatedData = new ArrayList<>();
            for (String[] data : cashierData) {
                if (data.length > 0 && data[0].equals(cashierID)) {
                    data[3] = newPassword; 
                }
                updatedData.add(String.join(",", data));
            }

            try (FileWriter writer = new FileWriter("CashierInformation.txt")) {
                for (String line : updatedData) {
                    writer.write(line + System.lineSeparator());
                }
            }

            JOptionPane.showMessageDialog(this, "Password for Cashier ID " + cashierID + " updated successfully.");
        } catch (IOException e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "An error occurred during password update.");
        }
    }
}

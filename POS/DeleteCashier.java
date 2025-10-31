import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteCashier extends JFrame {
    private List<String[]> cashierData = new ArrayList<>();
    private String[] columnNames = {"ID", "Name", "Role", "Password"};

    public void deleteCashier() {
        displayAllCashierInformation();

        String inputID = JOptionPane.showInputDialog("Enter Cashier ID to delete:");
        if (inputID != null && !inputID.isEmpty()) {
            int cashierID = Integer.parseInt(inputID);
            if (isCashierIDExist(cashierID)) {
                int confirmResult = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete Cashier ID " + cashierID + "?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                    deleteCashier(cashierID);
                } else {
                    JOptionPane.showMessageDialog(this, "Delete operation canceled.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cashier ID doesn't exist. Please try another.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input. Delete operation canceled.");
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

    private boolean isCashierIDExist(int cashierID) {
        for (String[] data : cashierData) {
            int id = Integer.parseInt(data[0]);
            if (id == cashierID) {
                return true;
            }
        }
        return false;
    }

    private void deleteCashier(int cashierID) {
        try {
            List<String> updatedData = new ArrayList<>();
            for (String[] data : cashierData) {
                int id = Integer.parseInt(data[0]);
                if (id != cashierID) {
                    updatedData.add(String.join(",", data));
                }
            }

            try (FileWriter writer = new FileWriter("CashierInformation.txt")) {
                for (String line : updatedData) {
                    writer.write(line + System.lineSeparator());
                }
            }

            JOptionPane.showMessageDialog(this, "Cashier ID " + cashierID + " deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "An error occurred during deletion.");
        }
    }
}

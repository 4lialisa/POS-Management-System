import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteProduct extends JFrame {
    private List<String[]> productData = new ArrayList<>();
    private String[] columnNames = {"ProductID", "ProductName", "Price"};

    public DeleteProduct() {
        displayAllProducts();

        String inputID = JOptionPane.showInputDialog("Enter Product ID to delete:");
        if (inputID != null && !inputID.isEmpty()) {
            int productID = Integer.parseInt(inputID);
            if (isProductIDExist(productID)) {
                int confirmResult = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete Product ID " + productID + "?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                    deleteProduct(productID);
                } else {
                    JOptionPane.showMessageDialog(this, "Delete operation canceled.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Product ID doesn't exist. Please try another.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input. Delete operation canceled.");
        }

        dispose();
    }

    private void displayAllProducts() {
        productData.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("ProductList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                productData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }

        JTable table = new JTable(new DefaultTableModel(productData.toArray(new Object[][]{}), columnNames));
        JScrollPane scrollPane = new JScrollPane(table);

        JOptionPane.showMessageDialog(this, scrollPane, "Product Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean isProductIDExist(int productID) {
        for (String[] data : productData) {
            int id = Integer.parseInt(data[0]);
            if (id == productID) {
                return true;
            }
        }
        return false;
    }

    private void deleteProduct(int productID) {
        try {
            List<String> updatedData = new ArrayList<>();
            for (String[] data : productData) {
                int id = Integer.parseInt(data[0]);
                if (id != productID) {
                    updatedData.add(String.join(",", data));
                }
            }

            try (FileWriter writer = new FileWriter("ProductList.txt")) {
                for (String line : updatedData) {
                    writer.write(line + System.lineSeparator());
                }
            }

            JOptionPane.showMessageDialog(this, "Product ID " + productID + " deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred during deletion.");
        }
    }
}

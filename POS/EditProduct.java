import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditProduct extends JFrame {
    private List<String[]> productData = new ArrayList<>();
    private String[] columnNames = {"ProductID", "ProductName", "Price"};

    public EditProduct() {
        displayAllProducts();

        String inputID = JOptionPane.showInputDialog("Enter Product ID to edit:");
        if (inputID != null && !inputID.isEmpty()) {
            if (isProductIDExist(inputID)) {
                String newPriceStr = JOptionPane.showInputDialog("Enter new price for Product ID " + inputID + ":");
                if (newPriceStr != null && !newPriceStr.isEmpty()) {
                    editProductPrice(inputID, newPriceStr);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid price. Edit operation canceled.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Product ID doesn't exist. Please try another.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input. Edit operation canceled.");
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

    private boolean isProductIDExist(String productID) {
        for (String[] data : productData) {
            if (data.length > 0 && data[0].equals(productID)) {
                return true;
            }
        }
        return false;
    }

    private void editProductPrice(String productID, String newPriceStr) {
        try {
            List<String> updatedData = new ArrayList<>();
            for (String[] data : productData) {
                if (data.length > 0 && data[0].equals(productID)) {
                    data[2] = newPriceStr; 
                }
                updatedData.add(String.join(",", data));
            }

            try (FileWriter writer = new FileWriter("ProductList.txt")) {
                for (String line : updatedData) {
                    writer.write(line + System.lineSeparator());
                }
            }

            JOptionPane.showMessageDialog(this, "Price for Product ID " + productID + " updated successfully.");
        } catch (IOException e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "An error occurred during price update.");
        }
    }

}

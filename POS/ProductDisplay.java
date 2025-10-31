import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductDisplay extends JFrame {
    private List<String[]> productData = new ArrayList<>();
    private String[] columnNames = {"ProductID", "ProductName", "Price"};
    private JButton returnButton;

    void displayAllProducts() {
        // Clear existing data
        productData.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("ProductList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                productData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
            JOptionPane.showMessageDialog(this, "An error occurred while reading product data.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (productData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No products to display.", "Empty List", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTable table = new JTable(new DefaultTableModel(productData.toArray(new Object[][]{}), columnNames));
        JScrollPane scrollPane = new JScrollPane(table);

        returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", Font.PLAIN, 16));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(returnButton, BorderLayout.SOUTH);
        setContentPane(panel);

        setTitle("Product Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

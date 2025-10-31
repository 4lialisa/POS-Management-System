import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AddProduct extends JFrame {
    private JTextField idField, nameField, priceField;
    private JLabel idLabel, nameLabel, priceLabel;
    private JButton addButton, returnButton;
    private Font labelFont = new Font("Arial", Font.BOLD, 16);
    private Font textFieldFont = new Font("Arial", Font.PLAIN, 16);

    public AddProduct() {
        idField = new JTextField(10);
        nameField = new JTextField(20);
        priceField = new JTextField(10);

        idLabel = new JLabel("Product ID: ");
        nameLabel = new JLabel("Product Name: ");
        priceLabel = new JLabel("Product Price: ");

        addButton = new JButton("Add Product");
        returnButton = new JButton("Return to Menu");

        idLabel.setFont(labelFont);
        nameLabel.setFont(labelFont);
        priceLabel.setFont(labelFont);
        idField.setFont(textFieldFont);
        nameField.setFont(textFieldFont);
        priceField.setFont(textFieldFont);
        addButton.setFont(textFieldFont);
        returnButton.setFont(textFieldFont);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
            }
        });

        setLayout(new GridLayout(4, 2, 10, 10));
        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(priceLabel);
        add(priceField);
        add(addButton);
        add(returnButton);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Add Product");
        setSize(500, 300);
        setVisible(true);
    }

    private void addProduct() {
        String idStr = idField.getText();
        String name = nameField.getText();
        String priceStr = priceField.getText();

        if (idStr.isEmpty() || name.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
        } else {
            try {
                int id = Integer.parseInt(idStr);
                double price = Double.parseDouble(priceStr);

                if (checkExistingID(id)) {
                    JOptionPane.showMessageDialog(this, "Product ID already exists. Please choose another ID.");
                } else {
                    writeToFile(id, name, price);
                    displayProductInfo(id, name, price);
                    JOptionPane.showMessageDialog(this, "Product added successfully.");
                    dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.");
            }
        }
    }

    private boolean checkExistingID(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ProductList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int existingID = Integer.parseInt(parts[0]);
                if (existingID == id) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void writeToFile(int id, String name, double price) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ProductList.txt", true))) {
            writer.write(id + "," + name + "," + price + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while writing to the file.");
        }
    }

    private void displayProductInfo(int id, String name, double price) {
        JFrame frame = new JFrame("Product Information");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel idLabel = new JLabel("Product ID:");
        JLabel nameLabel = new JLabel("Product Name:");
        JLabel priceLabel = new JLabel("Product Price:");

        JTextField idTextField = new JTextField(Integer.toString(id));
        JTextField nameTextField = new JTextField(name);
        JTextField priceTextField = new JTextField(Double.toString(price));

        idTextField.setEditable(false);
        nameTextField.setEditable(false);
        priceTextField.setEditable(false);

        panel.add(idLabel);
        panel.add(idTextField);
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(priceLabel);
        panel.add(priceTextField);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the AddProduct frame
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AddProduct();
            }
        });
    }
}

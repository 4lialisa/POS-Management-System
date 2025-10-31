import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductManagement extends JFrame {
    JButton displayProductButton, addProductButton, editProductButton, deleteProductButton, backButton;
    JPanel buttonPanel;
    Font labelFont = new Font("Arial", Font.BOLD, 16);
    Font textFieldFont = new Font("Arial", Font.PLAIN, 16);

    public ProductManagement() {
        displayProductButton = new JButton("Display Products");
        addProductButton = new JButton("Add Product");
        editProductButton = new JButton("Edit Product");
        deleteProductButton = new JButton("Delete Product");
        backButton = new JButton("Back");

        buttonPanel = new JPanel();
        buttonPanel.add(displayProductButton);
        buttonPanel.add(addProductButton);
        buttonPanel.add(editProductButton);
        buttonPanel.add(deleteProductButton);
        buttonPanel.add(backButton);

        buttonPanel.setLayout(new GridLayout(3, 2));
        displayProductButton.setFont(textFieldFont);
        addProductButton.setFont(textFieldFont);
        editProductButton.setFont(textFieldFont);
        deleteProductButton.setFont(textFieldFont);
        backButton.setFont(textFieldFont);

        displayProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductDisplay productDisplay = new ProductDisplay();
                productDisplay.displayAllProducts();
            }
        });

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProduct();
            }
        });

        editProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditProduct();
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteProduct();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);
        setTitle("Product Management");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void productManagementMenu() {
        System.out.println("Product Management Menu");
    }
}

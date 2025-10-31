import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesProcessingFrame extends JFrame {
    private List<Product> cart;
    private JTextField productIDField, quantityField, amountPaidField;
    private JTextArea cartTextArea;
    private JButton addToCartButton, proceedToPaymentButton, backButton;
    private double totalAmount;
    private String selectedPaymentMethod; 
    private JRadioButton cashRadioButton;
    private JRadioButton cardRadioButton;
    private JRadioButton ewalletRadioButton;

    public SalesProcessingFrame() {
        cart = new ArrayList<>();
        totalAmount = 0;

        productIDField = new JTextField(10);
        quantityField = new JTextField(5);
        amountPaidField = new JTextField(10);
        cartTextArea = new JTextArea(10, 30);
        cartTextArea.setEditable(false);

        addToCartButton = new JButton("Add to Cart");
        proceedToPaymentButton = new JButton("Proceed to Payment");
        backButton = new JButton("Back");

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });

        proceedToPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedToPayment();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

        cashRadioButton = new JRadioButton("Cash");
        cardRadioButton = new JRadioButton("Card");
        ewalletRadioButton = new JRadioButton("E-Wallet");

        ButtonGroup paymentMethodGroup = new ButtonGroup();
        paymentMethodGroup.add(cashRadioButton);
        paymentMethodGroup.add(cardRadioButton);
        paymentMethodGroup.add(ewalletRadioButton);

        cashRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPaymentMethod = "Cash";
            }
        });

        cardRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPaymentMethod = "Card";
            }
        });

        ewalletRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPaymentMethod = "E-Wallet";
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("Product ID:"));
        panel.add(productIDField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(addToCartButton);
        panel.add(proceedToPaymentButton);
        panel.add(new JPanel());
        panel.add(backButton);

        JScrollPane scrollPane = new JScrollPane(cartTextArea);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setTitle("Sales Processing");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addToCart() {
        try {
            int productID = Integer.parseInt(productIDField.getText());

            if (isProductIDExist(productID)) {
                int quantity = Integer.parseInt(quantityField.getText());
                Product product = getProductDetails(productID);

                double total = product.getPrice() * quantity;
                totalAmount += total;

                product.setQuantity(quantity);
                cart.add(product);

                updateCartTextArea();

                productIDField.setText("");
                quantityField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Product ID. Please enter a valid Product ID.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.");
        }
    }

    private boolean isProductIDExist(int productID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ProductList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int existingProductID = Integer.parseInt(data[0]);
                if (existingProductID == productID) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Product getProductDetails(int productID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ProductList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int existingProductID = Integer.parseInt(data[0]);
                if (existingProductID == productID) {
                    String productName = data[1];
                    double price = Double.parseDouble(data[2]);
                    return new Product(productID, productName, price, 0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateCartTextArea() {
        cartTextArea.setText("");
        for (Product product : cart) {
            cartTextArea.append(product.toString() + "\n");
        }
        cartTextArea.append("--------------------\n");
        cartTextArea.append("Total Amount: " + formatCurrency(totalAmount) + "\n");
    }

    private void proceedToPayment() {
        JFrame paymentFrame = new JFrame("Payment");
        paymentFrame.setLayout(new GridLayout(5, 2));

        JTextField totalAmountField = new JTextField("Total Amount: " + formatCurrency(totalAmount));
        totalAmountField.setEditable(false);

        JTextField amountPaidField = new JTextField("Amount Paid:");
        JTextField changeField = new JTextField("Change:");
        changeField.setEditable(false);

        JButton confirmPaymentButton = new JButton("Confirm Payment");

        confirmPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String amountPaidText = amountPaidField.getText();
            
                    double amountPaid = Double.parseDouble(amountPaidText.substring(amountPaidText.indexOf(":") + 1).trim());

                    if (amountPaid >= totalAmount) {
                   
                        selectedPaymentMethod = getSelectedPaymentMethod();
                        double change = amountPaid - totalAmount;
                        changeField.setText("Change: " + formatCurrency(change));
                        
                        saveTransactionToTransactionFile();

                      
                        generateAndDisplayReceipt(change);
                        clearFields();
                        paymentFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(paymentFrame, "Insufficient payment. Please enter a valid amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(paymentFrame, "Invalid input for amount paid. Please enter a valid number.");
                }
            }
        });

        paymentFrame.add(totalAmountField);
	paymentFrame.add(cashRadioButton);
	paymentFrame.add(cardRadioButton);
	paymentFrame.add(ewalletRadioButton);
	paymentFrame.add(amountPaidField);
	paymentFrame.add(changeField);
	paymentFrame.add(confirmPaymentButton);

        paymentFrame.setSize(400, 200);
        paymentFrame.setLocationRelativeTo(null);
        paymentFrame.setVisible(true);
    }

    private String getSelectedPaymentMethod() {
        if (cashRadioButton.isSelected()) {
            return "Cash";
        } else if (cardRadioButton.isSelected()) {
            return "Card";
        } else if (ewalletRadioButton.isSelected()) {
            return "E-Wallet";
        } else {
          
            return "Cash";
        }
    }

    private void saveTransactionToTransactionFile() {
        try (FileWriter writer = new FileWriter("transaction.txt", true)) {
            for (Product product : cart) {
                writer.write(product.toString() + "\n");
            }
            writer.write("--------------------\n");
            writer.write("Total Amount: " + formatCurrency(totalAmount) + "\n");
            writer.write("Payment Method: " + selectedPaymentMethod + "\n");
            writer.write("Date: " + getCurrentDate() + "\n");
            writer.write("====================\n");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while saving the transaction.");
        }
    }

    private void generateAndDisplayReceipt(double change) {
        StringBuilder receiptContent = new StringBuilder();
        receiptContent.append("Receipt\n");
        receiptContent.append("====================\n");
        for (Product product : cart) {
            receiptContent.append(product.toString()).append("\n");
        }
        receiptContent.append("--------------------\n");
        receiptContent.append("Total Amount: ").append(formatCurrency(totalAmount)).append("\n");
        receiptContent.append("Payment Method: ").append(selectedPaymentMethod).append("\n");
        receiptContent.append("Change: ").append(formatCurrency(change)).append("\n");
        receiptContent.append("Date: ").append(getCurrentDate()).append("\n");

        JOptionPane.showMessageDialog(this, receiptContent.toString(), "Receipt", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() {
        cart.clear();
        totalAmount = 0;
        updateCartTextArea();
        amountPaidField.setText("");
    }

    private String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "RM " + df.format(amount);
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}

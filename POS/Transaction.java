import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Transaction extends JFrame {
    private JTextArea transactionTextArea;
    private JButton returnButton;

    public Transaction() {
        transactionTextArea = new JTextArea(20, 40);
        transactionTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(transactionTextArea);

        displayTransactionHistory();

        returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(returnButton);

        setLayout(new BorderLayout());

        add(scrollPane, BorderLayout.CENTER);
        
        add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Transaction History");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void displayTransactionHistory() {
        String filePath = "transaction.txt";
        String absolutePath = new File(filePath).getAbsolutePath();

        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("--------------------")) {
                    transactionTextArea.append("\n");
                } else {
                    transactionTextArea.append(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while reading the transaction history.");
        }
    }
}

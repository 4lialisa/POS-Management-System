import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class CashierRegister extends JFrame 
{
    private JPanel IDPanel, IDBPanel, RegPanel, DisplayPanel, buttonPanel;
    private JTextField IDField, nameField, roleField, passwdField;
    private JLabel IDLabel, nameLabel, roleLabel, passwdLabel;
    private JButton IDButton, RegButton, ReturnButton;
    private int ID;
    private static final Font labelFont = new Font("Arial", Font.BOLD, 16);
    private static final Font textFieldFont = new Font("Arial", Font.PLAIN, 16);
    private static final String CRFile = "CashierInformation.txt";

    public void newID() 
    {
        idPanel();
        buttonPanel();
        setLayout(new GridLayout(2,1));
        add(IDPanel);
        add(IDBPanel);
        setTitle("Registration");
        setSize(500,200);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener
        (
            new WindowAdapter() 
            {
                @Override
                public void windowClosing(WindowEvent e) 
                {
                    returnToMenu();
                }
            }
        );
    }

    private void idPanel() 
    {
        IDPanel = new JPanel();
        IDField = new JTextField(10);
        IDLabel = new JLabel("New ID: ");
        IDLabel.setFont(labelFont);
        IDField.setFont(textFieldFont);
        IDBPanel = new JPanel();
        IDPanel.add(IDLabel);
        IDPanel.add(IDField);
        noComa(IDField);
    }

    private void buttonPanel()
    {
        IDButton = new JButton("Register");
        IDButton.setFont(textFieldFont);
        ReturnButton = new JButton("Return to Menu");
        ReturnButton.setFont(textFieldFont);
        IDButton.addActionListener(e -> regID());
        ReturnButton.addActionListener(e -> returnToMenu());
        KeyListener enterKeyListener = new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e) 
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    regID();
                }
            }
        };
        IDField.addKeyListener(enterKeyListener);
        IDBPanel.add(IDButton);
        IDBPanel.add(ReturnButton);
    }

    private void noComa(JTextField textField) 
    {
        textField.addKeyListener
        (
            new KeyAdapter() 
            {
                @Override
                public void keyTyped(KeyEvent e) 
                {
                    if (e.getKeyChar() == ',') 
                    {
                        e.consume();
                        JOptionPane.showMessageDialog(textField, "Please do not insert comma.");
                    }
                }
            }
        );
    }

    private void regID() 
    {
        String IDStr = IDField.getText();
        if (IDStr.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Please insert an ID.");
        }
        else 
        {
            try 
            {
                ID = Integer.parseInt(IDField.getText());
                if (checkID(ID)) 
                {
                    reg(ID);
                } 
                else 
                {
                    JOptionPane.showMessageDialog(this, "ID already exists, please insert a new ID.");
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(this, "Please enter an integer.");
            }
        }
    }

    private boolean checkID(int ID) 
    {
        String line;
        String[] part;
        int existID;
        Scanner fileScanner = null;
        try 
        {
            fileScanner = new Scanner(new File(CRFile));
            while (fileScanner.hasNextLine()) 
            {
                line = fileScanner.nextLine();
                part = line.split(",");
                existID = Integer.parseInt(part[0]);
                if(existID == ID) 
                {
                    return false;
                }
            }
        } 
        catch(FileNotFoundException e) 
        {
            createfile();
        } 
        finally 
        {
            if (fileScanner != null) 
            {
                fileScanner.close();
            }
        }
        return true;
    }

    private void createfile() 
    {
        File file = new File(CRFile);
        try 
        {
            if (file.createNewFile()) 
            {
                System.out.print("File created : " + file.getName());
            } 
            else 
            {
                System.out.println("File already exists.");
            }
        } 
        catch (IOException e) 
        {
            System.out.println("An error occurred while creating a file.");
            e.printStackTrace();
        }
    }

    private void reg(int ID)
    {
        clearFrame();
        regPanel();
        regButtonPanel();
        setLayout(new BorderLayout());
        add(RegPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setTitle("Registration");
        setSize(500,200);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void clearFrame() 
    {
        getContentPane().removeAll();
        revalidate();
        repaint();
    }

    private void returnToMenu() 
    {
        clearFrame();
        dispose();
        AdminMenu menu = new AdminMenu();
        menu.adminMenu();
    }

    private void regPanel()
    {
        nameField = new JTextField(20);
        passwdField = new JTextField(20);
        nameLabel = new JLabel("Name : ");
        passwdLabel = new JLabel("Password : ");
        RegPanel = new JPanel();
        RegPanel.setLayout(new GridLayout(2, 2, 10, 10));
        nameLabel.setFont(labelFont);
        passwdLabel.setFont(labelFont);
        nameField.setFont(textFieldFont);
        passwdField.setFont(textFieldFont);
        RegPanel.add(nameLabel);
        RegPanel.add(nameField);
        RegPanel.add(passwdLabel);
        RegPanel.add(passwdField);
        noComa(nameField);
        noComa(passwdField);
    }

    private void regButtonPanel() 
    {
        buttonPanel = new JPanel();
        RegButton = new JButton("Register");
        ReturnButton = new JButton("Return");
        RegButton.setFont(textFieldFont);
        ReturnButton.setFont(textFieldFont);
        ReturnButton.addActionListener(e -> returnToMenu());
        RegButton.addActionListener
        (
            new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    if (nameField.getText().isEmpty()) 
                    {
                        JOptionPane.showMessageDialog(CashierRegister.this, "You must enter your name.");
                    } 
                    else if (passwdField.getText().isEmpty()) 
                    {
                        JOptionPane.showMessageDialog(CashierRegister.this, "You must enter a password.");
                    } 
                    else
                    {
                        inputfile(ID, nameField.getText(), passwdField.getText());
                        display();
                    }
                }
            }
        );

        KeyListener enterKeyListener = new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e) 
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    if (nameField.getText().isEmpty()) 
                    {
                        JOptionPane.showMessageDialog(CashierRegister.this, "You must enter your name.");
                    } 
                    else if (passwdField.getText().isEmpty()) 
                    {
                        JOptionPane.showMessageDialog(CashierRegister.this, "You must enter a password.");
                    } 
                    else 
                    {
                        inputfile(ID, nameField.getText(), passwdField.getText());
                        display();
                    }
                }
            }
        };

        nameField.addKeyListener(enterKeyListener);
        passwdField.addKeyListener(enterKeyListener);
        buttonPanel.add(RegButton);
        buttonPanel.add(ReturnButton);
    }

    private void inputfile(int ID, String name, String passwd) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CRFile, true)))
        {
            writer.write(ID + "," + name + ",Cashier," + passwd + "\n");
        } 
        catch(IOException e) 
        {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    private void display() 
    {
        clearFrame();
        dispose();
        ButtonPanel();
        displayPanel();
        setLayout(new BorderLayout());
        add(DisplayPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        DisplayPanel.setLayout(new GridLayout(4,2));
        setTitle("Cashier Registered");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void displayPanel() 
    {
        IDField = new JTextField((readfile(ID,0)));
        nameField = new JTextField((readfile(ID,1)));
        roleField = new JTextField((readfile(ID,2)));
        passwdField = new JTextField((readfile(ID,3)));
        DisplayPanel = new JPanel();
        IDLabel = new JLabel("ID : ");
        nameLabel = new JLabel("Name : ");
        roleLabel = new JLabel("Role : ");
        passwdLabel = new JLabel("Password : ");        
        IDLabel.setFont(labelFont); 
        nameLabel.setFont(labelFont);
        roleLabel.setFont(labelFont);
        passwdLabel.setFont(labelFont);
        IDField.setFont(textFieldFont);
        nameField.setFont(textFieldFont);
        roleField.setFont(textFieldFont);
        passwdField.setFont(textFieldFont);
        DisplayPanel.add(IDLabel);
        DisplayPanel.add(IDField);
        DisplayPanel.add(nameLabel);
        DisplayPanel.add(nameField);
        DisplayPanel.add(roleLabel);
        DisplayPanel.add(roleField);
        DisplayPanel.add(passwdLabel);
        DisplayPanel.add(passwdField);
        IDField.setEditable(false);
        nameField.setEditable(false);
        roleField.setEditable(false);
        passwdField.setEditable(false);
        DisplayPanel.setLayout(new GridLayout(4, 2, 10, 10));
    }

    private void ButtonPanel() 
    {
        buttonPanel = new JPanel();
        ReturnButton = new JButton("Return");
        ReturnButton.setFont(textFieldFont);
        ReturnButton.addActionListener(e -> returnToMenu());
        buttonPanel.add(ReturnButton);
    }

    private String readfile(int ID, int display) 
    {
        String line;
        String[] part;
        String IDStr = String.valueOf(ID) + ",";
        try (BufferedReader reader = new BufferedReader(new FileReader(CRFile))) 
        {
            while ((line = reader.readLine()) != null) 
            {
                part = line.split(",");
                if (line.startsWith(IDStr)) 
                {
                    return part[display];
                }
            }
        } 
        catch(FileNotFoundException e) 
        {
            System.out.println("The CashierRegister file has gone.");
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return null;
    }

}

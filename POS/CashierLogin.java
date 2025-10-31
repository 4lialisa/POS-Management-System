import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class CashierLogin extends JFrame 
{
    private JPanel IDPanel, IDBPanel, passwdPanel;
    private JTextField IDField;
    private JPasswordField loginPasswordField;
    private JLabel IDLabel, passwdLabel;
    private JButton IDButton, ReturnButton;
    private Font labelFont = new Font("Arial", Font.BOLD, 16);
    private Font textFieldFont = new Font("Arial", Font.PLAIN, 16);
    private int ID;
    private static final String CRFile = "CashierInformation.txt";

    public void getID()
    {
        idPanel();
        buttonPanel();
        passwdPanelMethod();
        setLayout(new GridLayout(3, 1));
        add(IDPanel);
        add(passwdPanel);
        add(IDBPanel);
        setTitle("Login");
        setSize(500, 200);
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
        IDLabel = new JLabel("Insert ID : ");
        IDLabel.setFont(labelFont);
        IDField.setFont(textFieldFont);
        noComa(IDField);
    }

    private void buttonPanel() 
    {
        IDBPanel = new JPanel();
        IDButton = new JButton("Log In");
        ReturnButton = new JButton("Return to Menu");
        IDButton.setFont(textFieldFont);
        ReturnButton.setFont(textFieldFont);
        IDButton.addActionListener(e -> loginID());
        ReturnButton.addActionListener(e -> returnToMenu());
        IDBPanel.add(IDButton);
        IDBPanel.add(ReturnButton);
    }

    private void passwdPanelMethod() 
    {
        passwdPanel = new JPanel();
        passwdLabel = new JLabel("Password : ");
        loginPasswordField = new JPasswordField(10);
        loginPasswordField.setFont(textFieldFont);
        passwdLabel.setFont(labelFont);
        IDPanel.add(IDLabel);
        IDPanel.add(IDField);
        passwdPanel.add(passwdLabel);
        passwdPanel.add(loginPasswordField);
        KeyListener enterKeyListener = new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e) 
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    loginID();
                }
            }
        };
        IDField.addKeyListener(enterKeyListener);
        loginPasswordField.addKeyListener(enterKeyListener);
        noComa(loginPasswordField);
    }

    private void returnToMenu() 
    {
        clearFrame();
        dispose();
        MainMenu mainMenu = new MainMenu();
        mainMenu.mainmenu();
    }

    private void clearFrame() 
    {
        getContentPane().removeAll();
        revalidate();
        repaint();
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

    private void loginID() 
    {
        String IDStr = IDField.getText();
        char[] passwordChars = loginPasswordField.getPassword();
        String passwdStr = new String(passwordChars);
        if (IDStr.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Please insert an ID.");
        } 
        else 
        {
            ID = Integer.parseInt(IDField.getText());
            if (checkID(ID)) 
            {
                JOptionPane.showMessageDialog(this, "Unregistered ID");
            }
            else 
            {
                if (checkpasswd(IDStr, passwdStr)) 
                {
                    JOptionPane.showMessageDialog(this, "Password Incorrect.");
                } 
                else 
                {
                    login(ID);
                }
            }
        }
    }

    private boolean checkID(int ID) 
    {
        String line;
        String[] part;
        int existID;
        try (Scanner fileScanner = new Scanner(new File(CRFile))) 
        {
            while ((fileScanner.hasNextLine())) 
            {
                line = fileScanner.nextLine();
                part = line.split(",");
                existID = Integer.parseInt(part[0]);
                if (existID == ID) 
                {
                    return false;
                }
            }
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("The CashierInformation file has gone.");
        }
        return true;
    }

    private boolean checkpasswd(String ID, String passwdStr) 
    {
        String line;
        String[] part;
        String IDStr = ID + ",";
        try (BufferedReader reader = new BufferedReader(new FileReader(CRFile))) 
        {
            while ((line = reader.readLine()) != null) 
            {
                part = line.split(",");
                if (line.startsWith(IDStr)) 
                {
                    if (passwdStr.equals(part[3])) 
                    {
                        return false;
                    } 
                    else 
                    {
                        return true;
                    }
                }
            }
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("The CashierInformation file has gone.");
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return true;
    }

    private void login(int ID) 
    {
        clearFrame();
        CashierMenu cashierMenu = new CashierMenu();
        cashierMenu.cashierMenu();
        dispose();
    }
}

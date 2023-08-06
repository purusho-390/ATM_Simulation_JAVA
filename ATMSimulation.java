import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMSimulation extends JFrame {
    private JLabel accountLabel;
    private JTextField accountField;
    private JLabel pinLabel;
    private JPasswordField pinField;
    private JButton loginButton;

    // Dummy data for the user's account and balance
    static String ACCOUNT_NUMBER = "123456";
    static String PIN = "1234";
    static double balance = 1000.0;

    public ATMSimulation() {
        setTitle("ATM Simulation");
        setSize(800, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        accountLabel = new JLabel("Account Number:");
        accountField = new JTextField(15);

        pinLabel = new JLabel("PIN:");
        pinField = new JPasswordField(15);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login button click
                login();
            }
        });

        add(accountLabel);
        add(accountField);
        add(pinLabel);
        add(pinField);
        add(loginButton);

        setVisible(true);
    }

    private void login() {
    
        String accountNumber = accountField.getText();
        String pin = new String(pinField.getPassword());

        if (ATM.checkLogin(accountNumber, pin)) {
            // Open the main ATM window after successful login
            openMainMenu();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid account number or PIN. Please try again.");
        }
    }

    private void openMainMenu() {
        // Display the main menu and handle user actions
        String[] options = { "Check Balance", "Withdraw", "Deposit", "Logout" };
        int choice = JOptionPane.showOptionDialog(this, "Please select an option:", "Main Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                double balance = ATM.checkBalance();
                JOptionPane.showMessageDialog(this, "Your balance: $" + balance);
                openMainMenu(); // Go back to the main menu
                break;
            case 1:
                // Handle withdrawal
                String withdrawalAmountStr = JOptionPane.showInputDialog(this, "Enter the amount to withdraw:");
                try {
                    double withdrawalAmount = Double.parseDouble(withdrawalAmountStr);
                    if (withdrawalAmount <= 0) {
                        JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a positive value.");
                    } else if (withdrawalAmount > ATM.checkBalance()) {
                        JOptionPane.showMessageDialog(this, "Insufficient balance.");
                    } else {
                        ATM.withdraw(withdrawalAmount);
                        JOptionPane.showMessageDialog(this, "Withdrawal successful.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
                }
                openMainMenu(); // Go back to the main menu
                break;
            case 2:
                // Handle deposit
                String depositAmountStr = JOptionPane.showInputDialog(this, "Enter the amount to deposit:");
                try {
                    double depositAmount = Double.parseDouble(depositAmountStr);
                    if (depositAmount <= 0) {
                        JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a positive value.");
                    } else {
                        ATM.deposit(depositAmount);
                        JOptionPane.showMessageDialog(this, "Deposit successful.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
                }
                openMainMenu(); // Go back to the main menu
                break;
            case 3:
                // Logout
                JOptionPane.showMessageDialog(this, "Thank you for using our ATM. Goodbye!");
                break;
            default:
                // Invalid option
                JOptionPane.showMessageDialog(this, "Invalid option selected.");
                openMainMenu(); // Go back to the main menu
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ATMSimulation();
            }
        });
    }
}

// Separate class for the ATM logic
class ATM {
    public static boolean checkLogin(String accountNumber, String pin) {
        return ATMSimulation.ACCOUNT_NUMBER.equals(accountNumber) && ATMSimulation.PIN.equals(pin);
    }

    public static double checkBalance() {
        return ATMSimulation.balance;
    }

    public static void withdraw(double amount) {
        ATMSimulation.balance -= amount;
    }

    public static void deposit(double amount) {
        ATMSimulation.balance += amount;
    }
}

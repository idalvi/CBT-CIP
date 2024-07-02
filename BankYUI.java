package com.banky.ui;

import com.banky.model.Account;
import com.banky.model.Bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankYUI extends JPanel {
    private Bank bank;
    private JTextArea displayArea;

    public BankYUI() {
        bank = new Bank();
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBackground(Color.WHITE);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(Color.LIGHT_GRAY);
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create Account UI Components
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBackground(Color.GREEN);
        createAccountButton.addActionListener(new CreateAccountListener());
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(createAccountButton, gbc);

        // Deposit UI Components
        JButton depositButton = new JButton("Deposit");
        depositButton.setBackground(Color.YELLOW);
        depositButton.addActionListener(new DepositListener());
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(depositButton, gbc);

        // Withdraw UI Components
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBackground(Color.ORANGE);
        withdrawButton.addActionListener(new WithdrawListener());
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(withdrawButton, gbc);

        // Transfer UI Components
        JButton transferButton = new JButton("Transfer");
        transferButton.setBackground(Color.CYAN);
        transferButton.addActionListener(new TransferListener());
        gbc.gridx = 3;
        gbc.gridy = 0;
        panel.add(transferButton, gbc);

        // Check Balance UI Components
        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setBackground(Color.PINK);
        checkBalanceButton.addActionListener(new CheckBalanceListener());
        gbc.gridx = 4;
        gbc.gridy = 0;
        panel.add(checkBalanceButton, gbc);

        add(panel, BorderLayout.SOUTH);
    }

    private class CreateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel createAccountPanel = new JPanel(new GridLayout(0, 1));
            JTextField accountNumberField = new JTextField(10);
            JTextField ownerField = new JTextField(10);
            JPasswordField passwordField = new JPasswordField(10);
            createAccountPanel.add(new JLabel("Account Number:"));
            createAccountPanel.add(accountNumberField);
            createAccountPanel.add(new JLabel("Account Owner:"));
            createAccountPanel.add(ownerField);
            createAccountPanel.add(new JLabel("Password:"));
            createAccountPanel.add(passwordField);

            int option = JOptionPane.showConfirmDialog(null, createAccountPanel, "Create Account", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String accountNumber = accountNumberField.getText();
                String owner = ownerField.getText();
                String password = new String(passwordField.getPassword());
                double initialBalance = 0.0; // Default initial balance

                Account account = bank.createAccount(accountNumber, owner, password, initialBalance);
                displayArea.append("Account Created: " + account.getAccountNumber() + " (" + account.getOwner() + ") - Balance: " + account.getBalance() + "\n");
            }
        }
    }

    private class DepositListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel depositPanel = new JPanel(new GridLayout(0, 1));
            JTextField accountNumberField = new JTextField(10);
            JPasswordField passwordField = new JPasswordField(10);
            JTextField amountField = new JTextField(10);
            depositPanel.add(new JLabel("Account Number:"));
            depositPanel.add(accountNumberField);
            depositPanel.add(new JLabel("Password:"));
            depositPanel.add(passwordField);
            depositPanel.add(new JLabel("Amount to Deposit:"));
            depositPanel.add(amountField);

            int option = JOptionPane.showConfirmDialog(null, depositPanel, "Deposit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String accountNumber = accountNumberField.getText();
                String password = new String(passwordField.getPassword());
                double amount = Double.parseDouble(amountField.getText());

                Account account = bank.getAccount(accountNumber);
                if (account != null && account.verifyPassword(password)) {
                    account.deposit(amount);
                    displayArea.append("Deposited " + amount + " to Account: " + account.getAccountNumber() + " - New Balance: " + account.getBalance() + "\n");
                    JOptionPane.showMessageDialog(null, "Deposit successful!", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("path/to/deposit_icon.png"));
                } else {
                    displayArea.append("Invalid account or password.\n");
                }
            }
        }
    }

    private class WithdrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel withdrawPanel = new JPanel(new GridLayout(0, 1));
            JTextField accountNumberField = new JTextField(10);
            JPasswordField passwordField = new JPasswordField(10);
            JTextField amountField = new JTextField(10);
            withdrawPanel.add(new JLabel("Account Number:"));
            withdrawPanel.add(accountNumberField);
            withdrawPanel.add(new JLabel("Password:"));
            withdrawPanel.add(passwordField);
            withdrawPanel.add(new JLabel("Amount to Withdraw:"));
            withdrawPanel.add(amountField);

            int option = JOptionPane.showConfirmDialog(null, withdrawPanel, "Withdraw", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String accountNumber = accountNumberField.getText();
                String password = new String(passwordField.getPassword());
                double amount = Double.parseDouble(amountField.getText());

                Account account = bank.getAccount(accountNumber);
                if (account != null && account.verifyPassword(password)) {
                    if (account.withdraw(amount)) {
                        displayArea.append("Withdrew " + amount + " from Account: " + account.getAccountNumber() + " - New Balance: " + account.getBalance() + "\n");
                        JOptionPane.showMessageDialog(null, "Withdrawal successful!", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("path/to/withdraw_icon.png"));
                    } else {
                        displayArea.append("Insufficient funds.\n");
                    }
                } else {
                    displayArea.append("Invalid account or password.\n");
                }
            }
        }
    }

    private class TransferListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel transferPanel = new JPanel(new GridLayout(0, 1));
            JTextField fromAccountNumberField = new JTextField(10);
            JPasswordField fromPasswordField = new JPasswordField(10);
            JTextField toAccountNumberField = new JTextField(10);
            JTextField amountField = new JTextField(10);
            transferPanel.add(new JLabel("From Account Number:"));
            transferPanel.add(fromAccountNumberField);
            transferPanel.add(new JLabel("Password:"));
            transferPanel.add(fromPasswordField);
            transferPanel.add(new JLabel("To Account Number:"));
            transferPanel.add(toAccountNumberField);
            transferPanel.add(new JLabel("Amount to Transfer:"));
            transferPanel.add(amountField);

            int option = JOptionPane.showConfirmDialog(null, transferPanel, "Transfer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String fromAccountNumber = fromAccountNumberField.getText();
                String fromPassword = new String(fromPasswordField.getPassword());
                String toAccountNumber = toAccountNumberField.getText();
                double amount = Double.parseDouble(amountField.getText());

                Account fromAccount = bank.getAccount(fromAccountNumber);
                Account toAccount = bank.getAccount(toAccountNumber);

                if (fromAccount != null && toAccount != null && fromAccount.verifyPassword(fromPassword)) {
                    if (fromAccount.transferTo(toAccount, amount)) {
                        displayArea.append("Transferred " + amount + " from Account: " + fromAccount.getAccountNumber() + " to Account: " + toAccount.getAccountNumber() + "\n");
                        JOptionPane.showMessageDialog(null, "Transfer successful!", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("path/to/transfer_icon.png"));
                    } else {
                        displayArea.append("Insufficient funds.\n");
                    }
                } else {
                    displayArea.append("Invalid account or password.\n");
                }
            }
        }
    }

    private class CheckBalanceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel checkBalancePanel = new JPanel(new GridLayout(0, 1));
            JTextField accountNumberField = new JTextField(10);
            JPasswordField passwordField = new JPasswordField(10);
            checkBalancePanel.add(new JLabel("Account Number:"));
            checkBalancePanel.add(accountNumberField);
            checkBalancePanel.add(new JLabel("Password:"));
            checkBalancePanel.add(passwordField);

            int option = JOptionPane.showConfirmDialog(null, checkBalancePanel, "Check Balance", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String accountNumber = accountNumberField.getText();
                String password = new String(passwordField.getPassword());

                Account account = bank.getAccount(accountNumber);
                if (account != null && account.verifyPassword(password)) {
                    double balance = account.getBalance();
                    displayArea.append("Account: " + account.getAccountNumber() + " - Balance: " + balance + "\n");
                    JOptionPane.showMessageDialog(null, "Balance: " + balance, "Balance", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("path/to/balance_icon.png"));
                } else {
                    displayArea.append("Invalid account or password.\n");
                }
            }
        }
    }
}

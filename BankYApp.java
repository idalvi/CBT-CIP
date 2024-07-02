package com.banky.ui;

import javax.swing.*;
import java.awt.*;

public class BankYApp extends JFrame {
    public BankYApp() {
        setTitle("BankY");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        BankYUI bankYUI = new BankYUI();
        add(bankYUI, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankYApp::new);
    }
}

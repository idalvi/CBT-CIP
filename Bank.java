package com.banky.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;
    private static final String DATA_FILE = "data/accounts.txt";

    public Bank() {
        accounts = new HashMap<>();
        loadAccounts();
    }

    public Account createAccount(String accountNumber, String owner, String password, double initialBalance) {
        Account account = new Account(accountNumber, owner, password, initialBalance);
        accounts.put(accountNumber, account);
        saveAccounts();
        return account;
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            accounts = (Map<String, Account>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            accounts = new HashMap<>();
        }
    }
}

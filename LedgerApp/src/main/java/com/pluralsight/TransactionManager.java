package com.pluralsight;

/**
 * TransactionManager.java
 * This class handles reading and writing transactions to the transactions.csv file.
 * It provides methods to save a new transaction and to load all transactions.
 */
import java.io.*;
import java.util.*;

public class TransactionManager {
    // Name of the CSV file to store transactions
    public static String FILE_NAME = "transactions.csv";

    // Appends a transaction to the CSV file
    public static void addTransaction(Transaction transaction) {
        try {
            FileWriter fw = new FileWriter(FILE_NAME, true); // true = append mode
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(transaction.toCSV()); // Write the transaction as CSV
            bw.newLine(); // Move to next line
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    // Reads all transactions from the CSV file and returns them in reverse order (newest first)
    public static ArrayList<Transaction> readTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
            }
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                Transaction t = Transaction.fromCSV(line); // Convert each line to Transaction
                transactions.add(t);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }

        Collections.reverse(transactions); // Reverse to show newest first
        return transactions;
    }
}




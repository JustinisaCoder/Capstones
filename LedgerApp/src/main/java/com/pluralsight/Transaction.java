package com.pluralsight;

/**
 * Transaction.java
 * This class represents a single financial transaction.
 * It contains fields for date, time, description, vendor, and amount,
 * and provides methods to convert to/from CSV and to print itself.
 */
public class Transaction {
    // Variables to hold transaction details
    String date;
    String time;
    String description;
    String vendor;
    double amount;

    // Constructor to create a new Transaction
    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Converts a transaction to a CSV format string
    public String toCSV() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

    // Converts a CSV string to a Transaction object
    public static Transaction fromCSV(String line) {
        String[] parts = line.split("\\|");
        String date = parts[0];
        String time = parts[1];
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);
        return new Transaction(date, time, description, vendor, amount);
    }

    // Prints the transaction in a readable format
    public void print() {
        System.out.println(date + " " + time + " | " + description + " | " + vendor + " | " + amount);
    }
}



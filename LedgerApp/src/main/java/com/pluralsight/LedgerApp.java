package com.pluralsight;

/**
 * LedgerApp.java
 * This is the main class for the Accounting Ledger CLI application.
 * It provides the main menu, transaction entry, ledger view, and report filters.
 */

import java.util.*;
import java.time.*;
import java.time.format.*;

public class LedgerApp {
    static Scanner scanner = new Scanner(System.in); // For user input
    static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n--- Hi and Welcome to Your Safe Accounting Ledger ---");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().toUpperCase();

            if (choice.equals("D")) {
                addTransaction(true);
            } else if (choice.equals("P")) {
                addTransaction(false);
            } else if (choice.equals("L")) {
                showLedger();
            } else if (choice.equals("X")) {
                running = false;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }

        System.out.println("Goodbye!");
    }

    // Adds a new deposit or payment
    public static void addTransaction(boolean isDeposit) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (!isDeposit) {
            amount = -amount;
        }

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(dateFormat);
        String time = now.format(timeFormat);

        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        TransactionManager.addTransaction(transaction);
        System.out.println("Transaction saved!");
    }

    // Ledger screen with filtering options
    public static void showLedger() {
        ArrayList<Transaction> transactions = TransactionManager.readTransactions();
        boolean inLedger = true;

        while (inLedger) {
            System.out.println("\n--- Ledger Menu ---");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().toUpperCase();

            if (choice.equals("A")) {
                for (Transaction t : transactions) {
                    t.print();
                }
            } else if (choice.equals("D")) {
                for (Transaction t : transactions) {
                    if (t.amount > 0) {
                        t.print();
                    }
                }
            } else if (choice.equals("P")) {
                for (Transaction t : transactions) {
                    if (t.amount < 0) {
                        t.print();
                    }
                }
            } else if (choice.equals("R")) {
                showReports(transactions);
            } else if (choice.equals("H")) {
                inLedger = false;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    // Report menu with predefined filters and custom search
    public static void showReports(ArrayList<Transaction> transactions) {
        boolean inReports = true;

        while (inReports) {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                filterByDate(transactions, "MTD");
            } else if (choice.equals("2")) {
                filterByDate(transactions, "PM");
            } else if (choice.equals("3")) {
                filterByDate(transactions, "YTD");
            } else if (choice.equals("4")) {
                filterByDate(transactions, "PY");
            } else if (choice.equals("5")) {
                searchByVendor(transactions);
            } else if (choice.equals("6")) {
                customSearch(transactions);
            } else if (choice.equals("0")) {
                inReports = false;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    // Filters by predefined time ranges (MTD, PM, YTD, PY)
    public static void filterByDate(ArrayList<Transaction> transactions, String type) {
        LocalDate today = LocalDate.now();
        LocalDate start = today;
        LocalDate end = today;

        if (type.equals("MTD")) {
            start = today.withDayOfMonth(1);
        } else if (type.equals("PM")) {
            start = today.minusMonths(1).withDayOfMonth(1);
            end = start.withDayOfMonth(start.lengthOfMonth());
        } else if (type.equals("YTD")) {
            start = today.withDayOfYear(1);
        } else if (type.equals("PY")) {
            start = today.minusYears(1).withDayOfYear(1);
            end = start.withDayOfYear(start.lengthOfYear());
        }

        System.out.println("\n--- Filtered Transactions ---");
        for (Transaction t : transactions) {
            LocalDate tDate = LocalDate.parse(t.date);
            if ((tDate.isEqual(start) || tDate.isAfter(start)) &&
                    (tDate.isEqual(end) || tDate.isBefore(end))) {
                t.print();
            }
        }
    }

    // Searches by vendor name
    public static void searchByVendor(ArrayList<Transaction> transactions) {
        System.out.print("Enter vendor name to search: ");
        String vendorInput = scanner.nextLine().toLowerCase();

        System.out.println("\n--- Transactions Matching Vendor ---");
        for (Transaction t : transactions) {
            if (t.vendor.toLowerCase().contains(vendorInput)) {
                t.print();
            }
        }
    }

    // Custom search with multiple optional fields
    public static void customSearch(ArrayList<Transaction> transactions) {
        try {
            System.out.print("Start date (yyyy-MM-dd, optional): ");
            String startDateStr = scanner.nextLine();

            System.out.print("End date (yyyy-MM-dd, optional): ");
            String endDateStr = scanner.nextLine();

            System.out.print("Description (optional): ");
            String description = scanner.nextLine().toLowerCase();

            System.out.print("Vendor (optional): ");
            String vendor = scanner.nextLine().toLowerCase();

            System.out.print("Amount (optional): ");
            String amountStr = scanner.nextLine();

            LocalDate startDate = startDateStr.isEmpty() ? null : LocalDate.parse(startDateStr);
            LocalDate endDate = endDateStr.isEmpty() ? null : LocalDate.parse(endDateStr);
            Double amount = amountStr.isEmpty() ? null : Double.parseDouble(amountStr);

            System.out.println("\n--- Custom Search Results ---");

            for (Transaction t : transactions) {
                boolean match = true;

                LocalDate tDate = LocalDate.parse(t.date);

                if (startDate != null && tDate.isBefore(startDate)) {
                    match = false;
                }
                if (endDate != null && tDate.isAfter(endDate)) {
                    match = false;
                }
                if (!description.isEmpty() && !t.description.toLowerCase().contains(description)) {
                    match = false;
                }
                if (!vendor.isEmpty() && !t.vendor.toLowerCase().contains(vendor)) {
                    match = false;
                }
                if (amount != null && t.amount != amount) {
                    match = false;
                }

                if (match) {
                    t.print();
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong. Please check your input and try again.");
        }
    }
}






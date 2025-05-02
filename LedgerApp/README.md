# Accounting Ledger App (Java CLI)

A simple, user-friendly, ledger system built with Java. This application allows you to track 
financial transactions under the rader from the government, look at specific and detail logs if needed, and generate reports 
straight from the terminal.

---

## Features

- [x] Add deposits or payment entries with optional manual date/time
- [x] Store all transactions in `transactions.csv`
- [x] View your full ledger or filter by deposits, payments, or reports
- [x] Run reports like:
    - Month-To-Date
    - Previous Month
    - Year-To-Date
    - Previous Year
    - Vendor Search
    - Custom Search (date, vendor, description, amount)
- [x] Color-coded user feedback for success, errors, and prompts
- [x] Graceful handling of input errors, empty results, and user cancellations

---

### Interesting Piece of Code

One of the most challenging things that I learned how to implement was the custom search. I challenged myself by not looking for outside help unless absolutely nessesary. What this piece of code allows you to do is it allows the user to search any transaction, granted that they put the information. It also filters out any unnessesary words so the search only functions when you put key words lets say "Chipotle" for instance. This sidequest taught me how handle user logic gracefully.

---

## File Structure

```bash
├── LedgerApp.java              # Entry point and main navigation
├── Transaction.java            # Data model for ledger entries
├── TransactionManager.java     # Handles reading/parsing transaction data
└── transactions.csv            # Transaction history (created/updated by app)

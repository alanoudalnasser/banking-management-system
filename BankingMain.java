/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bankingmain;


/*
 * @author: Alanoud Alnasser, Sarah Alhezaimi, Sarah Almoqhim.
 *
 * Entry point and controller for the Banking Management System.
 * main() simply calls run() to start the program.
 * run() displays the menu in a loop and handles all user interactions.
 * Stores all customers, accounts, and transactions directly as static ArrayLists.
 */
import java.util.ArrayList;
import java.util.Scanner;
public class BankingMain {
    // ===================== Storage =====================
    
    // Lists that hold all data for the duration of the program
    private static ArrayList<Customer>    customers    = new ArrayList<>();
    private static ArrayList<Account>     accounts     = new ArrayList<>();
    private static ArrayList<Transaction> transactions = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("---Banking Management System---");

        int choice = 0;
        do {
            printMenu();
            choice = readInt("Enter choice: ");
            System.out.println();
            switch (choice) {
                case 1:
                    createCustomer();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    withdraw();
                    break;
                case 5: 
                    checkBalance();
                    break;
                case 6:
                    displayAllAccounts();
                    break;
                case 7:
                    viewTransactionHistory();
                    break;
                case 8: 
                    calculateInterest();
                    break;     
                case 0: 
                    System.out.println("System closed.");
                    break;
                default: 
                    System.out.println("Error: Invalid option. Try again.");
                    break;
            }
            System.out.println();
        } while (choice != 0); // keep looping until the user chooses 0 to exit
    }

    // ===================== Menu =====================

    private static void printMenu() {
        System.out.println("1. Create Customer");
        System.out.println("2. Create Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Check Balance");
        System.out.println("6. Display All Accounts");
        System.out.println("7. View Transaction History");
        System.out.println("8. Calculate Interest for Savings Account");
        System.out.println("0. Exit");
        System.out.println("========================================");
    }

    // ===================== Customer =====================

    private static void createCustomer() {
        System.out.println("--- Create Customer ---");
        String id    = readString("Customer ID : ");
        String name  = readString("Name        : ");
        String phone = readString("Phone       : ");

        // Validate that required fields are not empty
        if (id.isEmpty() || name.isEmpty()) {
            System.out.println("Error: ID and name cannot be empty.");
            return;
        }
        // Prevent duplicate customer IDs
        if (findCustomerById(id) != null) {
            System.out.println("Error: Customer ID already exists: " + id);
            return;
        }
        customers.add(new Customer(id, name, phone));
        System.out.println("Customer Created: " + id + " - " + name);
    }


    // ===================== Account =====================

    private static void createAccount() {
        System.out.println("--- Create Account ---");
        System.out.println("Choose Account Type: - Savings    - Current");
        String type = readString("Account Type: ");

        String accNum     = readString("Account Number  : ");
        String customerId = readString("Customer ID     : ");
        double deposit    = readDouble("Initial Deposit : ");

        // Ensure the customer exists before creating an account for them
        Customer owner = findCustomerById(customerId);
        if (owner == null) {
            System.out.println("Error: Customer not found: " + customerId);
            return;
        }
        // Prevent duplicate account numbers
        if (findAccountByNumber(accNum) != null) {
            System.out.println("Error: Account number already exists: " + accNum);
            return;
        }

        if (deposit < 0) {
            System.out.println("Initial balance cannot be negative.");
            return;
        }
        
        if (type.equalsIgnoreCase("Savings")) {
            double rate   = readDouble("Interest Rate   : ");
            double minBal = readDouble("Minimum Balance : ");
            // Initial deposit must cover the minimum balance requirement
            if (deposit < minBal) {
                System.out.printf("Error: Initial deposit must be >= minimum balance (%.2f).%n", minBal);
                return;
            }
            SavingsAccount sa = new SavingsAccount(accNum, deposit, owner.getName(), rate, minBal);
            accounts.add(sa);
            owner.addAccount(sa); // also link the account to the customer
            System.out.println("Savings Account Created: " + accNum + " for " + owner.getName());
            System.out.println("Current Balance: "+sa.checkBalance());

        } else if (type.equalsIgnoreCase("Current")) {
            double overdraft  = readDouble("Overdraft Limit : ");
            double serviceFee = readDouble("Service Fee     : ");
            CurrentAccount ca = new CurrentAccount(accNum, deposit, owner.getName(), overdraft, serviceFee);
            accounts.add(ca);
            owner.addAccount(ca); // also link the account to the customer
            System.out.println("Current Account Created: " + accNum + " for " + owner.getName());
            System.out.println("Current Balance: "+ca.checkBalance());

        } else {
            System.out.println("Error: Invalid account type.");
            return;
        }
    }

    private static void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts in the system.");
            return;
        }
        for (Account a : accounts) {
            System.out.println("----------------------------------------");
            a.displayAccountInfo(); // polymorphic — calls the correct subclass display
        }
        System.out.println("----------------------------------------");
    }

    // ===================== Transactions =====================

    private static void deposit() {
        System.out.println("--- Deposit ---");
        String accNum = readString("Account Number : ");
        double amount = readDouble("Amount         : ");
        // Polymorphism:
        // Account reference may refer to either a SavingsAccount or a CurrentAccount object.
        Account acc = findAccountByNumber(accNum);
        if (acc == null) { 
            System.out.println("Error: Account not found: " + accNum); 
            return; 
        }
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }
        acc.deposit(amount);
        acc.checkBalance();
        // Generate a unique ID based on the current number of transactions
        String txId = "TX" + (transactions.size() + 1);
        transactions.add(new Transaction(txId, accNum, "DEPOSIT", amount));
    }

    private static void withdraw() {
        System.out.println("--- Withdraw ---");
        String accNum = readString("Account Number : ");
        double amount = readDouble("Amount         : ");
        Account acc = findAccountByNumber(accNum);
        if (acc == null) { 
            System.out.println("Error: Account not found: " + accNum); 
            return; 
        }
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }
        double balanceBefore = acc.getBalance();
        acc.withdraw(amount); // polymorphic — applies savings or current rules
        acc.checkBalance();
        // Only record the transaction if the withdrawal actually succeeded
        if (acc.getBalance() < balanceBefore) {
            String txId = "TX" + (transactions.size() + 1);
            transactions.add(new Transaction(txId, accNum, "WITHDRAWAL", amount));
        }
    }

    private static void checkBalance() {
        System.out.println("--- Check Balance ---");
        String accNum = readString("Account Number : ");
        Account acc = findAccountByNumber(accNum);
        if (acc == null) { 
            System.out.println("Error: Account not found: " + accNum); 
            return; 
        }
        System.out.printf("Current Balance : %.2f%n", acc.checkBalance());
    }

    private static void viewTransactionHistory() {
        System.out.println("--- Transaction History ---");
        String accNum = readString("Account Number : ");
        if (findAccountByNumber(accNum) == null) {
            System.out.println("Error: Account not found: " + accNum);
            return;
        }
        boolean found = false;
        System.out.printf("\n%-8s %-12s %-15s %-12s %-15s\n", "TX ID", "Account", "Type", "Amount", "Date");
        System.out.println("-------------------------------------------------------------");
        // Filter transactions that belong to the requested account
        for (Transaction t : transactions) {
            if (t.getAccountNumber().equals(accNum)) {
                t.displayTransaction();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions recorded for account: " + accNum);
        } else {
            System.out.println("-------------------------------------------------------------");
        }
    }
    
    public static void calculateInterest() {
        String accNum = readString("Savings Account Number : ");
        
        Account account = findAccountByNumber(accNum);

        if (account == null) {
            System.out.println("Error: Account number not found.");
            return;
        }

        if (account instanceof SavingsAccount) {
            // Downcasting: Convert Account reference back to SavingsAccount
            // so that SavingsAccount-specific methods can be used.
            SavingsAccount savingsAccount = (SavingsAccount) account;
            savingsAccount.calculateInterest();
            savingsAccount.checkBalance();
        } else {
            System.out.println("Error: This account is not a savings account.");
        }
    }
    
    // ===================== Search Helpers =====================

    // Loops through customers and returns the one matching the given ID, or null
    private static Customer findCustomerById(String id) {
        for (Customer c : customers)
            if (c.getCustomerId().equals(id)) 
                return c;
        return null;
    }

    // Loops through accounts and returns the one matching the given number, or null
    private static Account findAccountByNumber(String num) {
        for (Account a : accounts)
            if (a.getAccountNumber().equals(num)) 
                return a;
        return null;
    }

    // ===================== Input Helpers =====================

    // Reads a line of text input from the user
    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Keeps prompting until the user enters a valid integer
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try { 
                return Integer.parseInt(line); 
            }
            catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }
    }

    // Keeps prompting until the user enters a valid decimal number
    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try { 
                return Double.parseDouble(line); 
            }
            catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankingmain;

/**
 *
 * @author: Alanoud Alnasser, Sarah Alhezaimi, Sarah Almoqhim.
 *
 * Abstract parent class for all bank account types.
 * Holds the common attributes: accountNumber, balance, and customerName.
 * Provides shared deposit() and checkBalance() logic, and declares
 * withdraw() as abstract so each subclass enforces its own rules.
 */
// Contains common attributes and methods shared by Savings and Current accounts.
public abstract class Account {

    private String accountNumber;
    private double balance;
    private String customerName;

    public Account(String accountNumber, double initialBalance, String customerName) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.customerName = customerName;
    }

    // ---------- Deposit ----------
    // Adds the given amount to the balance after validating it is positive
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Deposit amount must be positive.");
            return;
        }
        balance += amount;
        System.out.printf("Deposited: %.2f | New Balance: %.2f%n", amount, balance);
    }

    // ---------- Abstract withdraw ----------
    // Each subclass must implement its own withdrawal rules
    public abstract void withdraw(double amount);

    // ---------- Balance / Info ----------
    // Returns the current balance without modifying it
    public double checkBalance() {
        return balance;
    }
    
    // Displays basic account information.
    public void displayAccountInfo() {
        System.out.println("Account #  : " + accountNumber);
        System.out.println("Owner      : " + customerName);
        System.out.printf ("Balance    : %.2f%n", balance);
    }

    // ---------- Protected setter so subclasses can update balance ----------
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    // ---------- Getters ----------
    public String getAccountNumber() { 
        return accountNumber; 
    }
    public String getCustomerName() { 
        return customerName;  
    }
    public double getBalance() { 
        return balance;       
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankingmain;

/**
 * 
 * @author: Alanoud Alnasser, Sarah Alhezaimi, Sarah Almoqhim.
 * 
 * A type of Account designed for frequent daily transactions.
 * Extends Account and adds an overdraft limit and a monthly service fee.
 * Withdrawals are allowed to go below zero up to the overdraft limit.
 * Provides applyServiceFee() to deduct the monthly charge from the balance.
 */
// Current account supports overdraft and service fees.
public class CurrentAccount extends Account {

    private double overdraftLimit; // maximum amount the balance can go below zero
    private double serviceFee;     // fixed monthly charge deducted from the balance

    public CurrentAccount(String accountNumber, double initialBalance,
                          String customerName, double overdraftLimit, double serviceFee) {
        super(accountNumber, initialBalance, customerName);
        this.overdraftLimit = overdraftLimit;
        this.serviceFee = serviceFee;
    }
    
    // Overrides the withdrawal behavior.
    // Allows withdrawal into negative balance but only up to the overdraft limit
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive.");
            return;
        }
        if (getBalance() - amount < -overdraftLimit) {
            System.out.printf("Error: Exceeds overdraft limit of %.2f.%n", overdraftLimit);
            return;
        }
        setBalance(getBalance() - amount);
        System.out.printf("Withdrawn: %.2f | New Balance: %.2f%n", amount, getBalance());
    }

    // Deducts the monthly service fee from the account balance
    public void applyServiceFee() {
        setBalance(getBalance() - serviceFee);
        System.out.printf("Service Fee Applied: %.2f | New Balance: %.2f%n",
                          serviceFee, getBalance());
    }

    @Override
    public void displayAccountInfo() {
        System.out.println("Type       : Current Account");
        super.displayAccountInfo();
        System.out.printf ("Overdraft  : %.2f | Service Fee: %.2f%n",
                           overdraftLimit, serviceFee);
    }
}

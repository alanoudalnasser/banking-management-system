
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankingmain;

/**
 * 
 * @author: Alanoud Alnasser, Sarah Alhezaimi, Sarah Almoqhim.
 * 
 * A type of Account designed for saving money.
 * Extends Account and adds an interest rate and a minimum balance rule.
 * Withdrawals are blocked if they would drop the balance below the minimum.
 * Provides calculateInterest() to apply earned interest to the balance.
 */
// Savings account inherits common behavior from Account
public class SavingsAccount extends Account {

    private double interestRate;    // e.g. 0.05 = 5%
    private double minimumBalance; // balance must not fall below this after a withdrawal

    public SavingsAccount(String accountNumber, double initialBalance,
                          String customerName, double interestRate, double minimumBalance) {
        super(accountNumber, initialBalance, customerName);
        this.interestRate   = interestRate;
        this.minimumBalance = minimumBalance;
    }

    // Overrides the abstract withdraw method.
    //Blocks the withdrawal if the remaining balance would fall below the minimum
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive.");
            return;
        }
        if (getBalance() - amount < minimumBalance) {
            System.out.printf("Error: Insufficient funds. Minimum balance of %.2f must be "
                    + "maintained.%n",minimumBalance);
            return;
        }
        setBalance(getBalance() - amount);
        System.out.printf("Withdrawn : %.2f | New Balance: %.2f%n", amount, getBalance());
    }

    // Calculates interest earned and adds it to the account balance
    public void calculateInterest() {
        double interest = getBalance() * interestRate;
        setBalance(getBalance() + interest);
        System.out.printf("Interest Applied: %.2f (%.0f%%) | New Balance: %.2f%n",
                          interest, interestRate * 100, getBalance());
    }

    @Override
    public void displayAccountInfo() {
        System.out.println("Type       : Savings Account");
        super.displayAccountInfo();
        System.out.printf("Min Balance: %.2f | Interest Rate: %.0f%%\n",
                           minimumBalance, interestRate * 100);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankingmain;

/*
 * 
 * @author: Alanoud Alnasser, Sarah Alhezaimi, Sarah Almoqhim.
 *
 * Represents a single financial event on an account.
 * Records a unique transaction ID, account number, transaction type
 * (DEPOSIT or WITHDRAWAL), the amount, and the date it was made.
 */
public class Transaction {

    private String transactionId;   // unique ID e.g. TX1, TX2
    private String accountNumber;   // the account this transaction belongs to
    private String transactionType; // either "DEPOSIT" or "WITHDRAWAL"
    private double amount;
    private String dateText;        // set automatically to today's date

    public Transaction(String transactionId, String accountNumber,
                       String transactionType, double amount) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.dateText = java.time.LocalDate.now().toString(); // captures the current date
    }

    // Displays transaction details.
    public void displayTransaction() {
        System.out.printf("%-8s %-12s %-15s %-12.2f %-15s\n",
                          transactionId, accountNumber, transactionType, amount, dateText);
    }

    // ---------- Getters ----------
    public String getTransactionId() { 
        return transactionId; 
    }
    public String getAccountNumber() { 
        return accountNumber; 
    }
    public String getType() { 
        return transactionType;          
    }
    public double getAmount() { 
        return amount;        
    }
    public String getDateText() { 
        return dateText;      
    }
}

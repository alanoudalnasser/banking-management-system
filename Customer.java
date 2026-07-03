/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankingmain;

/* 
 * 
 * @author: Alanoud Alnasser, Sarah Alhezaimi, Sarah Almoqhim.
 *
 * Represents a bank customer with personal details.
 * Stores the customer's ID, name, and phone number.
 * Maintains a list of all accounts owned by this customer.
 */
import java.util.ArrayList;
public class Customer {

    private String             customerId;
    private String             name;
    private String             phone;
    private ArrayList<Account> accountList; // all accounts belonging to this customer

    public Customer(String customerId, String name, String phone) {
        this.customerId  = customerId;
        this.name        = name;
        this.phone       = phone;
        this.accountList = new ArrayList<>();
    }

    // Links an account to this customer. 
    public void addAccount(Account account) {
        accountList.add(account);
    }
    
    // Displays customer details and all linked accounts.
    public void displayCustomerInfo() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name       : " + name);
        System.out.println("Phone      : " + phone);
        System.out.println("Accounts   : " + accountList.size());
    }

    // ---------- Getters ----------
    public String getCustomerId() { 
        return customerId;   
    }
    public String getName() { 
        return name;         
    }
    public String getPhone() { 
        return phone;        
    }
    public ArrayList<Account> getAccountList() { 
        return accountList;  
    }
}

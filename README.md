# Banking Management System

A console-based Banking Management System built in Java, demonstrating core object-oriented programming principles through a real-world domain: customers, accounts, and transactions.

## Overview

This program allows a user to create customers and bank accounts, perform deposits and withdrawals, check balances, view a full transaction history, and calculate interest on savings accounts. All data is held in memory using `ArrayList` collections during execution — no database or external storage is used, keeping the project focused on clean object-oriented design rather than persistence.

The goal of the design was to model the banking domain properly, rather than placing all logic inside `main`. Responsibilities are separated across several classes with clear relationships between them.

## Class Structure

| Class | Responsibility |
|---|---|
| `Account` | Abstract base class. Defines shared behavior: `deposit()`, abstract `withdraw()`, and `displayAccountInfo()`. |
| `SavingsAccount` | Extends `Account`. Enforces a minimum balance of 500 on withdrawal. |
| `CurrentAccount` | Extends `Account`. Allows an overdraft of up to 1000 and applies a service fee. |
| `Customer` | Holds an `ArrayList<Account>` — a customer can own multiple accounts (composition). |
| `Transaction` | Records every financial event independently, keeping transaction history clean. |
| `BankingMain` | The entry point. Holds the `ArrayList`s of customers, accounts, and transactions, and drives the main menu loop. |

**Relationships:** `SavingsAccount` and `CurrentAccount` both extend `Account` (inheritance). `Customer` contains an `ArrayList` of `Account` objects (composition). `BankingSystem` coordinates all operations across customers, accounts, and transactions.

## OOP Principles Demonstrated

- Encapsulation: Every attribute is private and accessed only through getters or purposeful methods. `Account.setBalance()` is `protected` rather than `public`, so subclasses can update the balance after a withdrawal without external code bypassing the withdrawal rules directly.
- Inheritance: `SavingsAccount` and `CurrentAccount` both extend `Account`, reusing shared attributes and logic without duplicating code.
- Polymorphism: All accounts are stored together in a single `ArrayList<Account>`, yet each type's `withdraw()` behaves according to its own rules.
- Abstraction: `Account` is declared abstract and can never be instantiated directly.

## How to Run

### Option 1: Using NetBeans
1. Clone or download this repository.
2. Open the project folder in NetBeans (`File → Open Project`).
3. Right-click the project → **Run**.

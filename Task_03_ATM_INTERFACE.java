// TASK_03_ATM_INTERFACE //

import java.util.Scanner;
 class BankAccount {
    private String accountNumber;
    private String pin;
    private double balance;

    public BankAccount(String accountNumber, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public boolean validateAccount(String accountNumber, String pin) {
        return this.accountNumber.equals(accountNumber) && this.pin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. Remaining balance: $" + balance);
        } else {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. Updated balance: $" + balance);
        } else {
            System.out.println("Invalid amount.");
        }
    }
}

public class Task_03_ATM_INTERFACE {
    private BankAccount[] accounts;
    private BankAccount currentAccount;
    private int loginAttempts;

    public Task_03_ATM_INTERFACE(BankAccount[] accounts) {
        this.accounts = accounts;
        this.currentAccount = null;
        this.loginAttempts = 0;
    }

    public void login(Scanner scanner) {
        if (currentAccount != null) {
            System.out.println("You are already logged in.");
            return;
        }

        System.out.print("Enter your account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();

        for (BankAccount account : accounts) {
            if (account.validateAccount(accountNumber, pin)) {
                currentAccount = account;
                loginAttempts = 0;
                System.out.println("Login successful.");
                return;
            }
        }

        loginAttempts++;
        if (loginAttempts >= 3) {
            System.out.println("Too many unsuccessful login attempts. The ATM is locked.");
            System.exit(0);
        } else {
            System.out.println("Invalid account number or PIN. Please try again.");
        }
    }

    public void logout() {
        if (currentAccount != null) {
            currentAccount = null;
            System.out.println("Logout successful.");
        } else {
            System.out.println("You are not logged in.");
        }
    }

    public void checkBalance() {
        if (currentAccount != null) {
            System.out.println("Your account balance is: $" + currentAccount.getBalance());
        } else {
            System.out.println("You are not logged in.");
        }
    }

    public void withdraw(Scanner scanner) {
        if (currentAccount != null) {
            System.out.print("Enter the amount to withdraw: ");
            double amount = Double.parseDouble(scanner.nextLine());
            currentAccount.withdraw(amount);
        } else {
            System.out.println("You are not logged in.");
        }
    }

    public void deposit(Scanner scanner) {
        if (currentAccount != null) {
            System.out.print("Enter the amount to deposit: ");
            double amount = Double.parseDouble(scanner.nextLine());
            currentAccount.deposit(amount);
        } else {
            System.out.println("You are not logged in.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BankAccount[] accounts = {
                new BankAccount("60100201002598", "1234", 1000),
                new BankAccount("60102001002456", "6781", 9000),
                new BankAccount("60100201002281", "8798", 15000)
        };
        Task_03_ATM_INTERFACE atm = new Task_03_ATM_INTERFACE(accounts);

        while (true) {
            displayMainMenu();

            int choice = getValidChoice(scanner, 5);

            switch (choice) {
                case 1:
                    atm.login(scanner);
                    break;

                case 2:
                    atm.checkBalance();
                    break;

                case 3:
                    atm.withdraw(scanner);
                    break;

                case 4:
                    atm.deposit(scanner);
                    break;

                case 5:
                    atm.logout();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("╭─────────────────────────────────────────────────╮");
        System.out.println("│                 ATM Menu                      │");
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.println("│ 1. Login                                       │");
        System.out.println("│ 2. Check Balance                               │");
        System.out.println("│ 3. Withdraw Money                              │");
        System.out.println("│ 4. Deposit Money                               │");
        System.out.println("│ 5. Logout                                      │");
        System.out.println("╰─────────────────────────────────────────────────╯");
        System.out.print("Enter your choice: ");
    }

    private static int getValidChoice(Scanner scanner, int maxChoice) {
        int choice;
        while (true) {
            System.out.print("Enter your choice (1-" + maxChoice + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= maxChoice) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return choice;
    }
}

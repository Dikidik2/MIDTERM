import java.util.Scanner;

public class BankingApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the OOP Banking System");
        double openingBalance = readNonNegativeBalance(scanner);

        SavingsAccount savingsAccount = new SavingsAccount("SAV-1001", openingBalance);
        CheckingAccount checkingAccount = new CheckingAccount("CHK-2001", openingBalance);

        Account selectedAccount = savingsAccount;

        printAccountSummary(selectedAccount);

        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": {
                    double amount = readPositiveAmount(scanner, "Amount to withdraw: ");
                    if (amount > 0) selectedAccount.withdraw(amount);
                    break;
                }
                case "2": {
                    double amount = readPositiveAmount(scanner, "Amount to deposit: ");
                    if (amount > 0) selectedAccount.deposit(amount);
                    break;
                }
                case "3":
                    printAccountSummary(selectedAccount);
                    break;
                case "4":

                    selectedAccount = (selectedAccount == savingsAccount) ? checkingAccount : savingsAccount;
                    printAccountSummary(selectedAccount);
                    break;
                case "5":
                    running = false;
                    System.out.println("Thank you for using the system!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }
        scanner.close();
    }

    static void showMenu() {
        System.out.println("1 Withdraw 2 Deposit 3 Show balance 4 Switch account 5 Exit");
        System.out.print("Choice: ");
    }

    static double readNonNegativeBalance(Scanner scanner) {
        while (true) {
            System.out.print("Opening balance: ");
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= 0) return value;
                System.out.println("Opening balance cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }


    static double readPositiveAmount(Scanner scanner, String prompt) {
        System.out.print(prompt);
        try {
            double value = Double.parseDouble(scanner.nextLine().trim());
            if (value > 0) return value;
            System.out.println("Invalid amount. Please enter a number greater than 0.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
        return -1;
    }

    static void printAccountSummary(Account account) {
        System.out.printf("Current account: %s | Balance: \u20B1%.2f%n",
                account.getAccountType(), account.getBalance());
    }
}

abstract class Account {
    private final String accountNumber;
    private double balance;

    public Account(String accountNumber, double openingBalance) {
        this.accountNumber = accountNumber;
        this.balance = openingBalance;
    }

    public String getAccountNumber() { return accountNumber; }

    public double getBalance() { return balance; }

    public abstract double calculateWithdrawalFee();

    public abstract String getAccountType();

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount. Deposit must be greater than 0.");
            return false;
        }
        balance += amount;
        System.out.printf("New %s balance: \u20B1%.2f%n", getAccountType(), balance);
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount. Withdrawal must be greater than 0.");
            return false;
        }

        double fee = calculateWithdrawalFee();
        if (amount + fee > balance) {
            System.out.printf("Insufficient funds. Amount + fee (\u20B1%.2f) exceeds your balance. No fee charged.%n",
                    amount + fee);
            return false;
        }
        balance -= (amount + fee);
        System.out.printf("Withdrawal fee: \u20B1%.2f | New %s balance: \u20B1%.2f%n",
                fee, getAccountType(), balance);
        return true;
    }
}

class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, double openingBalance) {
        super(accountNumber, openingBalance);
    }

    @Override
    public double calculateWithdrawalFee() { return 0; }

    @Override
    public String getAccountType() { return "Savings"; }
}

class CheckingAccount extends Account {
    public CheckingAccount(String accountNumber, double openingBalance) {
        super(accountNumber, openingBalance);
    }

    @Override
    public double calculateWithdrawalFee() { return 10; }

    @Override
    public String getAccountType() { return "Checking"; }
}

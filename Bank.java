import java.util.ArrayList;
import java.util.*;



// Base calss
// abstruct
abstract class Account
{
	protected int accountNumber;
	protected double balance;

	public Account()
	{

	}

	public Account(int accountNumber)
	{
		this.accountNumber = accountNumber;
		balance = 0;
	}

	//to get the account number
	public int getAccountNumber()
	{
		return accountNumber;
	}

	//get the balance
	public double getBalance()
	{
		return balance;
	}

	//Abstract
	//function to deposit

	public abstract void deposit(double amount);
	//abstract
	//function to withdraw
	public abstract void withdraw(double amount); 

}
//check account child class

class CheckingAccount extends Account
{
	//Transaction fee
	private static double fee = 2.5;
	//default constructor
	public CheckingAccount()
	{
		super();
	}

	/*
	parameter constructor to intialize the checkingAccount
	with a custom account number and a transaction fee

	*/

	public CheckingAccount(int accountNumber, double fee)
	{
		super(accountNumber);
		this.fee = fee;
	}




	public void deposit(double amount)
	{
		if(amount > 0)
		{
			System.out.printf("Amount depostied is %.2f", amount);
			System.out.println();
			balance+=amount;
			balance-=fee;
			System.out.printf("After deposit current balance is %.2f", balance);
			System.out.println();
		}
		else
		{
			System.out.println("Negative amount can't be depostied");
			System.out.println();
		}
	}
	public void withdraw(double amount)
	{
		if(amount > 0)
		{
			System.out.printf("Amount withdrawn is %.2f", amount);
			System.out.println();
			balance-=amount;
			balance-=fee;
		    System.out.printf("After deposit current balance is %.2f", balance);
		    System.out.println();
		}
		else
		{
			System.out.printf("Negative amount can't be withdrawn");
			System.out.println();
		}
	}
}

//saving account child class

class SavingsAccount extends Account
{
	// interest rate
	private double interestRate;


	public SavingsAccount()
	{
		super();
	}

	/*
	parameter constructor to intialize the checkingAccount
	with a custom account number and a transaction fee

	*/

	public SavingsAccount(int accountNumber, double interestRate)
	{
		super(accountNumber);
		this.interestRate = interestRate;
	}

	//getter method

	public double getInterestRate()
	{
		return interestRate;
	}

	//setter method
	public void setInterestRate(double interestRate)
	{
		this.interestRate = interestRate;
	}

	public double calInterest()
	{
		return balance*interestRate;
	}

	public void applyInterest()
	{
		double interest = calInterest();
		System.out.printf("Interest amount %.2f is added to balance", interest);
		System.out.println();
		deposit(interest);
	}

	public void deposit(double amount)
	{
		if(amount > 0)
		{
			System.out.printf("Amount depostied is %.2f/n", amount);
			System.out.println();
			balance+=amount;
			System.out.printf("After deposit current balance is %.2f", balance);
			System.out.println();
		}
		else
		{
			System.out.println("Negative amount can't be depostied");
			System.out.println();
		}
	}
	public void withdraw(double amount)
	{
		if(amount > 0)
		{
			System.out.printf("Amount withdrawn is %.2f", amount);
			System.out.println();
			balance-=amount;
		    System.out.printf("After deposit current balance is %.2f", balance);
		    System.out.println();
		}
		else
		{
			System.out.printf("Negative amount can't be withdrawn");
			System.out.println();
		}
	}
}

public class Bank
{
	public static void main(String args[])
	{
		Scanner keyboard = new Scanner(System.in);
		//Create array of accounts
		Account accounts[] = new Account[10];
		int numAccounts = 0;
		int choice;

		do
		{
			choice = menu(keyboard);
			System.out.println();

			if(choice == 1)
			{
				accounts[numAccounts++] = createAccount(keyboard);
			}
			else if(choice == 2)
			{
				doDeposit(accounts, numAccounts, keyboard);
			}
			else if(choice == 3)
			{
				doWithdraw(accounts, numAccounts, keyboard);
			}
			else if(choice == 4)
			{
				applyInterest(accounts, numAccounts, keyboard);
			}
			else
			{
				System.out.println("Goodbye!");
			}
			//System.out.println();
		}while(choice != 5);
	}

	//Account choice
	public static int accountMenu(Scanner keyboard)
	{
		System.out.println("Select Account Type");
		System.out.println("1. Checking Account");
		System.out.println("2. Savings Account");

		int choice;

		do
		{
			System.out.print("Enter Choice: ");
			choice = keyboard.nextInt();
		}while(choice < 1 || choice > 2);

		return choice;
	}

	public static int searchAccount(Account accounts [], int count, int accountNumber)
	{
		for(int i = 0; i < count; i++)
		{
			if(accounts[i].getAccountNumber() == accountNumber)
			{
				return i;
			}
		}
		return -1;
	}

	public static void doWithdraw(Account accounts[], int count, Scanner keyboard)
	{
		//get account number
		System.out.print("Enter Account Number: ");
		int accountNumber = keyboard.nextInt();

		//search for account
		int index = searchAccount(accounts, count, accountNumber);
		if(index >= 0)
		{
			//Account
			System.out.print("Enter Withdrawn Ammount: ");
			double amount = keyboard.nextDouble();
			accounts[index].withdraw(amount);
		}
		else
		{
			System.out.println("Account is not found with AccountNumber: "+accountNumber);
		}
	}

	public static void applyInterest(Account accounts[], int count, Scanner keyboard)
	{
		//get account number
		System.out.print("Enter Account Number: ");
		int accountNumber = keyboard.nextInt();

		//search for account
		int index = searchAccount(accounts, count, accountNumber);
		if(index >= 0)
		{
			//must be an instance of savings account
			if(accounts[index] instanceof SavingsAccount)
			{
				((SavingsAccount)accounts[index]).applyInterest();
			}

		}
		else
		{
			System.out.println("Account is not found with AccountNumber: " +accountNumber);
		}
	}

	public static void doDeposit(Account accounts[], int count, Scanner keyboard)
	{
		//get account number
		System.out.print("Enter Account Number: ");
		int accountNumber = keyboard.nextInt();

		//search for account
		int index = searchAccount(accounts, count, accountNumber);
		if(index >= 0)
		{
			//Account
			System.out.print("Enter Deposit Ammount: ");
		    double amount = keyboard.nextDouble();
			accounts[index].deposit(amount);
		}
		else
		{
			System.out.println("Account is not found");
		}
	}
	//Function to create new account
	public static Account createAccount(Scanner keyboard)
	{
		Account account = null;
		int choice = accountMenu(keyboard);

		int accountNumber;
		System.out.print("Enter Account Number: ");
		accountNumber = keyboard.nextInt();
		if(choice == 1) // checking account
		{
			System.out.print("Enter Transaction Fee: ");
			double fee = keyboard.nextDouble();
			account = new CheckingAccount(accountNumber, fee);
		}
		else  // Savings account
		{
			System.out.print("Enter Interest Rate: ");
			double ir = keyboard.nextDouble();
			account = new SavingsAccount(accountNumber, ir);
		}

		return account;
	}

	public static int menu(Scanner keyboard)
	{
		System.out.println("Bank Account Menu");
		System.out.println("1. Create New Account");
		System.out.println("2. Deposit Funds");
		System.out.println("3. Withdraw Funds");
		System.out.println("3. Apply Interest");
		System.out.println("5. Quit");

		int choice;

		do
		{
			System.out.print("Enter Choice: ");
			choice = keyboard.nextInt();
		}while(choice < 1 || choice > 6);

		return choice;
	}
}
package com.abc

import org.scalatest.{Matchers, FlatSpec}
import org.joda.time._

class BankTest extends FlatSpec with Matchers {

  "Bank" should "customer summary" in {
    val bank: Bank = new Bank
    val john: Customer = new Customer("John").openAccount(new Account(Account.CHECKING))
    bank.addCustomer(john)
    bank.customerSummary should be("Customer Summary\n - John (1 account)")
  }

  it should "checking account" in {
    val bank: Bank = new Bank
    val checkingAccount: Account = new Account(Account.CHECKING)
    val bill: Customer = new Customer("Bill").openAccount(checkingAccount)
    bank.addCustomer(bill)
    checkingAccount.deposit(100.0)
    bank.totalInterestPaid() should be(0.1)
  }

  it should "savings account" in {
    val bank: Bank = new Bank
    val checkingAccount: Account = new Account(Account.SAVINGS)
    bank.addCustomer(new Customer("Bill").openAccount(checkingAccount))
    checkingAccount.deposit(1500.0)
    bank.totalInterestPaid() should be(2.0)
  }

  it should "maxi savings account" in {
    val bank: Bank = new Bank
    val maxisaveAccount: Account = new Account(Account.MAXI_SAVINGS)
    bank.addCustomer(new Customer("Bill").openAccount(maxisaveAccount))
    maxisaveAccount.deposit(3000.0)
    bank.totalInterestPaid() should be(150.0)
  }

  it should "changes made program" in {
    val bank: Bank = new Bank
    val maxisaveAccount: Account = new Account(Account.MAXI_SAVINGS)
    val bill: Customer = new Customer("Bill").openAccount(maxisaveAccount)
    val checkingAccount: Account = new Account(Account.CHECKING)
    bill.openAccount(checkingAccount)
    bank.addCustomer(bill)
    checkingAccount.deposit(100.0, new LocalDate(2016, 1, 11))
    maxisaveAccount.deposit(100.0, new LocalDate(2016, 1, 10))
    maxisaveAccount.deposit(3000.0, new LocalDate(2016, 1, 10))
    maxisaveAccount.withdraw(10.0, new LocalDate(2016, 8, 10))
    maxisaveAccount.transfer(-21.0, checkingAccount, new LocalDate(2016, 8, 10))
    bank.totalInterestPaid() should be(153.57100000000003)
    bank.totalInterestPaid(true) should be(91.55245508657134)
    bill.getStatement should be("Statement for Bill\n" +
      "\nMaxi Savings Account\n  deposit $100.00\n  deposit $3000.00\n  withdrawal $10.00\n  Transfer withdrawal $21.00\nTotal $3069.00\n" +
      "\nChecking Account\n  deposit $100.00\n  Transfer deposit $21.00\nTotal $121.00\n" +
      "\nTotal In All Accounts $3190.00")
  }
}

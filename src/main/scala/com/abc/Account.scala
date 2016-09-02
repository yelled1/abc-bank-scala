package com.abc

import scala.collection.mutable.ListBuffer
import org.joda.time._

object Account {
  final val CHECKING:     Int = 0
  final val SAVINGS:      Int = 1
  final val MAXI_SAVINGS: Int = 2
}

class Account(val accountType: Int, var transactions: ListBuffer[Transaction] = ListBuffer()) {
  def deposit(amount: Double, transDate: LocalDate = LocalDate.now) {
    if (amount <= 0)
      throw new IllegalArgumentException("amount must be greater than zero")
    else transactions += Transaction(amount, transDate, 0)
  }

  def withdraw(amount: Double, transDate: LocalDate = LocalDate.now) {
    if (amount <= 0)
      throw new IllegalArgumentException("amount must be greater than zero")
    else transactions += Transaction(-amount, transDate, 0)
  }

  def transfer(amount: Double, that: Account, transDate: LocalDate = LocalDate.now) {
    this.transactions += Transaction(amount, transDate, 1)
    that.transactions += Transaction(-amount, transDate, 1)
  }

  def interestEarned(compoundr: Boolean = false): Double = {
    val amount: Double = sumTransactions()
    if (compoundr) compoundInterestEarned
    else calc_interestRate(amount, false) * amount
    }

  def calc_interestRate(amount: Double, low_maxi: Boolean = false): Double =
    accountType match {
      case Account.SAVINGS =>
        if (amount <= 1000)        0.001
        else(1 + (amount - 1000) * 0.002) / amount
      case Account.MAXI_SAVINGS =>
        if (low_maxi)              0.001
        else                       0.05
      case _ => 0.001
      }

  def sumTransactions(checkAllTransactions: Boolean = true): Double =
    transactions.map(_.amount).sum

  def withdrawnInLast10(): Boolean = {
    def chkDaysBtwn(t: ListBuffer[Transaction]): Boolean = {
      if (t.isEmpty) false
      else if (Days.daysBetween(t.head.transDate, LocalDate.now).getDays < 10 && t.head.amount < 0) true
      else chkDaysBtwn(t.tail)
    }
    if (chkDaysBtwn(transactions)) return true
    false
  }

  def compoundInterestEarned: Double = {
    if (transactions.isEmpty) return 0
    var last_withdrawDate: LocalDate = new LocalDate(1800,1,1)
    var last_transDate:    LocalDate = transactions.head.transactionDate
    var sumOfAccount: Double         = transactions.head.amount
    def calculate_interest(t: ListBuffer[Transaction], accum: Double): Double = {
      if (t.isEmpty) accum
      else {
        val nDays: Int = Days.daysBetween(last_transDate, t.head.transactionDate).getDays
        val wDays: Int = Days.daysBetween(last_withdrawDate, t.head.transactionDate).getDays
        sumOfAccount += t.head.amount
        val annual_rate = calc_interestRate(sumOfAccount, wDays < 10)
        val intEarned = compound_interest(sumOfAccount, annual_rate, 365.0, nDays / 365.0)
        last_transDate = t.head.transactionDate
        if (t.head.amount < 0) last_withdrawDate = t.head.transactionDate
        calculate_interest(t.tail, (accum + intEarned))
      }
    }
    calculate_interest(transactions.tail, 0)
  }
  def compound_interest(principal: Double, rate: Double, times_per_year: Double, years: Double): Double = {
    val body: Double      = 1 + (rate / times_per_year)
    val exponent: Double  = times_per_year * years
    (principal * math.pow(body, exponent) - principal)
  }
}

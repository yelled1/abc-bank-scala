package com.abc

import org.joda.time._

case class Transaction(var amount: Double, transDate: LocalDate = LocalDate.now, transType: Int = 0) {
  //val transactionDate = DateProvider.getInstance.now
  val transactionDate = transDate
  val transactionType = transType
}

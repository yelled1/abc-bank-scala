package com.abc

//import java.util.Calendar
//import java.util.Date
import org.joda.time._

object DateProvider {
  def getInstance: DateProvider = {
    if (instance == null) instance = new DateProvider
    instance
  }

  private var instance: DateProvider = null
}

class DateProvider {
  def now: LocalDate = LocalDate.now()
    //return Calendar.getInstance.getTime
}

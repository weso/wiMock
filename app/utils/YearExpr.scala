package es.weso.wimock

abstract class YearExpr

case class SingleYear(year: Int) extends YearExpr {
  if (!YearExpr.availableYears.contains(year))
    throw WIMockException("Year " + year + " is not available. Must be one of " + YearExpr.availableYears.mkString(", "))

  override def toString : String = year.toString 
}
case class Range(start:Int,end:Int) extends YearExpr {
  
  
  if (start >= end) 
      throw WIMockException("Bad range: " + start + " must be lower than " + end)

  for (y <- start to end) {
    if (!YearExpr.availableYears.contains(y))
      throw WIMockException("Year " + y + " in range (" + start + "-" + end + ") is not available. Years available: " + YearExpr.availableYears.mkString(", "))
  }

  override def toString : String = "range(" + start.toString + "-" + end.toString + ")"
}

object YearExpr {
    val availableYears = play.Play.application().configuration().getString("wimock.availableYears").split(",").map(_.toInt)

}

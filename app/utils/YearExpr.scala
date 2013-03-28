package es.weso.wimock

abstract class YearExpr

case class SingleYear(year: Int) extends YearExpr {
  override def toString : String = year.toString 
}
case class Range(start:Int,end:Int) extends YearExpr {
  override def toString : String = "range(" + start.toString + "," + end.toString + ")"
}

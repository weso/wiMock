package es.weso.wimock

class ParseYear {

  // Regular expressions for years
  private val range = """range\((\d+)-(\d+)\)"""
  private val year  = """(\d+)"""
  
  private val yearItem = "(" + range + "|" + year + ")"
  private val yearList = "^" + yearItem + "(," + yearItem + ")*" + "$" 

  def extractYears(s : String) : List[YearExpr] = {
    if (s == "") return Nil
    else {

    // Convert string to Regex
    val yearListRegex = yearList.r
    val yearItemRegex = yearItem.r
    
    yearListRegex findFirstIn s match {
      case Some(str) => 
        yearItemRegex.findAllIn(str).map(item => extractYearExpr(item)).toList
      case None => throw new ParseRouteException("Cannot parse " + s + " with " + yearListRegex)
    }  
    }
  }
  
  def extractYearExpr(s : String) : YearExpr = {
    val rangeRegex = range.r
    val yearRegex = year.r 
    s match {
      case rangeRegex(start,end) => new Range(start.toInt,end.toInt)
      case yearRegex(y) => new SingleYear(y.toInt)
      case _ => throw new ParseRouteException("Cannot parse " + s + "as a year or range")
    } 
  }

}

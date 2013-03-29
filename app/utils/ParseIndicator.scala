package es.weso.wimock

class ParseIndicator {

  private val indicator = """(\w+)"""
  private val indicatorList = indicator + "(," + indicator + ")*"
    
  def extractIndicators(s : String) : List[Indicator] = {
    if (s == "") return Nil 
    else {
    // Explicitly convert string to Regex
    val indicatorListRegex = indicatorList.r
    val indicatorRegex 	 = indicator.r
    
    indicatorListRegex findFirstIn s match {
      case Some(str) => 
        indicatorRegex.findAllIn(str).map(item => new Indicator(item)).toList
      case None => throw new WIMockException("Cannot parse " + s + " with " + indicatorListRegex)
    }  
    }
  }


}

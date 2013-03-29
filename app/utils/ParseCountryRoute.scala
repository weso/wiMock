package es.weso.wimock

class ParseCountry {

  // Regular expressions for countries
  private val countryCode = """(\w+)"""
  private val countryCodeList = countryCode + "(," + countryCode + ")*"
    
  private val group = """group\((""" + countryCodeList + """)\)"""
  private val region = """region\((\w+)\)"""
  
  private val countryItem = "(" + group + "|" + region + "|" + countryCode + ")"
  private val countryItemList = "^" + countryItem + "(," + countryItem + ")*" + "$" 

  def extractCountries(s : String) : List[CountryExpr] = {
    if (s == "") return Nil 
    else {
    // Explicitly convert string to Regex
    val countryItemListRegex = countryItemList.r
    val countryItemRegex 	 = countryItem.r
    
    countryItemListRegex findFirstIn s match {
      case Some(str) => 
        countryItemRegex.findAllIn(str).map(item => extractCountryExpr(item)).toList
      case None => throw new WIMockException("Cannot parse " + s + " with " + countryItemListRegex)
    }  
    }
  }

  def extractCountryExpr(s : String) : CountryExpr = {
     
    val countryRegex = countryCode.r
    val groupRegex = group.r
    val regionRegex = region.r

    s match {
      case regionRegex(name) => new NamedRegion(name)
      case groupRegex(cs,_,_,_) => new Group(extractSingleCountries(cs))
      case countryRegex(c) => new SingleCountry(c)
      case _ => throw new WIMockException("Cannot parse " + s + " as a country or group")
    } 
  }
  
  def extractSingleCountries(s : String) : List[SingleCountry] = {
	  val countryListRegex = countryCodeList.r
	  val countryCodeRegex = countryCode.r				  
	  countryListRegex findFirstIn s match {
    	  case Some(str) => 
        	    countryCodeRegex.findAllIn(str).map(item => new SingleCountry(item)).toList
		  case None => throw new WIMockException("Cannot parse " + s + " with " + countryListRegex)
	}  
  }

}

package es.weso.wimock

abstract class CountryExpr
case class SingleCountry(code: String) extends CountryExpr {
  if (!CountryExpr.availableCountries.contains(code))
    throw WIMockException("Country " + code + " is not available. Must be one of " + CountryExpr.availableCountries.mkString(", "))

  override def toString() : String = code
}

case class Group(countries: List[SingleCountry]) extends CountryExpr {
  
  override def toString : String = "group(" + countries.mkString(",") + ")"
}

case class NamedRegion(name: String) extends CountryExpr {
  if (!CountryExpr.availableRegions.contains(name))
    throw WIMockException("Region " + name + " is not available. Must be one of " + CountryExpr.availableRegions.mkString(", "))

  override def toString : String = "region(" + name + ")"
}

object CountryExpr {
   val availableCountries = play.Play.application().configuration().getString("wimock.availableCountries").split(",")
   val availableRegions = play.Play.application().configuration().getString("wimock.availableRegions").split(",")
}

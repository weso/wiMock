package es.weso.wimock

import models._

abstract class CountryExpr
case class SingleCountry(code: String) extends CountryExpr {
  if (!Country.availableCountries.contains(code))
    throw WIMockException("Country " + code + " is not available. Must be one of " + Country.availableCountries.mkString(", "))
  val country : Country = Country.find(code).get

  override def toString() : String = country.name + " - " + country.code
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
   val availableCountries = Country.availableCountries
   val availableRegions = play.Play.application().configuration().getString("wimock.availableRegions").split(",")
}

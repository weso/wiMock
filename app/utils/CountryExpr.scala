package es.weso.wimock

abstract class CountryExpr
case class SingleCountry(code: String) extends CountryExpr {
  override def toString() : String = code
}

case class Group(countries: List[SingleCountry]) extends CountryExpr {
  override def toString : String = "group(" + countries.mkString(",") + ")"
}

case class NamedRegion(name: String) extends CountryExpr {
  override def toString : String = "region(" + name + ")"
}

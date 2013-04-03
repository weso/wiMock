package models

import play.api.Logger
import play.api.Play.current
import es.weso.wimock._
import play.api.libs.json._
import play.api.libs.Jsonp


case class Country(
    code: String,
    code2: String,
	name: String,
	ranking: Int,
	score: Double
) {

  def toJson : JsValue = {
    Json.toJson(Map (
    			"code" -> Json.toJson(code),
    			"code2" -> Json.toJson(code2),
				"name" -> Json.toJson (name),
                "score" -> Json.toJson (score),
                "ranking" -> Json.toJson (ranking)
                )
      )
  }
  
   def toTurtle : String = {
"""@prefix wi-ontology: <http://data.webfoundation.org/webindex/ontology/>.
@prefix wi-country: <http://data.webfoundation.org/webindex/data/area/country/> .
@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
   

""" +
      "wi-country:" + code + 
          "  rdfs:label \"" + name + "\"@en; \n" +
          "  rdf:type wi-ont:Country . \n"
 }
}

object Country {
 
  var countries : List[Country] = Nil;
  
  def find(code: String) : Option[Country] = {
    countries.find(c => c.code == code || c.code2 == code).headOption 
  }
  
  def findAll() : List[Country] = {
    countries;
  }

  def create(country: Country) = {
    countries = country :: countries
  }
  
  def availableCountries : List[String] = {
    countries.map(c => c.code) ++ 
    countries.map(c => c.code2)
  }
}
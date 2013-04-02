package models

import play.api.Logger
import play.api.Play.current
import es.weso.wimock._
import play.api.libs.json._
import play.api.libs.Jsonp


case class Country(
    code: String,
	name: String,
	ranking: Int,
	score: Double
) {

  def toJson : JsValue = {
    Json.toJson(Map (
    			"code" -> Json.toJson(code),
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
 
  def find(code: String) : Country = {
    return new Country("ESP","Spain",18,72.12)
  }

}
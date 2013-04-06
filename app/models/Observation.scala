package models

import play.api.Logger
import play.api.Play.current
import es.weso.wimock._
import play.api.libs.json._
import play.api.libs.Jsonp


case class Observation(
	country: String,
	indicator: String,
	year: Int,
	value: Double
) {
  
  def getId : String = {
    (country + indicator + year.toString + value.toString).hashCode.toString
  }
}

object Observation {
 
  def find(country: String,indicator:String,year:Int) : List[Observation] = {
    List(Observation(country,indicator,year,generate(country,indicator,year)))
  }

  def findGroup(group: String,indicator:String,year:Int) : List[Observation] = {
    List(Observation(group,indicator,year,generate(group,indicator,year)))
  }

  def findRegion(region: String,indicator:String,year:Int) : List[Observation] = {
    List(Observation(region,indicator,year,generate(region,indicator,year)))
  }

  def find(ce: CountryExpr, indicator:String, year:Int) : List[Observation] = {
    ce match {
     case SingleCountry(c) => find(c,indicator,year)
     case Group(cs) => findGroup(ce.toString,indicator,year)
     case NamedRegion(n) => findRegion(ce.toString,indicator,year)
    }
  }
  
  def find(countries:List[CountryExpr],
		   indicators:List[Indicator],
		   years:List[YearExpr]) : List[Observation] = {
    countries.map (ce => 
      indicators.map (ie => 
        years.map (ye => 
          ye match {
            case SingleYear(y) => 
            	ie match {
            	  case Indicator(i) => find(ce,i,y)
            	  case _ => throw new WIMockException("Unknown indicator expressoin: " + ie)
            	}
            case Range(start,end) =>
              	ie match {
            	  case Indicator(i) => 
            	    (start to end).map(y => find(ce,i,y)).flatten
            	  case _ => throw new WIMockException("Unknown indicator expressoin: " + ie)
            	}
            case _ => throw WIMockException("Unknown year expression: " + ye)
          }
        ).flatten
      ).flatten
    ).flatten      
  }

  
  /**
   * Generate a double value for an observation
   * The value is almost random
   * It is calculated from the values of country, indicator and year by a dirty trick
   */
  def generate(country: String, indicator: String, year: Int) : Double = {
    average(List(country.hashCode, indicator.hashCode, year).map(normalize))
  }
  
 def average(vals: List[Double]) : Double = {
   (0.0 /: vals ){_ + _} / vals.length
 }
 
 def normalize(x : Int) : Double = {
   Math.sin(x).abs
 }
 
 def jsonize(observations : List[Observation]) : JsValue = {
   Json.toJson (observations.map(obs => 
        Json.toJson(Map (
    			"country" -> Json.toJson(obs.country),
                "year" -> Json.toJson (obs.year),
                "indicator" -> Json.toJson (obs.indicator),
                "value" -> Json.toJson (obs.value)
                ))
   ))
 }
 
 def toTurtle(observations : List[Observation]) : String = {

"""@prefix wi-ontology: <http://data.webfoundation.org/webindex/ontology/>.
@prefix wi-obs: <http://data.webfoundation.org/webindex/observation/> .
@prefix wi-ind: <http://data.webfoundation.org/webindex/indicator/> .
@prefix wi-country: <http://data.webfoundation.org/webindex/country/> .
@prefix wi-year: <http://data.webfoundation.org/webindex/ref-year/> .
@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
   

""" +
    observations.map(obs =>
      "wi-obs:obs" + obs.getId + 
          "  wi-ontology:ref-indicator wi-ind:" + obs.indicator + "; \n" +
          "  wi-ontology:ref-area wi-country:" + obs.country + "; \n" +
          "  wi-ontology:ref-year \"" + obs.year.toString + "\"; \n" +
          "  rdf:type <http://purl.org/linked-data/cube#Observation> . \n"
    ).mkString
 }
}
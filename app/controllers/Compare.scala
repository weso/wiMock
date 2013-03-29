package controllers

import play.api._
import play.api.mvc._
import es.weso.wimock._
import models.Observation
import play.api.libs.json._
import play.api.libs.Jsonp
import play.api.libs.iteratee._


object Compare extends Controller {
  // Name of request parameters
  val country 	= "country"
  val year 		= "year"
  val indicator = "indicator"
    
  val TextTurtle = Accepting("text/turtle")
  
  
  def index = Action { request => {
    try {
    val countries = getCountries(request)
    val years = getYears(request)
    val indicators = getIndicators(request)
    val observations = Observation.find(countries,indicators,years)

    request match {
      case Accepts.Html() => prepareHTML(countries,years,indicators,observations) 
      case Accepts.Json() => prepareJSON(request,observations)
      case TextTurtle() => prepareTurtle(request,observations)
      case _ => BadRequest("Cannot handle accept header: " + request.accept.mkString(",") )
    }
    } catch {
        case e: WIMockException => BadRequest(e.msg)
    }
  }}
  
  def prepareHTML(countries: List[CountryExpr],years : List[YearExpr],indicators : List[Indicator],observations : List[Observation]) =
	  Ok(views.html.compare("Comparing elements",countries,years,indicators,observations))

  def prepareJSON(request: Request[AnyContent], observations : List[Observation]) = {
    val json = Observation.jsonize(observations)
    request.queryString.get("callback").flatMap(_.headOption) match {
        case Some(callback) => Ok(Jsonp(callback, json))
        case None => Ok(json)
    }
  }
  
  def prepareTurtle(request: Request[AnyContent], observations : List[Observation]) = {
          SimpleResult(
    		   header = ResponseHeader(200, Map(CONTENT_TYPE -> "text/turtle")), 
    		   body = Enumerator(Observation.toTurtle(observations))
       ) 
  }

  def getCountries(request : Request[AnyContent]) : List[CountryExpr] = {
    if (request.queryString.contains(country)) {
    	val pc = new ParseCountry
    	pc.extractCountries(request.queryString(country).headOption.getOrElse(""))
    } 
    else fillCountries
  }

  def getYears(request : Request[AnyContent]) : List[YearExpr] = {
    if (request.queryString.contains(year)) {
    	val py = new ParseYear
   		py.extractYears(request.queryString(year).headOption.getOrElse(""))
    } 
    else fillYears
  }

   def getIndicators(request : Request[AnyContent]) : List[Indicator] = {
    if (request.queryString.contains(indicator)) {
    	val pi = new ParseIndicator
   		pi.extractIndicators(request.queryString(indicator).headOption.getOrElse(""))
    } 
    else fillIndicators
  }

   def fillCountries : List[CountryExpr] = {
     CountryExpr.availableCountries.map(c => SingleCountry(c)).toList     
   }
   
   def fillYears : List[YearExpr] = {
     YearExpr.availableYears.map(y => SingleYear(y.toInt)).toList     
   }

   def fillIndicators : List[Indicator] = {
     Indicator.availableIndicators.map(i => Indicator(i)).toList     
   }

}
package controllers

import play.api._
import play.api.mvc._
import es.weso.wimock._

object Compare extends Controller {
  // Name of request parameters
  val country = "country"
  val year = "year"
    
  def index = Action { request => {
    try {
    val countries = getCountries(request)
    val years = getYears(request)
    Ok(views.html.compare("Comparing elements",countries,years))
    } catch {
        case e: ParseRouteException => BadRequest(e.msg)
    }
  }
  }
  
  def getCountries(request : Request[AnyContent]) : List[CountryExpr] = {
    if (request.queryString.contains(country)) {
    	val pc = new ParseCountry
    	pc.extractCountries(request.queryString(country).headOption.getOrElse(""))
    } 
    else Nil
  }

  def getYears(request : Request[AnyContent]) : List[YearExpr] = {
    if (request.queryString.contains(year)) {
    	val py = new ParseYear
   		py.extractYears(request.queryString(year).headOption.getOrElse(""))
    } 
    else Nil
  }

}
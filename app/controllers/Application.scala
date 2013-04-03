package controllers

import play.api._
import play.api.mvc._
import models.Country
import es.weso.utils.MyJson
import play.api.libs.iteratee._

object Application extends Controller {

  val TextTurtle = Accepting("text/turtle")

  def index = Action {
    Ok(views.html.index("WI-MOck - Web Index Mock"))
  }
  
  def country(code: String) = Action { implicit request =>
    Country.find(code) match {
      case Some(country) =>
      	request match {
      		case Accepts.Html() => Ok(views.html.country(country))
      		case Accepts.Json() => MyJson.prepareJSON(request,country.toJson)
      		case TextTurtle() => prepareTurtle(request,country)
      		case _ => BadRequest("Cannot handle accept header: " + request.accept.mkString(",") )
      	}
      case None => BadRequest("Country " + code + " not found")	
    }
  }

  def prepareTurtle(request: Request[AnyContent], country: Country) = {
          SimpleResult(
    		   header = ResponseHeader(200, Map(CONTENT_TYPE -> "text/turtle")), 
    		   body = Enumerator(country.toTurtle)
       ) 
  }


}
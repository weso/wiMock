package controllers

import play.api._
import play.api.mvc._
import es.weso.wimock._

object Test extends Controller {
  def index = Action { 
      Ok(views.html.test("Simple test page"))
  }
  
}
package controllers

import play.api._
import play.api.mvc._

object Compare extends Controller {
  
  def index = Action {
    Ok(views.html.index("Compare elements in webindex"))
  }
  
}
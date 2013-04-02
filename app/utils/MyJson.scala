package es.weso.utils

import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.libs.iteratee._

import play.api.libs.json._
import play.api.libs.Jsonp


object MyJson {

  def prepareJSON(request: Request[AnyContent], json : JsValue) = {
    request.queryString.get("callback").flatMap(_.headOption) match {
        case Some(callback) => Ok(Jsonp(callback, json))
        case None => Ok(json)
    }
  }

}
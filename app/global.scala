import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import models._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }


  override def onHandlerNotFound(request: RequestHeader): Result = {
    NotFound(
      views.html.notFoundPage(request.path)
    )
  }  
  
  override def onBadRequest(request: RequestHeader, error: String) = {
    BadRequest("Bad Request: " + error)
  }  
  
  override def onError(request: RequestHeader, ex: Throwable) = {
    InternalServerError(
      views.html.errorPage(ex)
    )
  }  
  
  override def onRouteRequest(request: RequestHeader): Option[Handler] = {
     Logger.info("Request. Uri=" + request.uri + 
                 ", Accept = " + request.headers("Accept") + 
                 ", remoteAddres =" + request.remoteAddress)
     super.onRouteRequest(request)
  }
  
  
}


object InitialData {
  
  def insert() = {
    
    if(Country.findAll.isEmpty) {
      
      Seq(
        Country("USA","us","United States of America",2,97.31),
        Country("SWE","se","Sweden",1,100),
        Country("ESP","es","Spain",18,72.12),
        Country("FRA","fr","France",14,78.93)
      ).foreach(Country.create)
    }
 }
}
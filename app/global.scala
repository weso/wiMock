import play.api._
import play.api.mvc._
import play.api.mvc.Results._

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



package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import es.weso.wimock._

class CountrySpec extends Specification {
  
  "Country " should {
    
    
    "render the country page" in {
      running(FakeApplication()) {
        val home = route(FakeRequest(GET, "/country")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("Countries")
      }
    }
  }

}
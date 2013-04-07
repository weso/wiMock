package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.mvc._

class ApplicationSpec extends Specification {
  
  "Application" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }

/*    "send 200 on root request" in new WithApplication {
        val result = route(FakeRequest(GET, "/"))
        status(result) must equalTo(OK)
    }

    "render the index page" in {
      running(FakeApplication()) {
        val home = route(FakeRequest(GET, "/")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("Web Index Mock")
      }
    } */
  } 
}
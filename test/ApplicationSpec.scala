package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {
  
  "Application" should {
    
    "send 404 on a bad request" in new WithApplication {
        val result = route(FakeRequest(GET, "/booom"))
        status(result.get) must equalTo(NOT_FOUND)
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
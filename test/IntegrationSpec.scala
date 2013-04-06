package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
class IntegrationSpec extends Specification {
  
  "Application" should {
    
    "work within a browser" in {
      running(TestServer(3333), HTMLUNIT) { browser =>
        browser.goTo("http://localhost:3333/")
        browser.pageSource must contain("Web Index Mock")
      }
    }

    "Get comparison" in {
      running(TestServer(3333), HTMLUNIT) { browser =>
        browser.goTo("http://localhost:3333/compare")
        browser.pageSource must contain("Comparison")
      }
    }
    
    "Get not found" in {
      running(TestServer(3333), HTMLUNIT) { browser =>
        browser.goTo("http://localhost:3333/booom")
        browser.pageSource must contain("Page not found")
      }
    }
    
    "Get compare es and fr" in {
      running(TestServer(3333), HTMLUNIT) { browser =>
        browser.goTo("http://localhost:3333/compare?country=es,fr")
        browser.pageSource must contain("Spain")
        browser.pageSource must contain("France")
      }
    }

  }
  
}
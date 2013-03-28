package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import es.weso.wimock._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class CompareSpec extends Specification {
  
  "Compare service" should {
    
    
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    
    "render the compare page" in {
      running(FakeApplication()) {
        val home = route(FakeRequest(GET, "/compare")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("Comparing")
      }
    }

    "compare two countries" in {
      running(FakeApplication()) {
        val home = route(FakeRequest(GET, "/compare?country=es,fr")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("es")
        contentAsString(home) must contain ("fr")
      }
    }

    "route parsing one country" in {
      val route = "es"
      val expected = List(new SingleCountry("es"))
      val pc = new ParseCountry()
      pc.extractCountries(route) must beEqualTo(expected)  
    }

    "route parsing two countries" in {
      val route = "es,fr"
      val expected = List(new SingleCountry("es"), new SingleCountry("fr"))
      val pc = new ParseCountry()
      pc.extractCountries(route) must beEqualTo(expected)  
    }

    "route parsing group of two countries" in {
      val route = "group(es,fr)"
      val expected = 
        List(new Group(
        		List(new SingleCountry("es"), 
        			 new SingleCountry("fr"))
        ))
      val pc = new ParseCountry()
      pc.extractCountries(route) must beEqualTo(expected)  
    }

    "route parsing group of two countries and a single country" in {
      val route = "group(es,fr),cn"
      val expected = 
        List(new Group(
        		List(new SingleCountry("es"), 
        			 new SingleCountry("fr"))),
            new SingleCountry("cn"))
      val pc = new ParseCountry()
      pc.extractCountries(route) must beEqualTo(expected)  
    }

    "route parsing group of two countries and a region" in {
      val route = "group(es,fr),region(europe)"
      val expected = 
        List(new Group(
        		List(new SingleCountry("es"), 
        			 new SingleCountry("fr"))),
            new NamedRegion("europe"))
      val pc = new ParseCountry()
      pc.extractCountries(route) must beEqualTo(expected)  
    }
    
    "route parsing a year" in {
      val route = "2011"
      val expected = 
        List(new SingleYear(2011))
      val py = new ParseYear()
      py.extractYears(route) must beEqualTo(expected)  
    }

    "route parsing two years" in {
      val route = "2011,2012"
      val expected = 
        List(new SingleYear(2011),new SingleYear(2012))
      val py = new ParseYear()
      py.extractYears(route) must beEqualTo(expected)  
    }

    "route parsing two years and range " in {
      val route = "2011,2012,range(2008-2010)"
      val expected = 
        List(new SingleYear(2011),
             new SingleYear(2012),
             new Range(2008,2010)
        )
      val py = new ParseYear()
      py.extractYears(route) must beEqualTo(expected)  
    }

  }
}
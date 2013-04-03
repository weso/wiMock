import models._

object InitialData {
  
  def insert() = {
    
    if(Country.findAll.isEmpty) {
      Seq(
        Country("SWE","se","Sweden",				  	1, 	100),
        Country("USA","us","United States of America",	2,	97.31),
        Country("FRA","fr","France",					14,	78.93),
        Country("ESP","es","Spain",						18,	72.12)
      ).foreach(Country.create)
    }
 }
}
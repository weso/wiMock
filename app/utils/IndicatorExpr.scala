package es.weso.wimock

case class Indicator(name: String) {
  if (!Indicator.availableIndicators.contains(name)) 
     throw WIMockException("Indicator " + name + " is not available. Must be one of " + Indicator.availableIndicators.mkString(", "))
  
  override def toString : String = name
}

object Indicator {
   val availableIndicators = play.Play.application().configuration().getString("wimock.availableIndicators").split(",")
   
}
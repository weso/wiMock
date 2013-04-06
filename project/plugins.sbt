// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.1.1")

libraryDependencies ++= Seq(
	"com.novocode" % "junit-interface" % "0.9-RC2" % "test->default",
	"org.seleniumhq.selenium" % "selenium-java" % "2.20.0" % "test",
	"info.cukes" % "cucumber-junit" % "1.0.9" % "test",
	"info.cukes" % "cucumber-scala" % "1.0.9" % "test"
	)

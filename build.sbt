enablePlugins(ScalaJSPlugin)

name := "fuckjs"

version := "Beta3"

organization := "se.chimps.js"

credentials += Credentials(Path.userHome / ".ivy2" / ".fuckjs")

publishTo := Some("se.chimps.js" at "http://yamr.kodiak.se/maven")

publishArtifact in (Compile, packageDoc) := false

resolvers += "se.chimps.js" at "http://yamr.kodiak.se/maven"

scalaVersion := "2.12.1"

scalaJSUseMainModuleInitializer := false

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"

libraryDependencies += "se.chimps.js" %%% "icanhazdom" % "20171010"

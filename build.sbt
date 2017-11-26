enablePlugins(ScalaJSPlugin)

name := "fuckjs"

version := "Beta3.4"

organization := "se.chimps.js"

credentials += Credentials(Path.userHome / ".ivy2" / ".fuckjs")

publishTo := Some("se.chimps.js" at "https://yamr.kodiak.se/maven")

publishArtifact in (Compile, packageDoc) := false

resolvers += "se.chimps.js" at "https://yamr.kodiak.se/maven"

scalaVersion := "2.12.1"

scalaJSUseMainModuleInitializer := false

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"

libraryDependencies += "se.chimps.js" %%% "icanhazdom" % "20171126"

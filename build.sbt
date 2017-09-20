enablePlugins(ScalaJSPlugin)

name := "fuckjs"

version := "Beta2"

organization := "se.chimps.fuckjs"

credentials += Credentials(Path.userHome / ".ivy2" / ".fuckjs")

publishTo := Some("se.chimps.fuckjs" at "http://yamr.kodiak.se/maven")

publishArtifact in (Compile, packageDoc) := false

scalaVersion := "2.12.1"

scalaJSUseMainModuleInitializer := false

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"

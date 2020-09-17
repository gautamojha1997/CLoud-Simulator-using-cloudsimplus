name := "simulation"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq("org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "org.cloudsimplus" % "cloudsim-plus" % "5.4.3",
  "ch.qos.logback" % "logback-classic" % "1.3.0-alpha5",
  "com.typesafe" % "config" % "1.4.0", "org.scalatest" % "scalatest" % "3.0.8" % "Test")

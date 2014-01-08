organization := "scala.com.store"

version := "1.0"

scalaVersion := "2.10.0"

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies +="org.scalatest" % "scalatest_2.10" % "2.0" % "test"


libraryDependencies += "junit" % "junit" % "4.5" % "test"

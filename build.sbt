name := "Food service"

version := "1.0.0"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.13",

  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.3" % Test,

  "com.h2database" % "h2" % "1.4.185",
  "com.typesafe.slick" %% "slick" % "3.2.3"
)
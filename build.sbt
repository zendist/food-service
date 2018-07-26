name := "Food service"

version := "1.0.0"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-stream" % "2.5.13",
  "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.14.0",
//  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.3",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.3" % Test,

  "com.h2database" % "h2" % "1.4.185",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "com.typesafe.slick" %% "slick" % "3.2.3"
)

enablePlugins(JavaAppPackaging)

import com.permutive.sbtliquibase.SbtLiquibase

enablePlugins(SbtLiquibase)
liquibaseUsername := "postgres"
liquibasePassword := "mysecretpassword"
liquibaseDriver   := "org.postgresql.jdbc.Driver"
liquibaseUrl      := "jdbc:postgresql:foodBase?createDatabaseIfNotExist=true"
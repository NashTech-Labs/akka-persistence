name := "akka-persistence"

version := "0.1"

scalaVersion := "2.12.8"

resolvers += "dnvriend" at "http://dl.bintray.com/dnvriend/maven"

val testDependencies = Seq(
  "com.github.dnvriend" %% "akka-persistence-inmemory" % "2.5.15.1",
  "org.scalatest" %% "scalatest" % "3.0.7" % Test,
  "com.typesafe.akka" %% "akka-testkit" % "2.5.19" % Test
)

val dependencies = Seq(
  "com.typesafe.akka" %% "akka-persistence" % "2.5.19",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
)

libraryDependencies ++= dependencies ++ testDependencies

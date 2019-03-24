name := "akka-persistence"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-persistence" % "2.5.19",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
)
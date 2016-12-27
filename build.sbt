name := "WebScalaPlayground"

version := "1.0"

scalaVersion := "2.11.7"

flywayLocations += "filesystem:./migrations"

flywayUrl := "jdbc:h2:file:./target/data"

flywayUser := "SA"

libraryDependencies ++= Seq(
  ws,
  "org.mockito" % "mockito-core" % "2.2.28" % "test",
  "org.scalatestplus.play" % "scalatestplus-play_2.11" % "2.0.0-M1" % "test",
  "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.10",
  "com.h2database" % "h2" % "1.4.191"
)

scalacOptions += "-feature"

coverageExcludedPackages := """controllers\..*Reverse.*;router.Routes.*;"""

lazy val root = (project in file(".")).enablePlugins(PlayScala)
    
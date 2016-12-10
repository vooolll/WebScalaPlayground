name := "WebScalaPlayground"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  ws,
  "org.mockito" % "mockito-core" % "2.2.28" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test",
  "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.10"
)

scalacOptions += "-feature"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
    
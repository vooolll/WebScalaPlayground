logLevel := Level.Warn

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/maven-releases/"
resolvers += "Flyway" at "https://flywaydb.org/repo"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.9")
addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.0.3")
name := "SNOWPARK-TWF"

version := "0.1"

scalaVersion := "2.12.13"

resolvers += "OSGeo Release Repository" at "https://repo.osgeo.org/repository/release/"
libraryDependencies += "com.snowflake" % "snowpark" % "0.6.0"
libraryDependencies += "org.skife.com.typesafe.config" % "typesafe-config" % "0.3.0"
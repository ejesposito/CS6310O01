import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "system"
  val appVersion      = "system-1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "org.apache.poi" % "poi" % "3.10.1",
    "org.apache.poi" % "poi-ooxml" % "3.10.1",
    "mysql" % "mysql-connector-java" % "5.1.18",
    "org.mindrot" % "jbcrypt" % "0.3m",
    "org.apache.commons" % "commons-email" % "1.4")

  val main = play.Project(appName, appVersion, appDependencies)
                 .settings(sbt.Keys.fork in Test := false)

}



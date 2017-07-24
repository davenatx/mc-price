import sbt._
 
object Version {
  val lift    = "3.1.0"
  val h2      = "1.4.196"
  val jetty   = "9.4.6.v20170531"
  val servlet = "3.1.0"
  val logback = "1.2.3"
}

object Library {
  val liftWebkit =  "net.liftweb"       %% "lift-webkit"      % Version.lift
  val liftMapper =  "net.liftweb"       %% "lift-mapper"      % Version.lift
  val h2 =          "com.h2database"    % "h2"                % Version.h2
  val jettyRunner = "org.eclipse.jetty" % "jetty-runner"      % Version.jetty
  val servlet =     "javax.servlet"     % "javax.servlet-api" % Version.servlet
  val logback =     "ch.qos.logback"    % "logback-classic"   % Version.logback
}

object Dependencies {
  import Library._

  val mcPrice = List(
    liftWebkit,
    liftMapper,
    h2,
    servlet     % "provided",
    logback
  )
}
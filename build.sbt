import com.typesafe.sbt.web.Import.WebKeys.{stage => webStage}

import scalariform.formatter.preferences._

name := "mc-price"

organization := "com.mcprice"

version := "0.1"

scalaVersion := "2.11.11"

scalacOptions ++= Seq("-optimize", "-deprecation", "-feature")

//javaOptions := Seq("-Drun.mode=production")

libraryDependencies ++= Dependencies.mcPrice

git.baseVersion := version.value

showCurrentGitBranch

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignParameters, true)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(CompactControlReadability, true)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(IndentLocalDefs, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)
  .setPreference(RewriteArrowSymbols, true)

enablePlugins(JettyPlugin)

// Control version of Jetty used by xsbt-web-plugin
containerLibs in Jetty := Seq(Library.jettyRunner intransitive())

containerMain in Jetty := "org.eclipse.jetty.runner.Runner"

// Initialize sbt-web plugin
lazy val root = (project in file(".")).enablePlugins(SbtWeb)

//sbt-less
LessKeys.compress in Assets := true

// Main less file
includeFilter in (Assets, LessKeys.less):= "styles.less"

// xsbt-web-plugin - copy css and js assets to Web App Directory
webappPostProcess := {
	webAppDir =>
		IO.copyFile(
      target.value / "web" / "less" / "main" / "styles" / "styles.min.css",
      webAppDir / "media" / "styles" / "styles.css"
    )
    // Include logback config
    IO.copyFile(
      baseDirectory.value / "src" / "main" / "resources" / "props" / "production.default.logback.xml",
      webAppDir / "WEB-INF" / "classes" / "props" / "production.default.logback.xml"
    )
    // Include production properties file
    IO.copyFile(
      baseDirectory.value / "src" / "main" / "resources" / "props" / "production.default.props",
      webAppDir / "WEB-INF" / "classes" / "props" / "production.default.props"
    )
}
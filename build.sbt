
// Our Scala versions.
lazy val `scala-2.12`     = "2.12.17"
lazy val `scala-2.13`     = "2.13.10"
lazy val `scala-3`        = "3.2.2"

// Publishing
ThisBuild / organization := "org.tpolecat"
ThisBuild / licenses    ++= Seq(("MIT", url("http://opensource.org/licenses/MIT")))
ThisBuild / homepage     := Some(url("https://github.com/tpolecat/typename"))
ThisBuild / developers   := List(
  Developer("tpolecat", "Rob Norris", "rob_norris@mac.com", url("http://www.tpolecat.org"))
)

// Headers
lazy val headerSettings = Seq(
  headerMappings := headerMappings.value + (HeaderFileType.scala -> HeaderCommentStyle.cppStyleLineComment),
  headerLicense  := Some(HeaderLicense.Custom(
    """|Copyright (c) 2020-2021 by Rob Norris
      |This software is licensed under the MIT License (MIT).
      |For more information see LICENSE or https://opensource.org/licenses/MIT
      |""".stripMargin
    )
  )
)

lazy val root = project.in(file("."))
  .aggregate(typename.jvm, typename.js, typename.native)
  .settings(
    sonatypeCredentialHost := "s01.oss.sonatype.org",
    publish / skip := true,
    headerSettings
  )

lazy val typename = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("typename"))
  .settings(
    name := "typename",

    headerSettings,

    // Compilation
    scalaVersion       := `scala-2.13`,
    crossScalaVersions := Seq(`scala-2.12`, `scala-2.13`, `scala-3`),
    Compile / doc     / scalacOptions --= Seq("-Xfatal-warnings"),
    Compile / doc     / scalacOptions ++= Seq(
      "-groups",
      "-sourcepath", (LocalRootProject / baseDirectory).value.getAbsolutePath,
      "-doc-source-url", "https://github.com/tpolecat/typename/blob/v" + version.value + "€{FILE_PATH}.scala",
    ),

    // MUnit
    libraryDependencies += "org.scalameta" %%% "munit" % "1.0.0-M6" % Test,

    // Scala 2 needs scala-reflect
    libraryDependencies ++= Seq("org.scala-lang" % "scala-reflect" % scalaVersion.value).filterNot(_ => scalaVersion.value.startsWith("3.")),

    // Publishing
    sonatypeCredentialHost := "s01.oss.sonatype.org"
  ).enablePlugins(AutomateHeaderPlugin)

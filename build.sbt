// Our Scala versions.
lazy val `scala-3.0`  = "3.0.0-M1"
lazy val `scala-2.12` = "2.12.12"
lazy val `scala-2.13` = "2.13.3"

// Publishing
name         := "typename"
organization := "org.tpolecat"
licenses    ++= Seq(("MIT", url("http://opensource.org/licenses/MIT")))
homepage     := Some(url("https://github.com/tpolecat/typename"))
developers   := List(
  Developer("tpolecat", "Rob Norris", "rob_norris@mac.com", url("http://www.tpolecat.org"))
)

// Headers
headerMappings := headerMappings.value + (HeaderFileType.scala -> HeaderCommentStyle.cppStyleLineComment)
headerLicense  := Some(HeaderLicense.Custom(
  """|Copyright (c) 2020 by Rob Norris
     |This software is licensed under the MIT License (MIT).
     |For more information see LICENSE or https://opensource.org/licenses/MIT
     |""".stripMargin
  )
)

// Compilation
scalaVersion       := `scala-2.13`
crossScalaVersions := Seq(`scala-2.12`, `scala-2.13`, `scala-3.0`)
Compile / doc     / scalacOptions --= Seq("-Xfatal-warnings")
Compile / doc     / scalacOptions ++= Seq(
  "-groups",
  "-sourcepath", (baseDirectory in LocalRootProject).value.getAbsolutePath,
  "-doc-source-url", "https://github.com/tpolecat/typename/blob/v" + version.value + "€{FILE_PATH}.scala",
)

// MUnit
libraryDependencies += "org.scalameta" %% "munit" % "0.7.18" % Test
testFrameworks += new TestFramework("munit.Framework")

// Scala 2 needs scala-reflect
libraryDependencies ++= Seq("org.scala-lang" % "scala-reflect" % scalaVersion.value).filterNot(_ => isDotty.value)

// Add some more source directories
unmanagedSourceDirectories in Compile ++= {
  val sourceDir = (sourceDirectory in Compile).value
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((3, _))  => Seq(sourceDir / "scala-3")
    case Some((2, _))  => Seq(sourceDir / "scala-2")
    case _             => Seq()
  }
}

// Also for test
unmanagedSourceDirectories in Test ++= {
  val sourceDir = (sourceDirectory in Test).value
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((3, _))  => Seq(sourceDir / "scala-3")
    case Some((2, _))  => Seq(sourceDir / "scala-2")
    case _             => Seq()
  }
}

// dottydoc really doesn't work at all right now
Compile / doc / sources := {
  val old = (Compile / doc / sources).value
  if (isDotty.value)
    Seq()
  else
    old
}

enablePlugins(AutomateHeaderPlugin)

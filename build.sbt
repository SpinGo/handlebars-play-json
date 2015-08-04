name := "handlebars-play-json"

organization := "com.spingo"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.11.7", "2.10.4")

version := "1.1.0-spingo"

resolvers ++= Seq(
  Classpaths.typesafeReleases,
  "Sonatype Releases"  at "http://oss.sonatype.org/content/repositories/releases",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "typesafe releases" at "http://repo.typesafe.com/typesafe/maven-releases/"
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.4.2",
  "com.gilt" %% "handlebars-scala" % "2.0.2-spingo",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

publishMavenStyle := true
publishTo := {
  val repo = if (version.value.trim.endsWith("SNAPSHOT")) "snapshots" else "releases"
  Some(repo at s"s3://spingo-oss/repositories/$repo")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

licenses := Seq("BSD-style" -> url("http://www.opensource.org/licenses/bsd-license.php"))

homepage := Some(url("https://github.com/SpinGo/handlebars-play-json"))

pomExtra := (
  <scm>
    <url>git@github.com:SpinGo/handlebars-play-json.git</url>
    <connection>scm:git:git@github.com:SpinGo/handlebars-play-json.git</connection>
  </scm>
  <developers>
    <developer>
      <id>timcharper</id>
      <name>Tim Harper</name>
      <url>http://github.com/timcharper</url>
      <organization>SpinGo</organization>
      <organizationUrl>http://www.spingo.com/</organizationUrl>
    </developer>
  </developers>)

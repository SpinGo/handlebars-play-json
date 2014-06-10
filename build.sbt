name := "handlebars-play-json"

organization := "com.spingo"

scalaVersion := "2.11.1"

crossScalaVersions := Seq("2.11.1", "2.10.4")

version := "0.3.1"

resolvers ++= Seq(
  Classpaths.typesafeReleases,
  "Sonatype Releases"  at "http://oss.sonatype.org/content/repositories/releases",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "typesafe releases" at "http://repo.typesafe.com/typesafe/maven-releases/"
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.3.0",
  "com.gilt" %% "handlebars" % "1.2.2-spingo",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test"
)

publishMavenStyle := true

// For publishing / testing locally
//publishTo := Some(Resolver.file("m2",  new File(Path.userHome.absolutePath+"/.m2/repository")))

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

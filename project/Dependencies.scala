import sbt._

object Dependencies {
  val circeVersion = "0.12.0-RC4"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
  lazy val scalacheck = "org.scalacheck" %% "scalacheck" % "1.14.0"
  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % circeVersion)
}

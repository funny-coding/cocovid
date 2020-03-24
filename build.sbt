name := "cocovid"

version := "0.1"

scalaVersion := "2.12.11"

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6")

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-encoding",
  "UTF-8",
  "-Xlint",
  "-Xverify",
  "-feature",
  "-Ypartial-unification",
  "-Xlint:-unused",
  "-language:_",
  "-Ylog-classpath"
)

val zioVersion   = "1.0.0-RC18-2"
val sparkVersion = "2.4.5"

libraryDependencies ++= Seq(
  // ZIO
  "dev.zio"           %% "zio"               % zioVersion,
  "dev.zio"           %% "zio-test"          % zioVersion % "test",
  "dev.zio"           %% "zio-test-sbt"      % zioVersion % "test",
  "dev.zio"           %% "zio-test-magnolia" % zioVersion % "test",
  "org.apache.spark"  %% "spark-sql"         % sparkVersion,
  "com.amazonaws"     % "aws-java-sdk"       % "1.11.749",
  "org.apache.hadoop" % "hadoop-aws"         % "3.2.1",
  "org.apache.hadoop" % "hadoop-common"      % "3.2.1"
)
testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))

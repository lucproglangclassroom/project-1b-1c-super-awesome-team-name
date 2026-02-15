name := "echotest-scala"

version := "0.4"

libraryDependencies ++= Seq(
  "com.lihaoyi"       %% "mainargs"        % "0.7.8",
  "com.github.sbt.junit" % "jupiter-interface" % "0.17.0" % Test, // required only for plain JUnit testing
  "org.scalatest"     %% "scalatest"       % "3.2.19"   % Test,
  "org.scalacheck"    %% "scalacheck"      % "1.19.0"   % Test,
  "org.scalatestplus" %% "scalacheck-1-18" % "3.2.19.0" % Test
)
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.5.3"

enablePlugins(JavaAppPackaging)

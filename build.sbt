name := """ach_case_tracker"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaCore,
  javaJdbc,
  javaWs,
  cache,
  evolutions,
  "org.webjars" %% "webjars-play" % "2.4.0-2",
  //play-bootstrap contains bootstrap, filters, and jquery as dependencies
  "com.adrianhurt" % "play-bootstrap3_2.11" % "0.4.5-P24",
  "com.loicdescotte.coffeebean" %% "html5tags" % "1.2.2"  // used for client side validation
)

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases",
  "Apache" at "http://repo1.maven.org/maven2/",
  Resolver.url("github repo for html5tags", url("http://loicdescotte.github.io/Play2-HTML5Tags/releases/"))(Resolver.ivyStylePatterns)
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

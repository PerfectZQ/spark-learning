name := "spark-learning"

version := "1.0"

scalaVersion := "2.11.8"

// 解决jar包冲突(deduplicate)问题
assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.last
  case PathList("javax", "activation", xs@_*) => MergeStrategy.last
  case PathList("org", "apache", xs@_*) => MergeStrategy.last
  case PathList("org", "w3c", xs@_*) => MergeStrategy.last
  case PathList("com", "google", xs@_*) => MergeStrategy.last
  case PathList("com", "codahale", xs@_*) => MergeStrategy.last
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case "log4j.properties" => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}


resolvers += "Cloudera Repository" at "https://repository.cloudera.com/content/repositories/releases/"
resolvers += "Spring Plugins Repository" at "http://repo.spring.io/plugins-release/"
// resolvers += "Maven Repository" at "https://mvnrepository.com/"
//                       organization         library        version    scope
libraryDependencies += "org.apache.camel" % "camel-test-spring" % "2.10.1" % "test" intransitive()
libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.11.8"
libraryDependencies += "com.google.guava" % "guava" % "16.0.1"
libraryDependencies += "commons-codec" % "commons-codec" % "1.10"
libraryDependencies += "commons-logging" % "commons-logging" % "1.2"
libraryDependencies += "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13"
libraryDependencies += "jline" % "jline" % "2.12.1"
libraryDependencies += "org.apache.zookeeper" % "zookeeper" % "3.4.5-cdh5.7.1"
libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.6.0-cdh5.7.1"
libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.6.0-cdh5.7.1"
libraryDependencies += "org.apache.hadoop" % "hadoop-auth" % "2.6.0-cdh5.7.1"
libraryDependencies += "org.apache.hadoop" % "hadoop-annotations" % "2.6.0-cdh5.7.1"
libraryDependencies += "org.apache.hadoop" % "hadoop-yarn-server-nodemanager" % "2.6.0-cdh5.7.1"
libraryDependencies += "org.apache.hbase" % "hbase" % "1.2.0-cdh5.7.1"
libraryDependencies += "org.apache.hbase" % "hbase-client" % "1.2.0-cdh5.7.1"
libraryDependencies += "org.apache.hbase" % "hbase-common" % "1.2.0-cdh5.7.1"
libraryDependencies += "org.apache.hbase" % "hbase-server" % "1.2.0-cdh5.7.1"
libraryDependencies += "org.mongodb" % "casbah_2.11" % "3.1.1"
libraryDependencies += "com.typesafe.scala-logging" % "scala-logging-slf4j_2.11" % "2.1.2" % "provided"
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.1.0"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.1.0"
libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.1.0"
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.38"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.5.2"
libraryDependencies += "com.typesafe.akka" % "akka-remote_2.11" % "2.5.2"
libraryDependencies += "com.typesafe.akka" % "akka-testkit_2.11" % "2.5.2"
//libraryDependencies += "javax.servlet" % "servlet-api" % "3.0.1" % "provided"
//libraryDependencies += "org.apache.hbase" % "hbase-spark" % "2.0.0-SNAPSHOT"
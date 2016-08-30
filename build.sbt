name := "spark-fu"

version := "1.0"

scalaVersion := "2.10.6"

resolvers += "Spark Packages Repo" at "https://dl.bintray.com/spark-packages/maven"
resolvers += Resolver.mavenLocal
//resolvers += "cloudera-repo-releases" at "https://repository.cloudera.com/artifactory/repo"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.0.0",
  "org.apache.spark" %% "spark-sql" % "2.0.0",
  "org.apache.spark" %% "spark-mllib" % "2.0.0",
  "org.scalanlp" %% "breeze" % "0.11.2",
  "org.scalanlp" %% "breeze-macros" % "0.11.2",
  "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.0-M1"
//  "com.cloudera.sparkts" % "sparkts" % "0.3.0"
//  "com.cloudera.sparkts" % "sparktimeseries" % "0.1.0"
)
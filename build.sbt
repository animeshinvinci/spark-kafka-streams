name := "spark-kafka-streams"

version := "1.0"

scalaVersion := "2.11.3"


libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.10.0.0"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.2.0"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.2.0"



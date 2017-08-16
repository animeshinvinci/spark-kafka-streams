package io.poc.kafka

import java.util

import org.apache.kafka.clients.producer.ProducerConfig

import org.apache.spark.{ SparkConf, SparkContext}


object KafkaSparkUtils {



  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setAppName("spark-kafka-streams")
      .setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val kafkaConfig = getKafkaConfig("localhost:9092")
    val kafkaSink = sc.broadcast(KafkaSink(kafkaConfig))

    val rddcsv =  sc.textFile("data.csv")
    rddcsv.cache()

    rddcsv.foreachPartition(part =>
      part.foreach(x =>
        //have logic to convert x to json String
        kafkaSink.value.send("mytopic",x)
        //println(x)
      ))
  }

    private def getKafkaConfig(kafkaBootstrap: String): util.HashMap[String, Object] = {
    val kafkaConfig = new util.HashMap[String, Object]
    kafkaConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrap)
    kafkaConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    kafkaConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    kafkaConfig
  }

}

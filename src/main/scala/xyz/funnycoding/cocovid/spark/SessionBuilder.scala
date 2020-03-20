package xyz.funnycoding.cocovid.spark

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import zio.{ Layer, ZLayer }

object SessionBuilder {
  trait Service {
    val sessionBuilder: SparkSession.Builder
  }

  val live: Layer[Nothing, SparkSessionBuilder] = ZLayer.succeed(
    new Service {
      val sessionBuilder: SparkSession.Builder = {
        val conf =
          new SparkConf()
            .set("spark.ui.enabled", "false")
            .set("spark.driver.host", "localhost")

        SparkSession.builder
          .config(conf)
          .master("local")
          .appName("cocovid")
      }
    }
  )
}

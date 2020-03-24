package xyz.funnycoding.cocovid

import org.apache.spark.SparkConf
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{ Dataset, Encoders, SparkSession }
import xyz.funnycoding.cocovid.model.DailyReport

object Main extends App {

  val conf = new SparkConf()
    .set("spark.ui.enabled", "false")
    .set("spark.driver.host", "localhost")

  private val sessionbuilder: SparkSession.Builder = SparkSession.builder
    .config(conf)
    .master("local")
    .appName("cocovid")

  val session: SparkSession = sessionbuilder.getOrCreate()
  session.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "s3.us-east-1.amazonaws.com")
  val schema = Encoders.product[DailyReport].schema
  private val frame = session.read
    .option("header", "true")
    .option("delimiter", ",")
    .schema(schema)
    .csv("s3a://cocovid19/*.csv")

  frame.printSchema()

  private val value: Dataset[DailyReport] = frame.as[DailyReport](Encoders.product[DailyReport])
  value.groupBy("country").agg(max("deaths")).foreach(x => println(x))
}

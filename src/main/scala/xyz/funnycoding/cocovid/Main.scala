package xyz.funnycoding.cocovid

import org.apache.spark.SparkConf
import org.apache.spark.sql.{ DataFrame, Dataset, SparkSession }
import xyz.funnycoding.cocovid.Cocovid.getClass
import xyz.funnycoding.cocovid.model.DailyReport
import scala.reflect.runtime.universe.TypeTag
import org.apache.spark.sql.{ Dataset, Encoders, SparkSession }

object Main extends App {

  val conf = new SparkConf()
    .set("spark.ui.enabled", "false")
    .set("spark.driver.host", "localhost")

  private val sessionbuilder: SparkSession.Builder = SparkSession.builder
    .config(conf)
    .master("local")
    .appName("cocovid")

  val session: SparkSession = sessionbuilder.getOrCreate()
  private val frame: DataFrame = session.read
    .option("header", "true")
    .option("inferSchema", "true")
    .option("delimiter", ",")
    .csv("src/main/resources/*.csv")

//    .as[DailyReport](Encoders.product[DailyReport])

  frame.printSchema()

  private val value: Dataset[DailyReport] = frame.as[DailyReport](Encoders.product[DailyReport])
  value.foreach(x => println(x))
}

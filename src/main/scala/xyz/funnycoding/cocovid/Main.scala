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
    .format("csv")
    .option("header", "true")
    .option("inferSchema", "true")
    .option("delimiter", ",")
    .csv(getClass.getResource("/19-03-20.csv").getPath)

//    .as[DailyReport](Encoders.product[DailyReport])

  frame.printSchema()

  private val value: Dataset[DailyReport] = frame.as[DailyReport](Encoders.product[DailyReport])
  value.foreach(x => println(x))
}

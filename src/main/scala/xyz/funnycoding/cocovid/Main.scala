package xyz.funnycoding.cocovid

import org.apache.spark.SparkConf
import org.apache.spark.sql.{ DataFrame, Dataset, SparkSession }
import xyz.funnycoding.cocovid.Cocovid.getClass
import xyz.funnycoding.cocovid.model.DailyReport
import scala.reflect.runtime.universe.TypeTag
import org.apache.spark.sql.{ Dataset, Encoders, SparkSession }

object Main extends App {

  val accessKeyId     = System.getenv("AWS_ACCESS_KEY_ID")
  val secretAccessKey = System.getenv("AWS_SECRET_ACCESS_KEY")

  val conf = new SparkConf()
    .set("spark.ui.enabled", "false")
    .set("spark.driver.host", "localhost")

  private val sessionbuilder: SparkSession.Builder = SparkSession.builder
    .config(conf)
    .master("local")
    .appName("cocovid")

  val session: SparkSession = sessionbuilder.getOrCreate()

  session.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", secretAccessKey)
  session.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", accessKeyId)
  session.sparkContext.hadoopConfiguration.set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
  session.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "s3.eu-west-3.amazonaws.com")
  /*private val frame: DataFrame = session.read
    .option("header", "true")
    .option("inferSchema", "true")
    .option("delimiter", ",")
    .csv("s3n://cocovid19/03-22-2020.csv")*/
  println(session.sparkContext.textFile("s3a://cocovid19/03-22-2020.csv").count())
//    .as[DailyReport](Encoders.product[DailyReport])

  /* frame.printSchema()

  private val value: Dataset[DailyReport] = frame.as[DailyReport](Encoders.product[DailyReport])
  value.foreach(x => println(x))*/
}

package xyz.funnycoding.cocovid.read

import scala.reflect.runtime.universe.TypeTag
import org.apache.spark.sql.{ Dataset, Encoders, SparkSession }
import zio.{ Layer, RIO, Task, ZLayer }

object ReadCsv {

  trait Service {
    def readCsv[A <: Product](spark: SparkSession, path: String)(
      implicit
      typeTag: TypeTag[A]
    ): Task[Dataset[A]]
  }

  val live: Layer[Nothing, ReadCsv] = ZLayer.succeed(
    new Service {
      def readCsv[A <: Product](spark: SparkSession, path: String)(
        implicit
        typeTag: TypeTag[A]
      ): RIO[Any, Dataset[A]] =
        RIO.effect(
          spark.read
            .format("csv")
            .option("header", "true")
            .option("inferSchema", "true")
            .option("delimiter", ",")
            .csv(path)
            .as[A](Encoders.product[A])
        )
    }
  )
}

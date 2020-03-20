package xyz.funnycoding.cocovid

import scala.reflect.runtime.universe.TypeTag
import org.apache.spark.sql.{ Dataset, SparkSession }
import zio.{ Has, RIO }

package object read {
  type ReadCsv = Has[ReadCsv.Service]

  def readCsv[A <: Product](spark: SparkSession, path: String)(
    implicit
    typeTag: TypeTag[A]
  ): RIO[ReadCsv, Dataset[A]] =
    RIO.accessM(_.get.readCsv(spark, path))
}

package xyz.funnycoding.cocovid

import zio._
import zio.console._
import spark._
import read._
import model._

object Cocovid extends App {

  type AppEnv = AppSparkSession with ReadCsv

  val appEnv: ZLayer[Any, Throwable, AppEnv] = (SessionBuilder.live >>> sparkSessionZLayer) ++ ReadCsv.live
  val program: ZIO[AppEnv with Console, Throwable, Unit] =
    for {
      spark       <- sparkSession
      _           <- putStrLn("Testing......")
      path        <- ZIO.effect(getClass.getResource("/19-03-20.csv").getPath)
      summaryData <- readCsv[DailyReport](spark, path)
      _           <- putStrLn("Printing summary to console......")
      _           <- ZIO.foreach_(summaryData.collect())(p => putStrLn(p.toString))
    } yield ()

  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] =
    program
      .provideCustomLayer(appEnv)
      .foldM(
        _ => putStrLn(s"Job failed!") *> ZIO.succeed(1),
        _ => putStrLn("Job completed!") *> ZIO.succeed(0)
      )
}

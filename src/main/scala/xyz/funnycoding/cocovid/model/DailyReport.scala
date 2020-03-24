package xyz.funnycoding.cocovid.model

case class DailyReport(
  province: Option[String],
  country: String,
  lastUpdate: String,
  confirmed: Option[Int],
  deaths: Option[Int],
  recovered: Option[Int],
  latitude: Option[Double],
  longitude: Option[Double]
)

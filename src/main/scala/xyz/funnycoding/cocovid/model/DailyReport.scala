package xyz.funnycoding.cocovid.model

case class DailyReport(
  province: Option[String],
  country: String,
  lastUpdate: String,
  confirmed: Int,
  deaths: Int,
  recovered: Int,
  latitude: Double,
  longitude: Double
)

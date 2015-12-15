import java.sql.Date

/**
  * Created by Raslu on 15.12.2015.
  */
case class Crime(val cdatetime: Date, val address: String, val district: Int, val beat: String,
                 val grid: Int, val crimedescr: String, val ucr_ncic_code: Int, val latitude: Double, val longitude: Double) {

}
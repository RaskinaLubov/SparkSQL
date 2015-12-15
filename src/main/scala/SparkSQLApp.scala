/**
  * Created by Raslu on 15.12.2015.
  */

import java.sql.Date
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime

import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLApp extends App{
  val sparkConf = new SparkConf().setAppName("Second Homework").setMaster("local[2]")
  val sc = new SparkContext(sparkConf)

  val sqlContext = new org.apache.spark.sql.SQLContext(sc)

  val raw_crime = sc.textFile("C:\\Users\\Raslu\\IdeaProjects\\SparkSQL\\src\\SacramentocrimeJanuary2006.csv")

  val d = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm")

  val sacr_crimes = raw_crime.map(_.split(",")).filter(_(0)!="cdatetime").map(r => Crime(
    new Date(DateTime.parse(r(0), d).toDate.getTime),
    r(1),
    r(2).toInt,
    r(3),
    r(4).toInt,r(5),
    r(6).toInt,
    r(7).toDouble,
    r(8).toDouble))

  import sqlContext.implicits._
  val sacr_crimes_df = sacr_crimes.toDF()

  sacr_crimes_df.registerTempTable("crime")

  val query = """
                 select district,count(*) as crime_in_distr from crime
                 group by district
              """

  import sqlContext._
  sql(query).show()
  sc.stop()
}
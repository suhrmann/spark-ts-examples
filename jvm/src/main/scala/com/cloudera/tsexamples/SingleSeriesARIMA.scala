package com.cloudera.tsexamples

import com.cloudera.sparkts.models.ARIMA
import org.apache.spark.mllib.linalg._
import org.apache.spark.{SparkConf, SparkContext}

/**
 * An example showcasing the use of ARIMA in a non-distributed context.
 */
object SingleSeriesARIMA {

  val EXAMPLE_DATA = "../data/R_ARIMA_DataSet1.csv"
  // val EXAMPLE_DATA = "s3://<S3 Bucket ID>/<path to>/R_ARIMA_DataSet1.csv"

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark-TS Single Series ARIMA Example").setMaster("local")
    conf.set("spark.io.compression.codec", "org.apache.spark.io.LZ4CompressionCodec")
    val sc = new SparkContext(conf)

    // The dataset is sampled from an ARIMA(1, 0, 1) model generated in R.
    val lines: Array[String] = sc.textFile(EXAMPLE_DATA).collect()
    val values: Array[Double] = lines.map(_.toDouble)
    val ts: Vector = Vectors.dense(values)
    val arimaModel = ARIMA.fitModel(1, 0, 1, ts)
    println("coefficients: " + arimaModel.coefficients.mkString(","))
    val forecast = arimaModel.forecast(ts, 20)
    println("forecast of next 20 observations: " + forecast.toArray.mkString(","))
  }
}

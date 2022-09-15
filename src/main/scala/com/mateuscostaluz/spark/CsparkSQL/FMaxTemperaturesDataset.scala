package com.mateuscostaluz.spark.CsparkSQL

import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{FloatType, IntegerType, StringType, StructType}

/** Find the maximum temperature by weather station */
object FMaxTemperaturesDataset {

  case class Temperature(stationID: String, date: Int, measure_type: String, temperature: Float)

  /** Our main function where the action happens */
  def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkSession using every core of the local machine
    val spark = SparkSession
      .builder
      .appName("MaxTemperatures")
      .master("local[*]")
      .getOrCreate()

    val temperatureSchema = new StructType()
      .add("stationID", StringType, nullable = true)
      .add("date", IntegerType, nullable = true)
      .add("measure_type", StringType, nullable = true)
      .add("temperature", FloatType, nullable = true)

    // Read the file as dataset
    import spark.implicits._
    val ds = spark.read
      .schema(temperatureSchema)
      .csv("data/1800.csv")
      .as[Temperature]
    
    // Filter out all but TMAX entries
    val maxTemps = ds.filter($"measure_type" === "TMAX")
    
    // Select only stationID and temperature)
    val stationTemps = maxTemps.select("stationID", "temperature")
    
    // Aggregate to find maximum temperature for every station
    val maxTempsByStation = stationTemps.groupBy("stationID").max("temperature")

    // Convert temperature to fahrenheit and sort the dataset
    val maxTempsByStationF = maxTempsByStation
      .withColumn("temperature", round($"max(temperature)" * 0.1f * (9.0f / 5.0f) + 32.0f, 2))
      .select("stationID", "temperature").sort("temperature")

    // Collect, format, and print the results
    val results = maxTempsByStationF.collect()
    
    for (result <- results) {
       val station = result(0)
       val temp = result(1).asInstanceOf[Float]
       val formattedTemp = f"$temp%.2f F"
       println(s"$station maximum temperature: $formattedTemp")
    }
  }
}

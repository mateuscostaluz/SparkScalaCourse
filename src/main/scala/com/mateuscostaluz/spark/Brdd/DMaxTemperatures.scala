package com.mateuscostaluz.spark.Brdd

import org.apache.log4j._
import org.apache.spark._

import scala.math.max

/** Find the minimum temperature by weather station */
object DMaxTemperatures extends App {
/** Our main function where the action happens */

  def parseLine(line:String): (String, String, Float) = {
    val fields = line.split(",")
    val stationID = fields(0)
    val entryType = fields(2)
    val temperature = fields(3).toFloat * 0.1f * (9.0f / 5.0f) + 32.0f
    (stationID, entryType, temperature)
  }

  // Set the log level to only print errors
  Logger.getLogger("org").setLevel(Level.ERROR)

  // Create a SparkContext using every core of the local machine
  val sc = new SparkContext("local[*]", "MaxTemperatures")

  // Read each line of input data
  val lines = sc.textFile("data/1800.csv")

  // Convert to (stationID, entryType, temperature) tuples
  val parsedLines = lines.map(parseLine)

  // Filtrando apenas linhas com TMAX
  val maxTemps = parsedLines.filter(x => x._2 == "TMAX")

  // Convertendo para (stationID, temperature)
  val stationTemps = maxTemps.map(x => (x._1, x._3.toFloat))

  // Reduz por stationID mantendo a temperatura máxima encontrada
  val maxTempsByStation = stationTemps.reduceByKey((x,y) => max(x,y))

  // Coleta os resultados para a impressão
  val maxResults = maxTempsByStation.collect()

  for (maxResult <- maxResults.sorted) {
    val station = maxResult._1
    val temp = maxResult._2
    val formattedTemp = f"$temp%.2f F"
    println(s"$station temperatura máxima: $formattedTemp")
  }

}

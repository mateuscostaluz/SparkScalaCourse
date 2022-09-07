package com.mateuscostaluz.spark.CsparkSQL

import org.apache.log4j._
import org.apache.spark.sql._

import java.io.{FileNotFoundException, FileReader}

object ASparkSQLDataset {

  case class Person(id:Int, name:String, age:Int, friends:Int)

  /** Our main function where the action happens */
  def main(args: Array[String]) {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Use SparkSession interface
    val spark = SparkSession
      .builder
      .appName("SparkSQL")
      .master("local[*]")
      .getOrCreate()

    try {

      // Load each line of the source data into an Dataset
      import spark.implicits._
      val schemaPeople = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .csv("data/fakefriends.csv")
        // \/ o esquema será inferido se comentado
        .as[Person]

      println(schemaPeople.getClass)

      schemaPeople.printSchema()

      schemaPeople.createOrReplaceTempView("people")

      val teenagers = spark.sql("SELECT * FROM people WHERE age >= 13 AND age <= 19")

      val results = teenagers.collect()

      results.foreach(println)

      val file = new FileReader("myfile.txt")

    } catch {

      case e: FileNotFoundException => e.printStackTrace()

    } finally {

      spark.stop()

      println("Closing the SparkSession...")

    }

  }

}

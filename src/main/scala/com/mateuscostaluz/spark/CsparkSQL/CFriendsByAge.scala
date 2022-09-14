package com.mateuscostaluz.spark.CsparkSQL

import org.apache.log4j._
import org.apache.spark.sql._
import org.apache.spark.sql.functions.{avg, col, round}

import java.io.FileNotFoundException

/** Our main function where the action happens */
object CFriendsByAge extends App {

  case class FakeFriends(id: Int, name: String, age: Int, friends: Int)

  // Set the log level to only print errors
  Logger.getLogger("org").setLevel(Level.ERROR)

  // Use new SparkSession interface in Spark 2.0
  val spark = SparkSession
    .builder
    .appName("SparkSQL")
    .master("local[*]")
    .getOrCreate()

  try {

    // Convert our csv file to a DataSet, using our Person case class to infer the schema.
    import spark.implicits._
    val people = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/fakefriends.csv")
      .as[FakeFriends]

    val friendsByAge = people.select("age", "friends")

    friendsByAge
      .groupBy("age")
      .avg("friends")
      .show()

    friendsByAge
      .groupBy("age")
      .avg("friends")
      .sort("age")
      .show()

    friendsByAge
      .groupBy("age")
      .agg(
        round(avg("friends"), 2)
      )
      .sort("age")
      .show()

    friendsByAge
      .groupBy("age")
      .agg(
        round(avg("friends"), 2).alias("friends_avg")
      )
      .sort("age")
      .show()

    // \/ ... testing the catch with not found file
    // val file = new FileReader("myfile.txt")

  } catch {

    case e: FileNotFoundException => e.printStackTrace()

  } finally {

    spark.stop()

    println("Closing the SparkSession...")

  }

}

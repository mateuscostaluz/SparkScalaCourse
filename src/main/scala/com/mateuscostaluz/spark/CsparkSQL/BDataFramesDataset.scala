package com.mateuscostaluz.spark.CsparkSQL

import org.apache.log4j._
import org.apache.spark.sql._

import java.io.{FileNotFoundException, FileReader}

/** Our main function where the action happens */
object BDataFramesDataset extends App {

  case class Person(id: Int, name: String, age: Int, friends: Int)

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
      .as[Person]

    // There are lots of other ways to make a DataFrame.
    // For example, spark.read.json("json file path")
    // or sqlContext.table("Hive table name")

    println("Here is our inferred schema:")
    people.printSchema()

    println("Let's select the name column:")
    people.select("name").show()

    println("Filter out anyone over 21:")
    people.filter(people("age") < 21).show()

    println("Group by age:")
    people.groupBy("age").count().show()

    println("Make everyone 10 years older:")
    people.select(people("name"), (people("age") + 10) as "age plus 10").show()

    val file = new FileReader("myfile.txt")

  } catch {

    case e: FileNotFoundException => e.printStackTrace()

  } finally {

    spark.stop()

    println("Closing the SparkSession...")

  }

}

package com.mateuscostaluz.spark.Brdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object HTotalAmountSpentByCustomer {

  def extractCustomerPricePairs(line: String): (Int, Float) = {
    val fields = line.split(",")
    (fields(0).toInt, fields(2).toFloat)
  }

  def main(args: Array[String]): Unit = {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkContext using every core of the local machine
    val sc = new SparkContext("local[*]", "TotalAmountSpentByCustomer")

    val input = sc.textFile("data/customer-orders.csv")

    val mappedInput = input.map(extractCustomerPricePairs)

    val totalByCustomer = mappedInput.reduceByKey((x, y) => x + y)

    val flipped = totalByCustomer.map(x => (x._2, x._1))

    val results = flipped.sortByKey(false).collect()

    for (result <- results) {
      println(f"${result._2} spent $$ ${result._1}%.2f")
    }

  }

}

package com.mateuscostaluz.spark.CsparkSQL

import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DoubleType, IntegerType, StructType}

/** Find the maximum temperature by weather station */
object GTotalAmountSpentByCustomerDataset {

  case class CustomerOrders(userID: Int, itemID: Int, price_paid: Double)

  /** Our main function where the action happens */
  def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkSession using every core of the local machine
    val spark = SparkSession
      .builder
      .appName("TotalAmountSpentByCustomerDataset")
      .master("local[*]")
      .getOrCreate()

    val customerOrdersSchema = new StructType()
      .add("userID", IntegerType, nullable = true)
      .add("itemID", IntegerType, nullable = true)
      .add("price_paid", DoubleType, nullable = true)

    // Read the file as dataset
    import spark.implicits._
    val ds = spark.read
      .schema(customerOrdersSchema)
      .csv("data/customer-orders.csv")
      .as[CustomerOrders]
    
    // Select only userID and price_paid)
    val customerOrders = ds.select("userID", "price_paid")
    
    // Aggregate to find price paid sum for every user
    val pricePaidByUserID = customerOrders
      .groupBy("userID")
      .agg(round(sum("price_paid"), 2)
          .alias("total_spent"))

    // Sort by userID
    val sortedAndRoundedPricePaidByUserID = pricePaidByUserID.sort("total_spent")

    // Print the results
    sortedAndRoundedPricePaidByUserID.show(sortedAndRoundedPricePaidByUserID.count.toInt)

    // Collect and print the results
    val results = sortedAndRoundedPricePaidByUserID.collect()

    for (result <- results) {
      val userID = result(0)
      val pricePaid = result(1)
      println(s"$userID spent $$ $pricePaid")
    }
  }
}

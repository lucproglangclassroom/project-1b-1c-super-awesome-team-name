package edu.luc.cs.cs371.echo
package main

import mainargs._

object Main:

  @main def wordCloudDemo(): Unit =
    // Create an observer that prints word cloud updates
    val observer = new StatsObserver
     

  val engine = new TopWordsEngine(
    howMany = 10, //Top x words over all
    minLength = 4, //Track only words with 4 or more letters
    windowSize = 100, //Track last x words
    observer = observer
  )
end Main

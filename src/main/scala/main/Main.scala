package edu.luc.cs.cs371.echo
package main

import mainargs._
import scala.language.unsafeNulls
import com.typesafe.scalalogging.Logger

object Main:

  private val logger = Logger(getClass.getName)

  @main def topwords(
      @arg(name = "cloud-size", short = 'c', doc = "size of the word cloud (number of words to show)")
      cloudSize: Int = 10,
      @arg(name = "length-at-least", short = 'l', doc = "minimum length of a word to be considered")
      lengthAtLeast: Int = 6,
      @arg(name = "window-size", short = 'w', doc = "size of moving window of recent words")
      windowSize: Int = 1000
  ): Unit =
  //implement third party library for logging
  logger.debug(
    s"howMany=$cloudSize minLength=$lengthAtLeast windowSize=$windowSize"
  )

   // Create an observer that prints word cloud updates
    val observer = new ConsoleObserver
     
    val engine = new TopWordsEngine(
      howMany = cloudSize,
      minLength = lengthAtLeast,
      windowSize = windowSize,
      observer = observer
    )

    // Read words from stdin
    val lines = scala.io.Source.stdin.getLines
    val words = lines.flatMap(_.split("(?U)[^\\p{Alpha}0-9']+").nn)

    // Process each word
    try
      for word <- words do
        engine.process(word)
        // Check for SIGPIPE on stdout
        if scala.sys.process.stdout.checkError() then
          sys.exit(1)
    catch
      case _: java.io.IOException => // Handle EOF gracefully
        sys.exit(0)

  // External entry point into Scala application
  def main(args: Array[String]): Unit =
    ParserForMethods(this).runOrExit(args.toIndexedSeq)
    ()
end Main

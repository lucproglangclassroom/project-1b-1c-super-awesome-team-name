package edu.luc.cs.cs371.echo

import com.typesafe.scalalogging.Logger
import main.{ConsoleObserver, TopWordsEngine}

object Main:

  private val logger = Logger(this.getClass)

  /** 
   * Entry point for processing top words from stdin.
   *
   * @param cloudSize Number of words to show in the word cloud
   * @param lengthAtLeast Minimum length of a word to be considered
   * @param windowSize Size of moving window of recent words
   */
  @main def topwords(
      cloudSize: Int = 10,
      lengthAtLeast: Int = 6,
      windowSize: Int = 1000
  ): Unit =
    // Log the parameters
    logger.debug(
      s"howMany=$cloudSize minLength=$lengthAtLeast windowSize=$windowSize"
    )

    // Create an observer that prints word cloud updates
    val observer = new ConsoleObserver

    // Create the TopWordsEngine
    val engine = new TopWordsEngine(
      howMany = cloudSize,
      minLength = lengthAtLeast,
      windowSize = windowSize,
      observer = observer
    )

    // Read words from stdin
    val words = scala.io.Source.stdin.getLines()
      .flatMap(_.split("(?U)[^\\p{Alpha}0-9']+").nn)

    // Process each word
    try
      for word <- words do engine.process(word)
    catch
      case _: java.io.IOException => 
        // Handle EOF gracefully
        sys.exit(0)

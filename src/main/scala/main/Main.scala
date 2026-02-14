package edu.luc.cs.cs371.echo.main


import com.typesafe.scalalogging.Logger
import edu.luc.cs.cs371.echo.main.ConsoleObserver
import edu.luc.cs.cs371.echo.main.TopWordsEngine
import mainargs._

object Main:

  private val logger = Logger(this.getClass)

  /** 
   * Entry point for processing top words from stdin.
   *
   * @param cloudSize Number of words to show in the word cloud
   * @param lengthAtLeast Minimum length of a word to be considered
   * @param windowSize Size of moving window of recent words
   */
  @main def run(
      @arg(name = "cloud-size", short = 'c')
      cloudSize: Int = 10,

      @arg(name = "length", short = 'l')
      lengthAtLeast: Int = 6,

      @arg(name = "window-size", short = 'w')
      windowSize: Int = 1000,

      @arg(name = "input-file", short = 'i')
      inputFile: String = ""
  ): Unit =
    // Log the parameters
    logger.info("=== STARTING APPLICATION ===")
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


    if inputFile.nonEmpty then
      val source = scala.io.Source.fromFile(inputFile)
      var wordCount = 0 // ← Add counter
      try
        source.getLines().flatMap(_.split("\\W+"))
          .foreach { word =>
            engine.process(word.toLowerCase)
            wordCount += 1
          }
      finally
        source.close()

      println(s"\n=== FINAL SUMMARY ===")
      println(s"Total words processed: $wordCount")
      println(s"Window size: $windowSize")
      println(s"Min length: $lengthAtLeast")
    else
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

  def main(args: Array[String]): Unit = ParserForMethods(this).runOrExit(args.toSeq) : Unit
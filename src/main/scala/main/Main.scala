package edu.luc.cs.cs371.echo.main

import com.typesafe.scalalogging.Logger
import impl.{ConsoleRenderer, FileInputHandler, StdinInputHandler}
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
    
    val initial = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = windowSize,
      minLength = lengthAtLeast,
      howMany = cloudSize
    )

    val processor = new ScanLeftProcessor()
    val renderer = new ConsoleRenderer()
    
    val inputHandler: InputHandler =
      if inputFile.nonEmpty then
        new FileInputHandler(inputFile)
      else
        new StdinInputHandler()

    val words = inputHandler.readWords()

    try
      processor.process(words, initial).foreach { state =>
        if !renderer.render(state) then
          // SIGPIPE or other output error detected
          sys.exit(1)
      }
    catch
      case _: java.io.IOException =>
        // Handle EOF gracefully
        sys.exit(0)

  def main(args: Array[String]): Unit = 
    ParserForMethods(this).runOrExit(args.toSeq)
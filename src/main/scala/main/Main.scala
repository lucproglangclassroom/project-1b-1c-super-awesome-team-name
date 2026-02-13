package main

import mainargs.*

import scala.language.unsafeNulls

object Main:


    System.err.println("Starting process")
    // Create an observer that prints word cloud updates
    @main def run(
                   @arg(name = "cloud-size", short = 'c', doc = "size of the word cloud (number of words to show)")
                   cloudSize: Int = 10,

                   @arg(name = "length-at-least", short = 'l', doc = "minimum length of a word to be considered")
                   lengthAtLeast: Int = 5,

                   @arg(name = "window-size", short = 'w', doc = "size of moving window of recent words")
                   windowSize: Int = 1000,

                   @arg(name = "input-file", short = 'i', doc = "path to input text file")
                   inputFile: String = ""
                 ): Unit =

      val observer = new ConsoleObserver
      val engine = new TopWordsEngine(
        howMany = cloudSize,
        minLength = lengthAtLeast,
        windowSize = windowSize,
        observer = observer
      )


      if inputFile.nonEmpty then
        // Read from file
        val source = scala.io.Source.fromFile(inputFile)
        try
          source.getLines().flatMap(_.split("\\W+"))
            .foreach(word => engine.process(word.toLowerCase))
        finally
          source.close()
      else
        // Read from stdin
        println("Enter text (Ctrl+D to end):")
        scala.io.Source.stdin.getLines()
          .flatMap(_.split("\\W+"))
          .foreach(word => engine.process(word.toLowerCase))


  // External entry point into Scala application
    def main(args: Array[String]): Unit =
      ParserForMethods(this).runOrExit(args.toIndexedSeq)
      ()

end Main

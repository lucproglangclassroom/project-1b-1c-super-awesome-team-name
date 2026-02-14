package edu.luc.cs.cs371.echo.main

/**
 * A concrete implementation of StatsObserver that prints word statistics
 * to the console in the required format: w1: f1 w2: f2 ... whowmany: fhowmany
 */
class ConsoleObserver extends StatsObserver:
  override def update(stats: WordStats): Unit =
    val output = stats.topWords.map { case (word, freq) => s"$word: $freq" }.mkString(" ")
    println(output)


end ConsoleObserver
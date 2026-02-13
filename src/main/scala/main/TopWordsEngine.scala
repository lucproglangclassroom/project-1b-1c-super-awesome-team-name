package edu.luc.cs.cs371.echo.main

import scala.collection.mutable
/*
  Maintains a moving window of recent words and tracks 
  which ones appear most frequently in that window
 */
class TopWordsEngine(
                      howMany: Int,
                      minLength: Int,
                      windowSize: Int,
                      observer: StatsObserver
                    ):

  private val window = mutable.Queue[String]()
  private val freq = mutable.Map[String, Int]()

  def process(word: String): Unit =
    if word.length >= minLength then
      window.enqueue(word)
      freq(word) = freq.getOrElse(word, 0) + 1

      if window.size > windowSize then
        val old = window.dequeue()
        freq(old) -= 1
        if freq(old) == 0 then freq.remove(old)

      // Update observer after every word once window is full
      if window.size >= windowSize then
        val top =
          freq.toList
            .sortBy { case (_, c) => -c }
            .take(howMany)
        observer.update(WordStats(top))

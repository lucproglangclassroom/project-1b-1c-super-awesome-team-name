package main

/**
 * Represents statistics about the top words in a sliding window.
 * @param topWords A list of (word, frequency) pairs, sorted by frequency (descending)
 */
case class WordStats(topWords: List[(String, Int)])

/**
 * Trait for observing updates to word statistics.
 * Implementations can display, log, or process these statistics in various ways.
 */
trait StatsObserver:
  /**
   * Called when new word statistics are available.
   * @param stats The current word statistics
   */
  def update(stats: WordStats): Unit
end StatsObserver

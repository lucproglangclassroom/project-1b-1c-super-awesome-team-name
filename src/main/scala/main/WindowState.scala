package edu.luc.cs.cs371.echo.main

case class WindowState(
                        window: Vector[String],
                        freq: Map[String, Int],
                        windowSize: Int,
                        minLength: Int,
                        howMany: Int
                      ):

  def process(word: String): WindowState =
    if word.length < minLength then this
    else
      val newWindow = window :+ word
      val newFreq = freq.updated(word, freq.getOrElse(word, 0) + 1)

      if newWindow.size > windowSize then
        val old = newWindow.head
        val decremented = newFreq(old) - 1
        val trimmedFreq = if decremented == 0 then newFreq.removed(old) else newFreq.updated(old, decremented)
        WindowState(newWindow.tail, trimmedFreq, windowSize, minLength, howMany)
      else
        WindowState(newWindow, newFreq, windowSize, minLength, howMany)


  def topWords: List[(String, Int)] =
    freq.toList.sortBy(-_._2).take(howMany)

  def display: String =
    topWords.map { case (w, f) => s"$w: $f" }.mkString(" ")
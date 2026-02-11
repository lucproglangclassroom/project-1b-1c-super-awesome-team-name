package edu.luc.cs.cs371.echo
package main

import mainargs._

object Main:

  /*
  def main(args: Array[String]): Unit =
    val echos = Seq(new impl.SimpleEcho, new impl.DoubleEcho)
    for e <- echos do
      println(e.echo("hello"))
  **/
  
  def topWords(
    cloudSize: Int = 20
    lengthAtLeast: Int = 6
    windowSize: Int = 1000

  )
end Main

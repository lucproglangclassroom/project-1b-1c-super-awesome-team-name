package edu.luc.cs.cs371.echo.main

trait WordProcessor:
  def process(words: Iterator[String], initial: WindowState): Iterator[WindowState]

class ScanLeftProcessor extends WordProcessor:
  def process(words: Iterator[String], initial: WindowState): Iterator[WindowState] =
    words.scanLeft(initial)((state, word) => state.process(word)).drop(1)
package edu.luc.cs.cs371.echo.main

trait WordProcessor:
  def process(words: Iterator[String], initial: WindowState): Iterator[WindowState]

class ScanLeftProcessor extends WordProcessor:
  def process(words: Iterator[String], initial: WindowState): Iterator[WindowState] =
    words.scanLeft(initial)(_.process(_)).drop(1)
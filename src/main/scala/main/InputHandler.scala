package edu.luc.cs.cs371.echo.main

import java.io.FileNotFoundException

/**
 * Trait for handling input from various sources (stdin, files, etc.)
 */
trait InputHandler:
  /**
   * Returns an iterator of words from the input source
   */
  def readWords(): Iterator[String]
end InputHandler

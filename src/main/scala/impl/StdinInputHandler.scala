package impl

import edu.luc.cs.cs371.echo.main.InputHandler
import scala.io.Source

/**
 * Input handler that reads words from stdin
 */
class StdinInputHandler extends InputHandler:
  def readWords(): Iterator[String] =
    Source.stdin.getLines()
      .flatMap(_.split("(?U)[^\\p{Alpha}0-9']+").nn)
      .map(_.toLowerCase.nn)
      .filter(_.nonEmpty)
end StdinInputHandler

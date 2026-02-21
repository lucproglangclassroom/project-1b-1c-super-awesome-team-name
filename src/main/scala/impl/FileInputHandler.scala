package impl

import edu.luc.cs.cs371.echo.main.InputHandler
import scala.io.Source

/**
 * Input handler that reads words from a file
 */
class FileInputHandler(filePath: String) extends InputHandler:
  def readWords(): Iterator[String] =
    Source.fromFile(filePath).getLines()
      .flatMap(_.split("\\W+"))
      .map(_.toLowerCase)
      .filter(_.nonEmpty)
end FileInputHandler

package impl

import edu.luc.cs.cs371.echo.main.{OutputRenderer, WindowState}

/**
 * Renderer that outputs to console and handles SIGPIPE
 */
class ConsoleRenderer extends OutputRenderer:
  def render(state: WindowState): Boolean =
    if state.window.size >= state.windowSize then
      println(state.display)
      // Check for SIGPIPE or other output errors
      if scala.sys.process.stdout.checkError() then
        false
      else
        true
    else
      true
end ConsoleRenderer

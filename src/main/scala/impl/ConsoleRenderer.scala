package impl

import edu.luc.cs.cs371.echo.main.{OutputRenderer, WindowState}

/**
 * Renderer that outputs to console and handles SIGPIPE
 */
class ConsoleRenderer extends OutputRenderer:
  def render(state: WindowState): Boolean =
    if state.window.size >= state.windowSize then
      try
        Console.println(state.display)
        // Check for SIGPIPE or other output errors
        !Console.out.checkError()
      catch
        case _: java.io.IOException =>
          false
    else
      true
end ConsoleRenderer

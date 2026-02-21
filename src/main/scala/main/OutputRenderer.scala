package edu.luc.cs.cs371.echo.main

/**
 * Trait for rendering output (word cloud display)
 */
trait OutputRenderer:
  /**
   * Renders the current window state to output
   * @param state The current window state
   * @return true if output was successful, false if there was an error (e.g., SIGPIPE)
   */
  def render(state: WindowState): Boolean
end OutputRenderer

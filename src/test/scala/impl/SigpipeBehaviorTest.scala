package impl

import edu.luc.cs.cs371.echo.main.WindowState
import org.scalatest.funsuite.AnyFunSuite

import java.io.{IOException, OutputStream, PrintStream}

class SigpipeBehaviorTest extends AnyFunSuite:

  test("ConsoleRenderer reports output failure when stdout is broken (SIGPIPE scenario)"):
    val failingOut = new PrintStream(
      new OutputStream:
        override def write(b: Int): Unit =
          throw new IOException("Broken pipe")
      ,
      true
    )

    val state = WindowState(
      window = Vector("hello"),
      freq = Map("hello" -> 1),
      windowSize = 1,
      minLength = 1,
      howMany = 1
    )

    val renderer = new ConsoleRenderer()
    val ok = Console.withOut(failingOut) {
      renderer.render(state)
    }

    assert(!ok)
    failingOut.close()

end SigpipeBehaviorTest

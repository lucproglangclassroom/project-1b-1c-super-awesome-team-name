package impl

import edu.luc.cs.cs371.echo.main.{ScanLeftProcessor, WindowState}
import org.scalatest.funsuite.AnyFunSuite

/**
 * Tests for the functional processor implementation
 */
class FunctionalProcessorTest extends AnyFunSuite:

  test("ScanLeftProcessor processes empty iterator"):
    val initial = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = 10,
      minLength = 1,
      howMany = 5
    )

    val processor = new ScanLeftProcessor()
    val words = Iterator.empty
    val states = processor.process(words, initial).toList
    
    assert(states.isEmpty)

  test("ScanLeftProcessor maintains immutability"):
    val initial = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = 3,
      minLength = 1,
      howMany = 5
    )

    val processor = new ScanLeftProcessor()
    val words = Iterator("one", "two", "three")
    val states = processor.process(words, initial).toList
    
    // Original state should be unchanged
    assert(initial.window.isEmpty)
    assert(initial.freq.isEmpty)
    
    // Each state should be a new instance (different objects)
    assert(!(states(0) eq initial))
    assert(!(states(1) eq states(0)))
    assert(!(states(2) eq states(1)))

  test("ScanLeftProcessor handles window overflow correctly"):
    val initial = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = 2,
      minLength = 1,
      howMany = 5
    )

    val processor = new ScanLeftProcessor()
    val words = Iterator("a", "b", "c", "d")
    val states = processor.process(words, initial).toList
    
    // After processing 4 words with window size 2, last state should have ["c", "d"]
    val lastState = states.last
    assert(lastState.window.size == 2)
    assert(lastState.window.contains("c"))
    assert(lastState.window.contains("d"))
    assert(!lastState.window.contains("a"))
    assert(!lastState.window.contains("b"))

end FunctionalProcessorTest

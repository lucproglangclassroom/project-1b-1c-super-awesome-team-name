package impl

import edu.luc.cs.cs371.echo.main.{ScanLeftProcessor, WindowState}
import org.scalatest.funsuite.AnyFunSuite

/**
 * Tests for interactive behavior - ensuring that input triggers immediate response
 */
class InteractiveBehaviorTest extends AnyFunSuite:

  test("processes words incrementally with scanLeft"):
    val initial = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = 3,
      minLength = 1,
      howMany = 2
    )

    val processor = new ScanLeftProcessor()
    val words = Iterator("one", "two", "three", "four", "five")
    
    val states = processor.process(words, initial).toList
    
    // Should produce one state per word
    assert(states.length == 5)
    
    // First state should have one word
    assert(states(0).window.size == 1)
    assert(states(0).window.contains("one"))
    
    // Third state should have full window
    assert(states(2).window.size == 3)
    assert(states(2).window.contains("three"))
    
    // Last state should have window with last 3 words
    assert(states(4).window.size == 3)
    assert(states(4).window.contains("five"))
    assert(!states(4).window.contains("one"))

  test("scanLeft produces states lazily"):
    val initial = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = 100,
      minLength = 1,
      howMany = 10
    )

    val processor = new ScanLeftProcessor()
    // Create a large iterator but only take first few states
    val words = Iterator.from(1).map(_.toString).take(1000)
    val states = processor.process(words, initial).take(5).toList
    
    // Should only process first 5 words, not all 1000
    assert(states.length == 5)
    assert(states.last.window.size == 5)

  test("each word produces exactly one state"):
    val initial = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = 10,
      minLength = 1,
      howMany = 5
    )

    val processor = new ScanLeftProcessor()
    val words = Iterator("a", "b", "c", "d", "e")
    val states = processor.process(words, initial).toList
    
    assert(states.length == 5)
    states.zipWithIndex.foreach { (state, idx) =>
      assert(state.window.size == idx + 1)
    }

end InteractiveBehaviorTest

package impl

import edu.luc.cs.cs371.echo.main.WindowState
import org.scalatest.funsuite.AnyFunSuite

class WindowStateTest extends AnyFunSuite:

  test("ignores words shorter than minLength"):
    val state = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = 3,
      minLength = 4,
      howMany = 5
    )

    val afterShort = state.process("the")    // too short
    val afterValid = afterShort.process("hello")  // valid

    // afterShort should be structurally equal to state
    assert(afterShort.window == state.window)
    assert(afterShort.freq == state.freq)
    assert(afterValid.window.contains("hello"))
    assert(!afterValid.window.contains("the"))

  test("maintains sliding window of specified size"):
    val state = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = 3,
      minLength = 1,
      howMany = 10
    )

    val s1 = state.process("one")
    val s2 = s1.process("two")
    val s3 = s2.process("three")
    val s4 = s3.process("four")  // "one" should slide out

    assert(s3.window.size == 3)
    assert(s3.window.contains("one"))
    assert(s4.window.size == 3)
    assert(!s4.window.contains("one"))
    assert(s4.window.contains("four"))

  test("tracks word frequencies correctly"):
    val state = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = 5,
      minLength = 1,
      howMany = 10
    )

    val finalState = List("apple", "banana", "apple", "cherry", "apple")
      .foldLeft(state)(_.process(_))

    assert(finalState.freq("apple") == 3)
    assert(finalState.freq("banana") == 1)
    assert(finalState.freq("cherry") == 1)

  test("sorts by frequency descending"):
    val state = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = 6,
      minLength = 1,
      howMany = 10
    )

    val finalState = List("alpha", "beta", "alpha", "gamma", "alpha", "beta")
      .foldLeft(state)(_.process(_))

    val topWords = finalState.topWords
    assert(topWords(0) == ("alpha", 3))
    assert(topWords(1) == ("beta", 2))

  test("limits results to howMany top words"):
    val state = WindowState(
      window = Vector.empty,
      freq = Map.empty,
      windowSize = 5,
      minLength = 1,
      howMany = 2
    )

    val finalState = List("word1", "word2", "word3", "word4", "word5")
      .foldLeft(state)(_.process(_))

    assert(finalState.topWords.size == 2)

  test("display formats correctly"):
    val state = WindowState(
      window = Vector("hello", "world", "hello"),
      freq = Map("hello" -> 2, "world" -> 1),
      windowSize = 3,
      minLength = 1,
      howMany = 2
    )

    val display = state.display
    assert(display.contains("hello: 2"))
    assert(display.contains("world: 1"))

end WindowStateTest

package impl

import main._
import org.scalatest.funsuite.AnyFunSuite

class TopWordsEngineTest extends AnyFunSuite:

  // Test observer that captures updates
  class TestObserver extends StatsObserver:
    var updates: List[WordStats] = List()
    var updateCount: Int = 0

    def update(stats: WordStats): Unit =
      updates = stats :: updates
      updateCount += 1

    def lastUpdate: Option[WordStats] = updates.headOption
    def allUpdates: List[WordStats] = updates.reverse

  test("ignores words shorter than minLength"):
    val observer = new TestObserver
    val engine = new TopWordsEngine(
      howMany = 5,
      minLength = 4,
      windowSize = 3,
      observer = observer
    )

    engine.process("the")    // too short
    engine.process("and")    // too short
    engine.process("hello")  // valid
    engine.process("world")  // valid
    engine.process("test")   // valid - window now full

    assert(observer.updateCount == 1)
    val topWords = observer.lastUpdate.get.topWords.map(_._1)
    assert(topWords.contains("hello"))
    assert(topWords.contains("world"))
    assert(topWords.contains("test"))
    assert(!topWords.contains("the"))

  test("maintains sliding window of specified size"):
    val observer = new TestObserver
    val engine = new TopWordsEngine(
      howMany = 10,
      minLength = 1,
      windowSize = 3,
      observer = observer
    )

    engine.process("one")
    engine.process("two")
    engine.process("three")

    assert(observer.updateCount == 1)

    engine.process("four")  // "one" slides out

    val words = observer.lastUpdate.get.topWords.map(_._1)
    assert(!words.contains("one"))
    assert(words.contains("four"))

  test("tracks word frequencies correctly"):
    val observer = new TestObserver
    val engine = new TopWordsEngine(
      howMany = 10,
      minLength = 1,
      windowSize = 5,
      observer = observer
    )

    engine.process("apple")
    engine.process("banana")
    engine.process("apple")
    engine.process("cherry")
    engine.process("apple")

    val freqs = observer.lastUpdate.get.topWords.toMap
    assert(freqs("apple") == 3)
    assert(freqs("banana") == 1)

  test("sorts by frequency descending"):
    val observer = new TestObserver
    val engine = new TopWordsEngine(
      howMany = 10,
      minLength = 1,
      windowSize = 6,
      observer = observer
    )

    List("alpha", "beta", "alpha", "gamma", "alpha", "beta").foreach(engine.process)

    val topWords = observer.lastUpdate.get.topWords
    assert(topWords(0) == ("alpha", 3))
    assert(topWords(1) == ("beta", 2))

  test("limits results to howMany top words"):
    val observer = new TestObserver
    val engine = new TopWordsEngine(
      howMany = 2,
      minLength = 1,
      windowSize = 5,
      observer = observer
    )

    List("word1", "word2", "word3", "word4", "word5").foreach(engine.process)

    assert(observer.lastUpdate.get.topWords.size == 2)

  test("only notifies observer when window is full"):
    val observer = new TestObserver
    val engine = new TopWordsEngine(
      howMany = 5,
      minLength = 1,
      windowSize = 4,
      observer = observer
    )

    engine.process("one")
    assert(observer.updateCount == 0)

    engine.process("two")
    assert(observer.updateCount == 0)

    engine.process("three")
    assert(observer.updateCount == 0)

    engine.process("four")
    assert(observer.updateCount == 1)

end TopWordsEngineTest

//tests passed as of 2/13/26 4:53 pm 
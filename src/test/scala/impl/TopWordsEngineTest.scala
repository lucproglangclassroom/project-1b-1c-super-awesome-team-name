package edu.luc.cs.cs371.echo.impl

import org.scalatest.funsuite.AnyFunSuite
import scala.language.unsafeNulls

class TopWordsEngineTest extends AnyFunSuite:

  def loadWords(file: String): Iterator[String] =
    scala.io.Source
      .fromResource(file)
      .getLines
      .flatMap(_.split("(?U)[^\\p{Alpha}0-9']+").nn)

  test("window fills and emits") {
    val obs = new TestObserver
    val engine = new TopWordsEngine(5, 3, 20, obs)

    loadWords("sample.txt").take(50).foreach(engine.process)

    assert(obs.seen.nonEmpty)
  }

  test("programming and scala dominate") {
    val obs = new TestObserver
    val engine = new TopWordsEngine(3, 4, 50, obs)

    loadWords("sample.txt").foreach(engine.process)

    val last = obs.seen.last.topWords.map(_._1)

    assert(last.contains("programming"))
    assert(last.contains("scala"))
  }

  test("frequencies never exceed window") {
    val obs = new TestObserver
    val engine = new TopWordsEngine(10, 3, 30, obs)

    loadWords("sample.txt").take(200).foreach(engine.process)

    obs.seen.foreach { stats =>
      stats.topWords.foreach { case (_, f) =>
        assert(f <= 30)
      }
    }
  }

  test("short words ignored") {
    val obs = new TestObserver
    val engine = new TopWordsEngine(5, 6, 20, obs)

    loadWords("sample.txt").foreach(engine.process)

    val allWords =
      obs.seen.flatMap(_.topWords.map(_._1)).toSet

    assert(!allWords.contains("fun"))
    assert(!allWords.contains("test"))
  }

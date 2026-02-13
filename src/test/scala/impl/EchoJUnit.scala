package edu.luc.cs.cs371.echo
package impl

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

import java.io.{ByteArrayOutputStream, PrintStream}
import scala.jdk.CollectionConverters.*

/**
  * Plain JUnit testing without ScalaTest.
  *
  * Alternatively, one can use
  * [[http://www.scalatest.org/getting_started_with_junit_4_in_scala JUnit with ScalaTest]]
  */
class EchoJUnit:

  @Test
  def testSimpleEmpty: Unit =
    assertEquals("", (new SimpleEcho).echo(""))

  @Test
  def testSimpleNonempty: Unit =
    assertEquals("hello", (new SimpleEcho).echo("hello"))

  @Test
  def testDoubleEmpty: Unit =
    assertEquals(" ", (new DoubleEcho).echo(""))

  @Test
  def testDoubleNonempty: Unit =
    assertEquals("hello hello", (new DoubleEcho).echo("hello"))

  @Test
  def testSimpleUsingList: Unit =
    val echos = List(new SimpleEcho)
    val result = echos(0).echo("")
    assertEquals("", result)

  @Test
  def testSimpleAlsoUsingList: Unit =
    val echos = List(new SimpleEcho)
    try
      val result = echos(1).echo("")
      fail("should have gotten an IndexOutOfBoundsException by now!")
    catch
      case ex: IndexOutOfBoundsException => // all good

end EchoJUnit

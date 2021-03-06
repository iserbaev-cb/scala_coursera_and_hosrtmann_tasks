package tasks_hostmann.scala-impatient-code.code.ch15.sec06

import scala.util.control.TailCalls._

object TailCallDemo extends App {
  def evenLength(xs: Seq[Int]): TailRec[Boolean] =
    if (xs.isEmpty) done(true) else tailcall(oddLength(xs.tail))

  def oddLength(xs: Seq[Int]): TailRec[Boolean] =
    if (xs.isEmpty) done(false) else tailcall(evenLength(xs.tail))

  println(evenLength(1 to 1000000).result)
}

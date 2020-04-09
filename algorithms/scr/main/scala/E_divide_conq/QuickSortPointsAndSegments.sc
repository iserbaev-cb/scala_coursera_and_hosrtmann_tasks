import scala.collection.Searching
import scala.math
import scala.util.Sorting
import scala.collection.Searching._

/**
 * Точка считается принадлежащей отрезку, если она находится внутри него или на границе.
 * Для каждой точки в порядке появления во вводе выведите, скольким отрезкам она принадлежит.
 *
 * точка НЕ лежит на данном отрезке только в двух случаях:
 * 1) Если координата конца отрезка меньше координаты точки;
 * 2) Если координата начала отрезка больше координаты точки;
 * В любом другом случае точка лежит на данном отрезке
 */
object Main {
  def solve(segmentCount: Int, pointCount: Int, segments: Array[(Int,Int)], points: Array[Int]): Unit = {
    val sortedLeft = segments.clone()
    Sorting.quickSort(sortedLeft)(Ordering.by(_._1))
    val sortedRight = segments.clone()
    Sorting.quickSort(sortedRight)(Ordering.by(_._2))

    def leftCount(point: Int) = sortedLeft.lastIndexWhere{case (left,right) => left <= point}
    def rightCount(point: Int) = sortedRight.lastIndexWhere{case (left,right) => right < point}

    def resultCount(point: Int) = leftCount(point) - rightCount(point)

    println(points.map(resultCount).mkString(" "))
  }
  def main(args: Array[String]): Unit = {
    val (segmentCount,pointCount)  = {
      val s = scala.io.StdIn.readLine().split(" ")
      s.head.toInt -> s.last.toInt
    }
    val segments = (1 to segmentCount).map(_ => {
      val s = scala.io.StdIn.readLine().split(" ")
      s.head.toInt -> s.last.toInt
    }).toArray
    val points = scala.io.StdIn.readLine().split(" ").map(_.toInt)

    solve(segmentCount, pointCount, segments, points)
  }
  def test(): Unit = {
    solve(2,3,Array((0,5),(7,10)),Array(1,6,11))
  }
}
Main.test()

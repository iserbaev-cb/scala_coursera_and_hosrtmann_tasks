package structures

import cats.syntax.option._

import scala.annotation.tailrec
import scala.collection.immutable

sealed trait BinarySearchTree[+T] {
  def key:         Option[T]
  def left:        BinarySearchTree[T]
  def right:       BinarySearchTree[T]
  def parentLabel: Option[T]

  def map[R](f: T => R): BinarySearchTree[R] = this match {
    case Nil =>
      Nil
    case Node(k, left, right, parentLabel) =>
      Node(f(k), left.map(f), right.map(f), parentLabel.map(f))
  }

  def fold[R](r: R)(f: (R, T) => R): R = {
    @tailrec
    def recur(rr: R, acc: List[BinarySearchTree[T]]): R = acc match {
      case immutable.Nil =>
        rr
      case ::(head, tl) =>
        head match {
          case Nil =>
            recur(rr, tl)
          case Node(k, left, right, _) =>
            recur(f(rr, k), left :: right :: tl)
        }
    }

    recur(r, List(this))
  }

  def fold2[R](r: R)(f: (R, T) => R): R = {
    // ! not tailrec
    def recur(rr: R, bb: BinarySearchTree[T]): R = bb match {
      case Nil =>
        rr
      case Node(k, left, right, _) =>
        recur(f(recur(rr, left), k), right)
    }

    recur(r, this)
  }
}

case object Nil extends BinarySearchTree[Nothing] {
  override def key: Option[Nothing] = None

  override def left:        BinarySearchTree[Nothing] = Nil
  override def right:       BinarySearchTree[Nothing] = Nil
  override def parentLabel: Option[Nothing]           = None
}

case class Node[+T](
  k:           T,
  left:        BinarySearchTree[T],
  right:       BinarySearchTree[T],
  parentLabel: Option[T]
) extends BinarySearchTree[T] {
  override def key: Option[T] = k.some
}

object BinarySearchTree {
  def inorderTreeWalk[T](x: BinarySearchTree[T]): List[T] = {
    @tailrec
    def recur(ts: List[BinarySearchTree[T]], acc: List[T]): List[T] = ts match {
      case immutable.Nil => acc
      case ::(head, tl) =>
        (head.left, head.right) match {
          case (Nil, Nil) =>
            recur(tl, head.key.map(_ :: acc).getOrElse(acc))
          case (Nil, value) =>
            recur(value :: tl, head.key.map(_ :: acc).getOrElse(acc))
          case (value, Nil) =>
            recur(value :: tl, head.key.map(_ :: acc).getOrElse(acc))
          case (value, value1) =>
            recur(value :: value1 :: tl, head.key.map(_ :: acc).getOrElse(acc))
        }
    }

    recur(List(x), List.empty)
  }

  // O(log n)
  @tailrec
  def treeSearch[T](x: BinarySearchTree[T], t: T)(
    implicit ordering: Ordering[T]
  ): BinarySearchTree[T] =
    x match {
      case Nil =>
        x
      case Node(k, _, _, _) if ordering.equiv(t, k) =>
        x
      case Node(k, l, _, _) if ordering.lt(t, k) =>
        treeSearch(l, t)
      case Node(k, _, r, _) if ordering.gt(t, k) =>
        treeSearch(r, t)
    }

  def treeMin[T](x: BinarySearchTree[T]): BinarySearchTree[T] = {
    @tailrec
    def recur(
      xx:  BinarySearchTree[T],
      acc: BinarySearchTree[T]
    ): BinarySearchTree[T] = xx.left match {
      case Nil =>
        acc
      case value: Node[T] =>
        recur(value, value)

    }
    recur(x, x)
  }

  def treeMax[T](x: BinarySearchTree[T]): BinarySearchTree[T] = {
    @tailrec
    def recur(
      xx:  BinarySearchTree[T],
      acc: BinarySearchTree[T]
    ): BinarySearchTree[T] = xx.right match {
      case Nil =>
        acc
      case value =>
        recur(value, value)
    }
    recur(x, x)
  }

  // O(log n)
  def parent[T](root:  BinarySearchTree[T], parentLabel: Option[T])(
    implicit ordering: Ordering[T]
  ): BinarySearchTree[T] =
    parentLabel.map(BinarySearchTree.treeSearch(root, _)).getOrElse(Nil)

  def treeSuccessor[T](x: BinarySearchTree[T], root: BinarySearchTree[T])(
    implicit opt:         Ordering[Option[T]],
    tOrd:                 Ordering[T]
  ): BinarySearchTree[T] =
    x.right match {
      case value: Node[T] =>
        treeMin(value)
      case Nil =>
        @tailrec
        def recur(
          y:  BinarySearchTree[T],
          xx: BinarySearchTree[T]
        ): BinarySearchTree[T] =
          parent(root, y.parentLabel) match {
            case yp: Node[T] if y.right.key == xx.key =>
              recur(yp, y)
            case _ =>
              y
          }
        recur(parent(root, x.parentLabel), x)
    }

  def treePredecessor[T](x: BinarySearchTree[T], root: BinarySearchTree[T])(
    implicit opt:           Ordering[Option[T]],
    tOrd:                   Ordering[T]
  ): BinarySearchTree[T] =
    x.left match {
      case value: Node[T] =>
        treeMax(value)
      case Nil =>
        @tailrec
        def recur(
          y:  BinarySearchTree[T],
          xx: BinarySearchTree[T]
        ): BinarySearchTree[T] =
          parent(root, y.parentLabel) match {
            case yp: Node[T] if y.left.key == xx.key =>
              recur(yp, y)
            case _ =>
              y
          }
        recur(parent(root, x.parentLabel), x)
    }

  def treeInsert[T](
    x:           BinarySearchTree[T],
    v:           BinarySearchTree[T],
    parentLabel: Option[T] = None
  )(
    implicit ordering: Ordering[T]
  ): BinarySearchTree[T] =
    (x, v) match {
      case (Nil, Nil) =>
        Nil
      case (Nil, n @ Node(_, _, _, _)) =>
        n.copy(parentLabel = parentLabel)
      case (n @ Node(_, _, _, _), Nil) =>
        n.copy(parentLabel = parentLabel)
      case (pp @ Node(pk, pl, _, _), n @ Node(nk, _, _, _))
          if ordering.lt(nk, pk) =>
        pp.copy(left = treeInsert(pl, n, pp.key), parentLabel = parentLabel)
      case (pp @ Node(_, _, r, _), n @ Node(_, _, _, _)) =>
        pp.copy(right = treeInsert(r, n, pp.key), parentLabel = parentLabel)
    }

  def treeInserts[T](x: BinarySearchTree[T], vv: T*)(
    implicit ordering:  Ordering[T]
  ): BinarySearchTree[T] = vv.toSeq.foldLeft(x) {
    case (xx, v) =>
      treeInsert(xx, Node(v, Nil, Nil, None))
  }

  def treeDelete[T](x: BinarySearchTree[T], v: T)(
    implicit ordering: Ordering[T]
  ): BinarySearchTree[T] = {
//    @tailrec
    def recur(xx: BinarySearchTree[T]): BinarySearchTree[T] = xx match {
      case Nil =>
        xx
      case Node(zk, zl, zr, parentLabel) if ordering.equiv(zk, v) =>
        (zl, zr) match {
          case (Nil, Nil) =>
            Nil
          case (Nil, node @ Node(_, _, _, _)) =>
            node.copy(parentLabel = parentLabel)
          case (node @ Node(_, _, _, _), Nil) =>
            node.copy(parentLabel = parentLabel)
          case (l @ Node(_, _, _, _), r @ Node(rk, rl, rr, _)) =>
            rl match {
              case Nil =>
                Node(rk, l.copy(parentLabel = Some(rk)), rr, parentLabel)
              case Node(yk, yl, yr, _) =>
                val tree = Node(
                  yk,
                  l.copy(parentLabel = Some(yk)),
                  r.copy(parentLabel = Some(yk)),
                  parentLabel
                )
                val t2 = treeInsert(tree, yl, parentLabel)
                treeInsert(t2, yr, parentLabel)
            }

        }
      case Node(zk, zl, zr, parentLabel) if ordering.lt(zk, v) =>
        Node(zk, zl, recur(zr), parentLabel)
      case Node(zk, zl, zr, parentLabel) if ordering.gt(zk, v) =>
        Node(zk, recur(zl), zr, parentLabel)
    }

    recur(x)
  }
}

object TestTree extends App {
  import BinarySearchTree._

  def test(): Unit = {

    /**
      *                        15
      *                     /     \
      *                    6      18
      *                   / \    /  \
      *                  3   7  17   20
      *                 / \   \
      *                2   4  13
      *                      /
      *                     9
      */
    val seq    = Seq(15, 6, 3, 7, 2, 4, 13, 9, 18, 17, 20)
    val result = treeInserts(Nil, seq: _*)

    assert(inorderTreeWalk(result).forall(seq.contains))

    assert(treeMin(result).key.get == seq.min)
    assert(treeMax(result).key.get == seq.max)

    seq.foreach(i => assert(treeSearch(result, i).key.get == i))

    assert(parent(result, result.left.parentLabel).key.get == 15)
    assert(parent(result, result.left.left.parentLabel).key.get == 6)
    assert(parent(result, result.parentLabel).key.isEmpty)

    assert(treeSuccessor(result, result).key.get == 17)
    assert(treeSuccessor(result.left.right.right, result).key.get == 15)
    assert(treePredecessor(result, result).key.get == 13)
    assert(treePredecessor(result.left.right, result).key.get == 6)
    assert(treePredecessor(result.right.left, result).key.get == 15)

    assert(seq.sum == result.fold(0)(_ + _))
    assert(seq.product == result.fold(1)(_ * _))
    assert(seq.sum == result.fold2(0)(_ + _))
    assert(seq.product == result.fold2(1)(_ * _))

    assert(result.map(_ + 1).fold(0)(_ + _) == seq.map(_ + 1).sum)

    assert(treeDelete(result, 3).fold(0)(_ + _) == (seq.sum - 3))

    val resW15 = treeDelete(result, 15)
    assert(treeSearch(resW15, 15).key.isEmpty)
    println(resW15)
  }

  test()

}

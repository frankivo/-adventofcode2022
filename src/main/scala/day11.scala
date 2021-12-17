import collection.mutable.Seq as MSeq
import scala.collection.mutable
import scala.util.Try

object day11 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    //    println(s"Part 2: ${part2()}")
  }

  type Board = Seq[MSeq[Int]]

  extension (b: Board) {
    def grow: Board = b.map(_.map(_ + 1))

    def reset: Board = b.map(_.map(c => if (c > 9) 0 else c))

    def flash: Long = {
      var keepGoing = true
      val flashed = mutable.HashSet[(Int, Int)]()

      while keepGoing
      do {
        keepGoing = false
        b.indices.foreach(row => {
          b.head.indices.foreach(col => {
            if (b(row)(col) > 9 && !flashed.contains((row, col))) {
              flashed += ((row, col))

              adjecent(row, col)
                .foreach(c => {
                  val cur = findCell(c._1, c._2).get
                  b(c._1)(c._2) = cur + 1
                  if (!flashed.contains(c._1, c._2))
                    keepGoing = true
                })
            }
          })
        })
      }
      flashed.size
    }

    def findCell(row: Int, col: Int): Option[Int] = Try(b(row)(col)).toOption

    def adjecent(row: Int, col: Int): Seq[(Int, Int)] = {
      val xs = (-1 to 1).map(_ + row)
      val ys = (-1 to 1).map(_ + col)

      xs
        .flatMap(x => ys.map(y => (x, y)))
        .filterNot(c => c._1 == row && c._2 == 1)
        .filter(c => findCell(c._1, c._2).isDefined)
    }

    def debug(): Unit = {
      b.map(row => {
        row.map(c => {
          if (c > 9) 'X'
          else c
        }).mkString
      }).foreach(println)
    }
  }


  def part1(): Int = {
    val data = input
    //    data.debug()
    //    val x = data.grow
    //    x.flash
    //    println("-" * 20)
    //    x.debug()
    //    println("-" * 20)
    //    val y = x.reset.grow
    //    y.flash
    //    y.debug()

    (0 until 2).foldLeft(0) {
      (score, step) => {

      }
    }

    0
  }

  def part2(): Int = ???

  val input: Board = {
    io.Source.fromResource("day11.txt")
      .getLines()
      .map(l => MSeq.from(l.toCharArray.map(_.getNumericValue)))
      .toSeq
  }
}

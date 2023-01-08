package com.github.frankivo
package tasks

import java.time.Instant


object day17 {
  def main(args: Array[String]): Unit = {
    println(Instant.now())
    println(s"Tower is ${part1()} units tall")
    println(Instant.now())
    //    println(s"Tower is ${part2()} units tall")
  }

  private val jetStream = JetStream()
  private val rockStream = RockStream()

  private val moveCount = 2022
  private val fieldWidth = 7
  private val rockStatic = "#"
  private val rockMoving = "@"
  private val air = "."

  private def part1(): Int = solve(Map.empty[Int, Int], moveCount).size

  private def part2(): Long = {
    //    val p1 = solve(Set.empty[coordinate], 200)
    //    p1.show()
    0
  }

  private def solve(start: Map[Int, Int], moves: Int): Map[Int, Int] = {
    val end = (0 until 2).foldLeft(start) {
      (state1, _) => {
        val stopped = Seq.unfold(rockStream.next(state1.height)) {
          r => {
            Option.when(r.exists(_.name == rockMoving)) {
              val moved = r.blowJet
              val fallen = moved.fall
              (fallen, fallen)
            }
          }
        }.last

        stopped.foldLeft(state1) {
          (state2, cur) => state2 + (cur.y -> (state2.getOrElse(cur.y, 1) << cur.x))
        }
      }

    }
    println(end)
    end
  }

  extension (field: Map[Int, Int]) {
    private def height: Int = field.size

    private def show(): Unit = {
      (0 to height).reverse.foreach(y => {
        (0 to fieldWidth + 1).foreach(x => {
          if (y == 0)
            print(if (x == 0 || x == fieldWidth + 1) "+" else "-")
          else if (x == 0 || x == fieldWidth + 1)
            print("|")
          else
            print(air)
          //            print(coordinates.find(c => c.y == y && c.x == x).map(_.name).getOrElse(air))
        })
        println()
      })
      println()
    }
  }

  extension (rock: Seq[coordinate]) {
    private def blowJet: Seq[coordinate] = {
      val jet = jetStream.next
      rock
        .map(r => {
          val direction = if (jet == '>') 1 else -1
          val blocked = if (isBlockedHorizontal(jet)) 0 else 1
          coordinate(r.x + (direction * blocked), r.y, r.name)
        })
    }

    private def fall: Seq[coordinate] = {
      rock
        .map(r => {
          coordinate(
            r.x,
            r.y - (if (isBlockedVertical) 0 else 1),
            if (isBlockedVertical) rockStatic else rockMoving
          )
        })
    }

    private def isBlockedHorizontal(jet: Char): Boolean = {
      val move = if (jet == '>') 1 else -1
      rock.exists(r => {
        Seq(-1, fieldWidth).contains(r.x + move)
      })

      //      getRock.exists(r => {
      //        Seq(0, fieldWidth + 1).contains(r.x + move) ||
      //          coordinates.find(c => c.x == r.x + move && c.y == r.y).map(_.name).getOrElse(air) == rockStatic
      //      })
    }

    private def isBlockedVertical: Boolean = {
      rock.exists(r => {
        r.y - 1 == -1 //||
        //          coordinates.find(c => c.x == r.x && c.y == r.y - 1).map(_.name).getOrElse(air) == rockStatic
      })
    }
  }

  private class JetStream {
    private val jets: String = util.get("day17.txt").head

    private val iterator: Iterator[Int] = LazyList.from(0).iterator

    def next: Char = jets(iterator.next() % jets.length)

    val size: Int = jets.length
  }

  private class RockStream {
    private val iterator: Iterator[Int] = LazyList.from(0).iterator

    def next(top: Int): Seq[coordinate] = {
      val shape = iterator.next() % 5
      val xys = shape match
        case 0 => // Horizontal line
          (2 to 5).map(i => (i, top + 3))
        case 1 => // Cross
          Seq((4, top + 6), (3, top + 5), (4, top + 5), (5, top + 5), (4, top + 4))
        case 2 => // Reverse L
          Seq((5, top + 6), (5, top + 5), (5, top + 4), (4, top + 4), (3, top + 4))
        case 3 => // Vertical line
          (4 to 7).map(i => (3, top + i))
        case 4 => // Square
          Seq((3, top + 4), (4, top + 4), (3, top + 5), (4, top + 5))

      xys.map(xy => coordinate(xy._1, xy._2, rockMoving))
    }
  }
}

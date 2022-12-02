package com.github.frankivo
package tasks

object day2 {

  def main(args: Array[String]): Unit = {
    println(s"Total score: $getTotalScore")
    println(s"Total score with secret strategy: $getScoreWithWinningStrategy")
  }

  private val hands = Map(
    'A' -> "rock",
    'B' -> "paper",
    'C' -> "scissors",
    'X' -> "rock",
    'Y' -> "paper",
    'Z' -> "scissors",
  )

  private val scores = Map(
    "rock" -> 1,
    "paper" -> 2,
    "scissors" -> 3,
  )

  private val wins = Map( // Left wins
    "rock" -> "scissors",
    "scissors" -> "paper",
    "paper" -> "rock",
  )

  private def getTotalScore: Long = {
    input.foldLeft(0L) {
      (total, line) => {

        val opponent = hands(line._1)
        val player = hands(line._2)
        val score = scores(player)

        if (opponent equals player)
          total + 3 + score
        else if (wins(player) equals opponent)
          total + 6 + score
        else
          total + 0 + score
      }
    }
  }

  private def getScoreWithWinningStrategy: Long = {
    input.foldLeft(0L) {
      (total, line) => {

        val opponent = hands(line._1)

        val player = {
          if (line._2 equals 'X') wins(opponent) // Lose
          else if (line._2 equals 'Y') opponent // Draw
          else wins.find(w => opponent equals w._2).get._1 // Win
        }

        val score = scores(player)

        if (opponent equals player)
          total + 3 + score
        else if (wins(player) equals opponent)
          total + 6 + score
        else
          total + 0 + score
      }
    }
  }


  private val input: Seq[(Char, Char)] = util.get("day2.txt")
    .map(_.filter(('A' to 'Z').toSet))
    .map(l => (l(0), l(1)))
}

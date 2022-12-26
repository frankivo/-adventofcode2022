package com.github.frankivo
package tasks

import com.google.gson.reflect.TypeToken
import com.google.gson.{JsonArray, JsonParser}

object day13 {
  def main(args: Array[String]): Unit = {
    println(s"Sum of sorted pair indices: $part1")
  }

  private case class Pair(index: Int, left: JsonArray, right: JsonArray)

  def part1: Long = rawInput.flatMap(p => Option.when(compareLists(p.left, p.right))(p.index)).sum

  private def compareLists(left: JsonArray, right: JsonArray): Boolean = {
    left.size() == right.size()
  }

  private val rawInput: Seq[Pair] = util.get("day13.txt")
    .filterNot(_.isEmpty)
    .map(JsonParser.parseString)
    .map(_.getAsJsonArray)
    .sliding(2)
    .zip(LazyList.from(1))
    .map(p => Pair(p._2, p._1.head, p._1.last))
    .toSeq
}

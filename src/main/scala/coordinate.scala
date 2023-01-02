package com.github.frankivo

case class coordinate(x: Int, y: Int, name: String = "?") {
  def distance(other: coordinate): Int = Seq((x - other.x).abs, (y - other.y).abs).max
}

package basics

abstract class Animal(val age: Int, val weight: Double) {
  def sound(): String
}

class Cow(a: Int, w: Double) extends Animal(a, w) {
  def sound() = "moo"
}

trait Animal2 {
  val age: Int
  val weight: Double
  def sound(): String
}

class Giraffe(val age: Int, val weight: Double) extends Animal2 {
  def sound() = "bleet"
}
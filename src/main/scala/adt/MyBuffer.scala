package adt

trait MyBuffer[A] {
  def apply(i: Int): A
  def insert(elem: A, i: Int): Unit
  def length: Int
  def remove(i: Int): A
  def update(i: Int, elem: A): Unit
  def iterator: Iterator[A]
}
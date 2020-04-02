package adt

class MyMutableSLL[A] extends MyMutableList[A] {
  import MyMutableSLL._
  private var head: Node[A] = null
  private var tail: Node[A] = null
  private var numElems = 0

  def apply(index: Int): A = ???
  def insert(elem: A, index: Int): Unit = ???
  def length: Int = ???
  def remove(index: Int): A = ???
  def update(index: Int, elem: A): Unit = ???
  def iterator: Iterator[A] = ???
}

object MyMutableSLL {
  class Node[A](data: A, next: Node[A])
}
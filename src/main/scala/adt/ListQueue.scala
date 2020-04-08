package adt

class ListQueue[A] extends Queue[A] {
  import ListQueue._
  private var front: Node[A] = null
  private var back: Node[A] = null

  def dequeue(): A = {
    val ret = front.data
    front = front.next
    ret
  }

  def enqueue(a: A): Unit = {
    val n = new Node[A](a, null)
    if (back != null) {
      back.next = n
      back = n
    } else {
      front = n
      back = n
    }
  }

  def isEmpty: Boolean = front == null

  def peek: A = front.data
}

object ListQueue {
  private class Node[A](val data: A, var next: Node[A])
}
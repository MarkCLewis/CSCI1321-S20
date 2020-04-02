package adt

import scala.reflect.ClassTag

class MyMutableArrayList[A : ClassTag] extends MyMutableList[A] {
  private var size = 0
  private var arr = Array.fill(10)(null.asInstanceOf[A])

  def apply(index: Int): A = {
    if (index < 0 || index >= size) throw new IndexOutOfBoundsException(s"$index: size = $size")
    arr(index)
  }

  def insert(elem: A, index: Int): Unit = {
    if (index < 0 || index > size) throw new IndexOutOfBoundsException(s"$index: size = $size")
    if (size >= arr.length) {
      val tmp = Array.fill(arr.length*2)(null.asInstanceOf[A])
      for (i <- 0 until arr.length) tmp(i) = arr(i)
      arr = tmp
    }
    for (i <- size to index+1 by -1) arr(i) = arr(i-1)
    arr(index) = elem
    size += 1
  }

  def length: Int = size

  def remove(index: Int): A = {
    if (index < 0 || index >= size) throw new IndexOutOfBoundsException(s"$index: size = $size")
    val ret = arr(index)
    for (i <- index until size-1) arr(i) = arr(i+1)
    size -= 1
    ret
  }

  def update(index: Int, elem: A): Unit = {
    if (index < 0 || index >= size) throw new IndexOutOfBoundsException(s"$index: size = $size")
    arr(index) = elem
  }

  def iterator = new Iterator[A] {
    private var index = 0
    def hasNext: Boolean = index < arr.length
    def next(): A = {
      val ret = arr(index)
      index += 1
      ret
    }
  }
}
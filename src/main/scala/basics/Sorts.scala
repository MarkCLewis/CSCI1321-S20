package basics

object Sorts extends App {
  def bubbleSort[A <% Ordered[A]](arr: Array[A]): Unit = {
    for (i <- 0 until arr.length; j <- 0 until arr.length - 1 - i) {
      if (arr(j) > arr(j+1)) {
        val tmp = arr(j)
        arr(j) = arr(j+1)
        arr(j+1) = tmp
      }
    }
  }

  def bubbleSort2[A](arr: Array[A])(gt: (A, A) => Boolean): Unit = {
    for (i <- 0 until arr.length; j <- 0 until arr.length - 1 - i) {
      if (gt(arr(j), arr(j+1))) {
        val tmp = arr(j)
        arr(j) = arr(j+1)
        arr(j+1) = tmp
      }
    }
  }

  val nums = Array.fill(20)(util.Random.nextInt(100))
  println(nums.mkString(", "))
  bubbleSort2(nums)(_ % 10 < _ % 10)
  println(nums.mkString(", "))

  def merge[A](l1: List[A], l2: List[A])(lt: (A, A) => Boolean): List[A] = (l1, l2) match {
    case (Nil, _) => l2
    case (_, Nil) => l1
    case (h1 :: t1, h2 :: t2) =>
      if (lt(h1, h2)) h1 :: merge(t1, l2)(lt) else h2 :: merge(l1, t2)(lt)
  }

  def mergeSort[A](lst: List[A])(lt: (A, A) => Boolean): List[A] = lst match {
    case Nil => Nil
    case h :: Nil => lst
    case _ =>
      val (left, right) = lst.splitAt(lst.length / 2)
      merge(mergeSort(left)(lt), mergeSort(right)(lt))(lt)
  }

  println(mergeSort(List.fill(10)(util.Random.nextInt(100)))(_ < _))
}
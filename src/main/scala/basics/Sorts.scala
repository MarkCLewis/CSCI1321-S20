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

  // O(n*log(n))
  // n = 2^l  ----> l = log_2(n) = O(log(n))
  // n/2
  // n/4
  // ...
  // 8 = 2^3
  // 4 = 2^2
  // 2 = 2^1
  // 1 = 2^0
  def mergeSort[A](lst: List[A])(lt: (A, A) => Boolean): List[A] = lst match {
    case Nil => Nil
    case h :: Nil => lst
    case _ =>
      val (left, right) = lst.splitAt(lst.length / 2)
      merge(mergeSort(left)(lt), mergeSort(right)(lt))(lt)
  }

  println(mergeSort(List.fill(10)(util.Random.nextInt(100)))(_ < _))

  // O(n*log(n))
  def quicksort[A](lst: List[A])(lt: (A, A) => Boolean): List[A] = lst match {
    case Nil | _ :: Nil => lst
    case pivot :: t =>
      val (less, greater) = t.partition(x => lt(x, pivot))
      quicksort(less)(lt) ::: (pivot :: quicksort(greater)(lt))
  }

  println(quicksort(List.fill(10)(util.Random.nextInt(100)))(_ < _))

  def quicksort[A](arr: Array[A])(lt: (A, A) => Boolean): Unit = {
    def helper(start: Int, end: Int): Unit = {
      if (start < end - 1) {
        // Pick better pivot
        var low = start + 1
        var high = end - 1
        while (low <= high) {
          if(lt(arr(low), arr(start))) {
            low += 1
          } else {
            val tmp = arr(low)
            arr(low) = arr(high)
            arr(high) = tmp
            high -= 1
          }
        }
        val tmp = arr(start)
        arr(start) = arr(high)
        arr(high) = tmp
        helper(start, high)
        helper(high + 1, end)
      }
    }
    helper(0, arr.length)
  }

  val nums2 = Array.fill(10)(util.Random.nextInt(100))
  quicksort(nums2)(_ < _)
  println(nums2.mkString(", "))
}
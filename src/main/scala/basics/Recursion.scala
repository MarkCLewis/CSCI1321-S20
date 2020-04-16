package basics

object Recursion extends App {
  def factorial(n: Int): Int = if (n < 2) 1 else n * factorial(n-1)

  def fibonacci(n: Int): Int = if (n < 2) 1 else fibonacci(n-1) + fibonacci(n-2)

  println(factorial(5))
  println(fibonacci(5))

  def packBin(bins: Array[Double], objs: Array[Double]): Boolean = {
    def helper(obj: Int): Boolean = {
      if (obj >= objs.length) true else {
        var ret = false
        for (bin <- bins.indices) {
          if (bins(bin) >= objs(obj)) {
            bins(bin) -= objs(obj)
            ret ||= helper(obj + 1)
            bins(bin) += objs(obj)
          }
        }
        ret
      }
    }
    helper(0)
  }

  println(packBin(Array(7, 5), Array(5, 4, 3)))
  println(packBin(Array(9, 9, 9, 4), Array(5, 5, 5, 5)))

  def knapsack(items: List[(Double, Double)], capacity: Double): Double = items match {
    case Nil => 0.0
    case (weight, value) :: t =>
      val take = if (capacity >= weight) value + knapsack(t, capacity - weight) else 0.0
      val leave = knapsack(t, capacity)
      take max leave
  }

  println(knapsack(List(2.0 -> 3.0, 5.0 -> 4.0, 7.0 -> 6.0, 1.0 -> 1.5), 10))

}
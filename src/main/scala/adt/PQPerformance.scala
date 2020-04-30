package adt

object PQPerformance extends App {
  val nums = Array.fill(1000000)(util.Random.nextInt())
  time(new UnsortedArrayPQ[Int](_ < _))
  
  time(new BinaryHeapPQ[Int](_ < _))
  
  def time(pq: MyPriorityQueue[Int]): Unit = {
    val start = System.nanoTime()
    for (n <- nums) pq.enqueue(n)
    while (!pq.isEmpty) pq.dequeue()
    println((System.nanoTime() - start) * 1e-9)
  }

}
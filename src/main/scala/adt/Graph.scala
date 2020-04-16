package adt

object Graph extends App {
  val matrix = Array(
    Array(-1, 8, -1, -1, -1, -1, -1),
    Array(-1, -1, 3, -1, 5, -1, -1),
    Array(7, -1, -1, -1, -1, -1, 4),
    Array(-1, -1, 2, -1, -1, 1, -1),
    Array(-1, -1, -1, 6, -1, -1, -1),
    Array(-1, -1, -1, -1, -1, -1, 5),
    Array(-1, -1, -1, -1, -1, 2, 9)
  )

  def reachable(
      node1: Int,
      node2: Int,
      connect: Array[Array[Int]],
      visited: Set[Int]
  ): Boolean = {
    if (node1 == node2) true
    else {
      var ret = false
      for (n <- 0 until connect.length) {
        if (connect(node1)(n) >= 0 && !visited(n))
          ret ||= reachable(n, node2, connect, visited + node1)
      }
      ret
    }
  }

  println(reachable(0, 5, matrix, Set.empty))
  println(reachable(5, 0, matrix, Set.empty))

  def shortestPath(
      node1: Int,
      node2: Int,
      connect: Array[Array[Int]],
      visited: Set[Int]
  ): Int = {
    if (node1 == node2) 0
    else {
      var ret = 1000000000
      for (n <- 0 until connect.length) {
        if (connect(node1)(n) >= 0 && !visited(n))
          ret = ret min (shortestPath(n, node2, connect, visited + node1) + connect(node1)(n))
      }
      ret
    }
  }

  println(shortestPath(0, 6, matrix, Set.empty))
}

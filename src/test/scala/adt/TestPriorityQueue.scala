package adt

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class TestPriorityQueue {
  private var pq: MyPriorityQueue[Int] = null

  @Before def makePQ(): Unit = {
    pq = new BinaryHeapPQ[Int](_ < _)
  }

  @Test def emptyOnCreate(): Unit = {
    assertTrue(pq.isEmpty)
  }

  @Test def addRemoveAFew(): Unit = {
    pq.enqueue(5)
    pq.enqueue(8)
    pq.enqueue(3)
    assertFalse(pq.isEmpty)
    assertEquals(3, pq.peek)
    assertEquals(3, pq.dequeue)
    assertEquals(5, pq.peek)
    assertEquals(5, pq.dequeue)
    assertEquals(8, pq.peek)
    assertEquals(8, pq.dequeue)
    assertTrue(pq.isEmpty)
  }

  @Test def addRemoveMany(): Unit = {
    val nums = Array.fill(1000)(util.Random.nextInt)
    nums.foreach(pq.enqueue)
    assertFalse(pq.isEmpty)
    for (n <- nums.sorted) {
      assertEquals(n, pq.peek)
      assertEquals(n, pq.dequeue())
    }
    assertTrue(pq.isEmpty)
  }
}
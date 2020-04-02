package adt

import org.junit.Test
import org.junit.Before
import org.junit.Assert._

class TestMyMutableList {
  private var buffer: MyMutableList[Int] = null

  @Before def createBuffer() = {
    buffer = new MyMutableArrayList[Int]
  }

  @Test def emptyOnCreate() = {
    assertTrue(buffer.length == 0)
  }

  @Test def addSome() = {
    buffer.insert(5, 0)
    buffer.insert(8, 0)
    buffer.insert(3, 2)
    assertEquals(8, buffer(0))
    assertEquals(5, buffer(1))
    assertEquals(3, buffer(2))
    assertEquals(3, buffer.length)
  }

  @Test def addRemoveSome() = {
    buffer.insert(5, 0)
    buffer.insert(8, 0)
    buffer.insert(3, 2)
    assertEquals(8, buffer(0))
    assertEquals(5, buffer(1))
    assertEquals(3, buffer(2))
    assertEquals(3, buffer.length)
    assertEquals(8, buffer.remove(0))
    assertEquals(5, buffer(0))
    assertEquals(3, buffer(1))
    assertEquals(2, buffer.length)
  }

  @Test def addRemoveALotFromEndAndIterate() = {
    val nums = Array.fill(100)(util.Random.nextInt())
    for (n <- nums) buffer.insert(n, buffer.length)
    assertEquals(nums.length, buffer.length)
    for ((n1, n2) <- nums.iterator.zip(buffer.iterator)) {
      assertEquals(n1, n2)
    }
    for (n <- nums.reverse) assertEquals(n, buffer.remove(buffer.length-1))
    assertEquals(0, buffer.length)
  }

  @Test def addRemoveALotFromFrontAndIterate() = {
    val nums = Array.fill(100)(util.Random.nextInt())
    for (n <- nums) buffer.insert(n, 0)
    assertEquals(nums.length, buffer.length)
    for ((n1, n2) <- nums.reverse.iterator.zip(buffer.iterator)) {
      assertEquals(n1, n2)
    }
    for (n <- nums.reverse) assertEquals(n, buffer.remove(0))
    assertEquals(0, buffer.length)
  }
}
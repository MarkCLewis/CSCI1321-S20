package adt

import org.junit.Test
import org.junit.Before
import org.junit.Assert._

class TestMyMutableList {
  private var lst: MyMutableList[Int] = null

  @Before def createBuffer() = {
    lst = new MyMutableDLL[Int]
  }

  @Test def emptyOnCreate() = {
    assertTrue(lst.length == 0)
  }

  @Test def addSome() = {
    lst.insert(5, 0)
    lst.insert(8, 0)
    lst.insert(3, 2)
    assertEquals(8, lst(0))
    assertEquals(5, lst(1))
    assertEquals(3, lst(2))
    assertEquals(3, lst.length)
  }

  @Test def addRemoveSome() = {
    lst.insert(5, 0)
    lst.insert(8, 0)
    lst.insert(3, 2)
    assertEquals(8, lst(0))
    assertEquals(5, lst(1))
    assertEquals(3, lst(2))
    assertEquals(3, lst.length)
    assertEquals(8, lst.remove(0))
    assertEquals(5, lst(0))
    assertEquals(3, lst(1))
    assertEquals(2, lst.length)
  }

  @Test def addRemoveALotFromEndAndIterate() = {
    val nums = Array.fill(100)(util.Random.nextInt())
    for (n <- nums) lst.insert(n, lst.length)
    assertEquals(nums.length, lst.length)
    for ((n1, n2) <- nums.iterator.zip(lst.iterator)) {
      assertEquals(n1, n2)
    }
    for (n <- nums.reverse) assertEquals(n, lst.remove(lst.length-1))
    assertEquals(0, lst.length)
  }

  @Test def addRemoveALotFromFrontAndIterate() = {
    val nums = Array.fill(100)(util.Random.nextInt())
    for (n <- nums) lst.insert(n, 0)
    assertEquals(nums.length, lst.length)
    for ((n1, n2) <- nums.reverse.iterator.zip(lst.iterator)) {
      assertEquals(n1, n2)
    }
    for (n <- nums.reverse) assertEquals(n, lst.remove(0))
    assertEquals(0, lst.length)
  }
}
package adt

import org.junit.Test
import org.junit.Assert._
import org.junit.Before

class TestBST {
  private var bst: BSTMap[Int, String] = null
  
  @Before def makeTree(): Unit = bst = new BSTMap[Int, String](_ < _)

  @Test def startEmpty(): Unit = {
    assertTrue(bst.isEmpty)
  }

  @Test def addGetAFew(): Unit = {
    bst += 5 -> "five" += 3 -> "three" += 7 -> "seven" += 4 -> "four" += 9 -> "nine"
    assertFalse(bst.isEmpty)
    assertEquals(Some("three"), bst.get(3))
    assertEquals(Some("four"), bst.get(4))
    assertEquals(Some("five"), bst.get(5))
    assertEquals(Some("seven"), bst.get(7))
    assertEquals(Some("nine"), bst.get(9))
  }

  @Test def addIterateAFew(): Unit = {
    bst += 5 -> "five" += 3 -> "three" += 7 -> "seven" += 4 -> "four" += 9 -> "nine"
    val iter = bst.iterator
    assertTrue(iter.hasNext)
    assertEquals((3 -> "three"), iter.next())
    assertEquals((4 -> "four"), iter.next())
    assertEquals((5 -> "five"), iter.next())
    assertEquals((7 -> "seven"), iter.next())
    assertEquals((9 -> "nine"), iter.next())
    assertFalse(iter.hasNext)
  }

  @Test def addAFewRemoveRoot(): Unit = {
    bst += 5 -> "five" += 3 -> "three" += 7 -> "seven" += 4 -> "four" += 9 -> "nine"
    bst -= 5
    assertEquals(Some("three"), bst.get(3))
    assertEquals(Some("four"), bst.get(4))
    assertEquals(None, bst.get(5))
    assertEquals(Some("seven"), bst.get(7))
    assertEquals(Some("nine"), bst.get(9))
    val iter = bst.iterator
    assertTrue(iter.hasNext)
    assertEquals((3 -> "three"), iter.next())
    assertEquals((4 -> "four"), iter.next())
    assertEquals((7 -> "seven"), iter.next())
    assertEquals((9 -> "nine"), iter.next())
    assertFalse(iter.hasNext)
  }

  @Test def bigTest(): Unit = {
    val keys = Array.fill(1000)(util.Random.nextInt()).distinct
    val pairs = keys.map(_ -> util.Random.nextString(5))
    for (kv <- pairs) bst += kv
    for (kv <- pairs) assertEquals(Some(kv._2), bst.get(kv._1))
    val iter = bst.iterator
    for (kv <- pairs.sortBy(_._1)) assertEquals(kv, iter.next)
    for (kv <- pairs) {
      assertEquals(Some(kv._2), bst.get(kv._1))
      bst -= kv._1
      assertEquals(None, bst.get(kv._1))
    }
    assertTrue(bst.isEmpty)
  }
}
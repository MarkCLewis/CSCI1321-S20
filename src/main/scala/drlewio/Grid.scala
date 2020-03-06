package drlewio

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.input.KeyCode
import scala.concurrent.java8.FuturesConvertersImpl.P

class Grid {
  val odds = 0.3
  private var _currentPill = new Pill(Seq(new PillHalf(3, 0, ColorOption.randomColor),
    new PillHalf(4, 0, ColorOption.randomColor)))
  private var _nextPill = new Pill(Seq(new PillHalf(3, 0, ColorOption.randomColor),
    new PillHalf(4, 0, ColorOption.randomColor)))
  private var _elements: Seq[Element] = (for (i <- 0 until 8; j <- 6 until 16; if math.random < odds) yield {
    new Virus(i, j, ColorOption.randomColor)
  })

  val gridWidth = 8
  val gridHeight = 16
  private var keysHeld = Set[KeyCode]()
  private var dropping = false
  private var fallDelay = 0.0
  val fallInterval = 1.0
  private var moveDelay = 0.0
  val moveInterval = 0.1

  def currentPill = _currentPill
  def elements: Seq[Element] = currentPill +: _elements
  def update(delay: Double): Unit = {
    if (dropping) {
      // We build this Map for fast lookups to check if things are supported or need to be removed.
      val g = _elements.flatMap(e => e.cells.map(c => (c.x, c.y) -> (e, c))).toMap
      val toFall = unsupportedElements(g)
      if (toFall.isEmpty) {
        val toRemove = checkForRemove(g)
        if (toRemove.isEmpty) {
          _currentPill = _nextPill
          _nextPill = new Pill(Seq(new PillHalf(3, 0, ColorOption.randomColor), new PillHalf(4, 0, ColorOption.randomColor)))          
          dropping = false
        } else {
          _elements = _elements.flatMap(_.removeAll(toRemove))
        }
      } else {
        _elements = _elements.map(e => if (toFall(e)) e.move(0, 1, isClear) else e)
      }
      fallDelay = 0.0
    } else {
      fallDelay += delay
      moveDelay += delay
      if (fallDelay >= fallInterval) {
        val movedPill = currentPill.move(0, 1, isClear)
        if (movedPill == _currentPill) {
          _elements +:= _currentPill
          dropping = true
        } else {
          _currentPill = movedPill
        }
        fallDelay = 0.0
      }
      if (moveDelay >= moveInterval) {
        if (keysHeld(KeyCode.Up)) _currentPill = currentPill.rotate(isClear)
        if (keysHeld(KeyCode.Left)) _currentPill = currentPill.move(-1, 0, isClear)
        if (keysHeld(KeyCode.Right)) _currentPill = currentPill.move(1, 0, isClear)
        if (keysHeld(KeyCode.Down)) _currentPill = currentPill.move(0, 1, isClear)
        moveDelay = 0.0
      }
    }
  }
  def isClear(x: Int, y: Int): Boolean = {
    y < gridHeight && x >= 0 && x < gridWidth && 
      !_elements.exists(e => e.cells.exists(c => c.x == x && c.y == y))
  }

  def keyPressed(keyCode: KeyCode): Unit = keysHeld += keyCode
  def keyReleased(keyCode: KeyCode): Unit = keysHeld -= keyCode

  def buildPassable: PassableGrid = {
    val cells = for (elem <- elements; cell <- elem.cells) yield {
      cell.buildPassable
    }
    PassableGrid(cells)
  }

  def unsupportedElements(g: Map[(Int, Int), (Element, GridCell)]): Set[Element] = {
    _elements = _elements.sortBy(_.cells.foldLeft(0)((acc, c) => acc max c.y)) // Need to go through elements from bottom to top.
    val unsupported = collection.mutable.Set[Element]() // Set of things that we have found that aren't supported.
    for (e <- _elements; if !e.selfSupported) { // Run through the things that could fall.
      val maxY = e.cells.foldLeft(0)((acc, c) => acc max c.y)
      if (e.cells.forall(c => maxY < gridHeight-1 && g.get(c.x -> (c.y+1)).map(below => below._1 == e || unsupported(below._1)).getOrElse(true))) unsupported += e
    }
    unsupported.toSet
  }

  def checkForRemove(g: Map[(Int, Int), (Element, GridCell)]): Set[GridCell] = {
    val ret = collection.mutable.Set[GridCell]()
    // Check columns
    for (x <- 0 until gridWidth) {
      var matchingCells = List[GridCell]()
      for (y <- 0 until gridHeight) {
        val p = x -> y
        if (g.contains(p)) {
          if (matchingCells.isEmpty || g(p)._2.color == matchingCells.head.color) {
            matchingCells ::= g(p)._2
          } else {
            if (matchingCells.length >= 4) ret ++= matchingCells
            matchingCells = g(p)._2 :: Nil
          }
          println(x, y, matchingCells, ret)
        } else {
          if (matchingCells.length >= 4) ret ++= matchingCells
          matchingCells = Nil
        }
      }
      if (matchingCells.length >= 4) {
        ret ++= matchingCells
      }
    }
    // Check rows
    for (y <- 0 until gridHeight) {
      var matchingCells = List[GridCell]()
      for (x <- 0 until gridWidth) {
        val p = x -> y
        if (g.contains(p)) {
          if (matchingCells.isEmpty || g(p)._2.color == matchingCells.head.color) {
            matchingCells ::= g(p)._2
          } else {
            if (matchingCells.length >= 4) ret ++= matchingCells
            matchingCells = g(p)._2 :: Nil
          }
        } else {
          if (matchingCells.length >= 4) {
            ret ++= matchingCells
          }
          matchingCells = Nil
        }
      }
      if (matchingCells.length >= 4) ret ++= matchingCells
    }
    println(ret)
    ret.toSet
  }
}
package drlewio

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.input.KeyCode

class Grid {
  val odds = 0.3
  private var _currentPill = new Pill(Seq(new PillHalf(3, 0, ColorOption.randomColor),
    new PillHalf(4, 0, ColorOption.randomColor)))
  private var _nextPill = new Pill(Seq(new PillHalf(3, 0, ColorOption.randomColor),
    new PillHalf(4, 0, ColorOption.randomColor)))
  private var _elements: Seq[Element] = (for (i <- 0 until 8; j <- 6 until 16; if math.random < odds) yield {
    new Virus(i, j, ColorOption.randomColor)
  })

  private var keysHeld = Set[KeyCode]()
  private var fallDelay = 0.0
  val fallInterval = 1.0
  private var moveDelay = 0.0
  val moveInterval = 0.1

  def currentPill = _currentPill
  def elements: Seq[Element] = currentPill +: _elements
  def update(delay: Double): Unit = {
    fallDelay += delay
    moveDelay += delay
    if (fallDelay >= fallInterval) {
      val movedPill = currentPill.move(0, 1, isClear)
      if (movedPill == _currentPill) {
        _elements +:= _currentPill
        _currentPill = _nextPill
        _nextPill = new Pill(Seq(new PillHalf(3, 0, ColorOption.randomColor),
          new PillHalf(4, 0, ColorOption.randomColor)))
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
  def isClear(x: Int, y: Int): Boolean = {
    y < 16 && x >= 0 && x < 8 && 
      !_elements.exists(e => e.cells.exists(c => c.x == x && c.y == y))
  }

  def keyPressed(keyCode: KeyCode): Unit = keysHeld += keyCode
  def keyReleased(keyCode: KeyCode): Unit = keysHeld -= keyCode
}
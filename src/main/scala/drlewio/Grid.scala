package drlewio

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Grid {
  val odds = 0.3
  private var _currentPill = new Pill(Seq(new PillHalf(3, 0, ColorOption.randomColor),
    new PillHalf(4, 0, ColorOption.randomColor)))
  private var _elements: Seq[Element] = (for (i <- 0 until 8; j <- 6 until 16; if math.random < odds) yield {
    new Virus(i, j, ColorOption.randomColor)
  })

  private var leftHeld = false
  private var rightHeld = false
  private var fallDelay = 0.0
  val fallInterval = 1.0

  def currentPill = _currentPill
  def elements: Seq[Element] = currentPill +: _elements
  def update(delay: Double): Unit = {
    fallDelay += delay
    if (fallDelay >= fallInterval) {
      _currentPill = currentPill.move(0, 1, (x,y) => y < 16)
      fallDelay = 0.0
    }
    if (leftHeld) _currentPill = currentPill.move(-1, 0, (x,y) => y < 16)
    if (rightHeld) _currentPill = currentPill.move(1, 0, (x,y) => y < 16)
  }
  def leftPressed(): Unit = leftHeld = true
  def rightPressed(): Unit =rightHeld = true
  def leftReleased(): Unit = leftHeld = false
  def rightReleased(): Unit = rightHeld = false
}
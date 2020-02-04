package drlewio

import scalafx.scene.canvas.GraphicsContext

class PillHalf(val x: Int, val y: Int, val color: ColorOption.Value) extends GridCell {
  def move(dx: Int, dy: Int): PillHalf = {
    new PillHalf(x + dx, y + dy, color)
  }
  def moveAllowed(dx: Int, dy: Int, isClear: (Int, Int) => Boolean): Boolean = {
    isClear(x + dx, y + dy)
  }
}
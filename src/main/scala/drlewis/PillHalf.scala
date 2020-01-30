package drlewis

import scalafx.scene.canvas.GraphicsContext

class PillHalf(val x: Int, val y: Int, val color: ColorOption.Value) extends GridCell {
  def draw(gc: GraphicsContext): Unit = ???
}
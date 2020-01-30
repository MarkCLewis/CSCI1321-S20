package drlewis

import scalafx.scene.canvas.GraphicsContext

trait GridCell {
  def x: Int
  def y: Int
  def color: ColorOption.Value
  def draw(gc: GraphicsContext): Unit
}
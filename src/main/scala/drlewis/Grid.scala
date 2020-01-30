package drlewis

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Grid {
  def render(gc: GraphicsContext): Unit = {
    gc.fill = Color.DarkGrey
    gc.fillRect(40,40,160,320)
  }

  def update(delay: Double): Unit = ???
}
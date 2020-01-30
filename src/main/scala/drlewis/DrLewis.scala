package drlewis

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

object DrLewis extends JFXApp {
  val grid = new Grid
  
  def draw(gc: GraphicsContext): Unit = {
    gc.fill = Color.Black
    gc.fillRect(0, 0, 800, 800)
    grid.render(gc)
  }

  stage = new JFXApp.PrimaryStage {
    title = "Dr. Lewis"
    scene = new Scene(800, 800) {
      val canvas = new Canvas(800, 800)
      val gc = canvas.graphicsContext2D
      content = canvas
      draw(gc)
    }
  }
}
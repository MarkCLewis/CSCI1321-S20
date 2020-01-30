package drlewis

class Virus(val x: Int, val y: Int, val color: ColorOption.Value) extends GridCell with Element {
  def cells: Seq[drlewis.GridCell] = ???
  
  def draw(gc: scalafx.scene.canvas.GraphicsContext): Unit = ???
}
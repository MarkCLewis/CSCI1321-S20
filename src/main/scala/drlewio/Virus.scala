package drlewio

class Virus(val x: Int, val y: Int, val color: ColorOption.Value) extends GridCell with Element {
  def cells: Seq[GridCell] = Seq(this)
  
  def move(dx: Int, dy: Int, isClear: (Int, Int) => Boolean): Element = this

  def buildPassable = PassableCell(x, y, color, 1)

  def selfSupported: Boolean = true

  def removeAll(victims: Set[GridCell]): Option[Element] = if (victims(this)) None else Some(this)
}
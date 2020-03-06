package drlewio

trait Element {
  def cells: Seq[GridCell]
  def move(dx: Int, dy: Int, isClear: (Int, Int) => Boolean): Element
  def selfSupported: Boolean
  def removeAll(victims: Set[GridCell]): Option[Element]
}
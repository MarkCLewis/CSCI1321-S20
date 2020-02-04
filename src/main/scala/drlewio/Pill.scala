package drlewio

class Pill(halves: Seq[PillHalf]) extends Element {
  def cells: Seq[GridCell] = halves

  def move(dx: Int, dy: Int, isClear: (Int, Int) => Boolean): Pill = {
    if(halves.forall(_.moveAllowed(dx, dy, isClear))) {
      new Pill(halves.map(_.move(dx, dy)))
    } else this
  }
}
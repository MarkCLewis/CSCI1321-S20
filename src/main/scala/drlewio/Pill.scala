package drlewio

class Pill(val halves: Seq[PillHalf]) extends Element {
  def cells: Seq[GridCell] = halves

  def move(dx: Int, dy: Int, isClear: (Int, Int) => Boolean): Pill = {
    if(halves.forall(_.moveAllowed(dx, dy, isClear))) {
      new Pill(halves.map(_.move(dx, dy)))
    } else this
  }

  def rotate(isClear: (Int, Int) => Boolean): Pill = {
    if (halves.length < 2) this
    else {
      val newPill = if (halves(0).y == halves(1).y) {
        val s = halves.sortBy(_.x)
        new Pill(Seq(s(0).move(1, 0), s(1).move(0, -1)))
      } else {
        val s = halves.sortBy(_.y)
        new Pill(Seq(s(0).move(-1, 1), s(1)))
      }
      if (newPill.halves.forall(ph => isClear(ph.x, ph.y))) newPill else this
    }
  }
}
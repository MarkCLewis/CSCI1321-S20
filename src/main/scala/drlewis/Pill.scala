package drlewis

class Pill(halves: Seq[PillHalf]) extends Element {
  def cells: Seq[GridCell] = halves
}
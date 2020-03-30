package drlewio

import scalafx.scene.input.KeyCode

object KeyData {
  val KeyPressed = 1001
  val KeyReleased = 1002
  val Up = 0
  val Down = 1
  val Left = 2
  val Right = 3

  val codeToInt: Map[KeyCode, Int] = Map(KeyCode.Up -> Up, KeyCode.Down -> Down, KeyCode.Left -> Left, KeyCode.Right -> Right)
}
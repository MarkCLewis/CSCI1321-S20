package drlewio

import scalafx.scene.input.KeyCode

object KeyData {
  val KeyPressed = 1001
  val KeyReleased = 1002

  val codeToInt: Map[KeyCode, Int] = Map(KeyCode.Up -> 0, KeyCode.Down -> 1, KeyCode.Left -> 2, KeyCode.Right -> 3)
}
package actors

import akka.actor.Actor
import scalafx.scene.image.WritableImage
import akka.actor.Props

class CrystalManager(img: WritableImage) extends Actor {
  for (_ <- 1 to 20) {
    context.actorOf(Props[FloatyBit])
  }
  for (child <- context.children) {
    child ! FloatyBit.Move(400, 0)
  }

  import CrystalManager._
  def receive = {
    case TestMove(x, y, ox, oy) =>
      if (x < 0 || x >= img.width.value || y < 0 || y >= img.height.value) {
        sender ! FloatyBit.Move(ox, oy)
      }
    case m => println("Unhandled message in CrystalManager: "+m)
  }
}

object CrystalManager {
  case class TestMove(x: Int, y: Int, ox: Int, oy: Int)
}
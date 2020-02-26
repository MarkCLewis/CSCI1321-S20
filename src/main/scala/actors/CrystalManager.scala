package actors

import akka.actor.Actor
import scalafx.scene.image.WritableImage
import akka.actor.Props
import scalafx.scene.paint.Color
import scalafx.application.Platform

class CrystalManager(img: WritableImage) extends Actor {
  val writer = img.pixelWriter
  val reader = img.pixelReader.get
  for (i <- 0 until img.width.value.toInt) writer.setColor(i, img.height.value.toInt - 1, Color.Black)

  for (_ <- 1 to 20) {
    context.actorOf(Props[FloatyBit])
  }
  for (child <- context.children) {
    child ! FloatyBit.Move((util.Random.nextGaussian * 100 + 400).toInt min 799 max 0, 0)
  }

  import CrystalManager._
  def receive = {
    case TestMove(x, y, ox, oy) =>
      if (x < 0 || x >= img.width.value || y < 0 || y >= img.height.value) {
        sender ! FloatyBit.Move(ox, oy)
      } else if (reader.getColor(x, y) == Color.Black) {
        Platform.runLater(writer.setColor(ox, oy, Color.Black))
        sender ! FloatyBit.Move((util.Random.nextGaussian * 100 + 400).toInt min 799 max 0, 0)
      } else {
        sender ! FloatyBit.Move(x, y)
      }
    case m => println("Unhandled message in CrystalManager: "+m)
  }
}

object CrystalManager {
  case class TestMove(x: Int, y: Int, ox: Int, oy: Int)
}
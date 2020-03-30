package drlewio

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.canvas.Canvas
import scalafx.scene.Scene
import scalafx.scene.input.KeyEvent
import java.net.Socket
import java.io.ObjectOutputStream
import java.io.ObjectInputStream
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Client extends JFXApp {
  val canvas = new Canvas(800, 800)
  val gc = canvas.graphicsContext2D
  val renderer = new Renderer(gc)

  val sock = new Socket("localhost", 8000)
  val oos = new ObjectOutputStream(sock.getOutputStream())
  val ois = new ObjectInputStream(sock.getInputStream())

  Future {
    while(true) {
      val pg = ois.readObject() match {
        case g: PassableGrid => g
      }
      renderer.render(pg)
    }
  }

  stage = new JFXApp.PrimaryStage {
    title = "Dr. Lewio"
    scene = new Scene(800, 800) {
      content = canvas

      onKeyPressed = (ke: KeyEvent) => {
        println("Sending press.")
        oos.writeInt(KeyData.KeyPressed)
        oos.writeInt(KeyData.codeToInt(ke.code))
        oos.flush()
      }
      onKeyReleased = (ke: KeyEvent) => {
        println("Sending released.")
        oos.writeInt(KeyData.KeyReleased)
        oos.writeInt(KeyData.codeToInt(ke.code))
        oos.flush()
      }
    }
  }

}

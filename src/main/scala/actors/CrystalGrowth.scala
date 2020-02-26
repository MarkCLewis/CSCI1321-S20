package actors

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.WritableImage
import scalafx.scene.image.ImageView
import akka.actor.ActorSystem
import akka.actor.Props

object CrystalGrowth extends JFXApp {
  val img = new WritableImage(800, 800)
  val system = ActorSystem("Crystals")
  val manager = system.actorOf(Props(new CrystalManager(img)))

  stage = new JFXApp.PrimaryStage {
    title = "Crystals!"
    scene = new Scene(800, 800) {
      val viewer = new ImageView(img)
      content = viewer
      onCloseRequest = e => {
        system.terminate()
      }
    }
  }
}
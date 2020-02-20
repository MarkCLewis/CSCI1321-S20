package multithreading

import basics.Complex
import scalafx.Includes._
import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.image.WritableImage
import scalafx.scene.image.ImageView
import scalafx.scene.paint.Color
import scalafx.scene.input.KeyCode
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalafx.application.Platform

class Julia(c: Complex) {
  private var maxCount = 10000
  private var realMin = -2.0
  private var realMax = 2.0
  private var imaginaryMin = -2.0
  private var imaginaryMax = 2.0

  val stage = new Stage {
    title = s"Julia ${c.r}+${c.i}i"
    scene = new Scene(1000, 1000) {
      val wimg = new WritableImage(1000, 1000)
      val view = new ImageView(wimg)
      content = view
      calcJulia(wimg)

      onMouseClicked = me => {
        val x = realMin + me.x * (realMax - realMin) / wimg.width.value
        val y = imaginaryMin + me.y * (imaginaryMax - imaginaryMin) / wimg.height.value
          val realDelta = (realMax - realMin) * 0.05
          val imaginaryDelta = (imaginaryMax - imaginaryMin) * 0.05
          realMin = x - realDelta
          realMax = x + realDelta
          imaginaryMin = y - imaginaryDelta
          imaginaryMax = y + imaginaryDelta
          maxCount *= 2
          println(s"zoom at $x+${y}i")
          calcJulia(wimg)
      }

      onKeyPressed = ke => {
        if (ke.code == KeyCode.Space) {
          realMin = -2.0
          realMax = 2.0
          imaginaryMin = -2.0
          imaginaryMax = 2.0
          maxCount = 10000
          calcJulia(wimg)
        }
      }

    }
  }
  stage.show()

  def calcJulia(wimg: WritableImage): Unit = {
    val writer = wimg.pixelWriter
    val start = System.nanoTime()
    val colorFutures = for (i <- 0 until wimg.width.value.toInt) yield Future {
      val x = realMin + i * (realMax - realMin) / wimg.width.value
      for (j <- 0 until wimg.height.value.toInt) yield {
        val y = imaginaryMin + j * (imaginaryMax - imaginaryMin) / wimg.height.value
        val cnt = juliaCount(Complex(x, y))
        val color = if (cnt >= maxCount) Color.Black else Color(math.log(cnt + 1) / math.log(maxCount + 1), 0.0, 0.0, 1.0)
        (i, j, color)
      }
    }
    Future.sequence(colorFutures).foreach { colors => 
      Platform.runLater(
        for (col <- colors; (i, j, color) <- col) {
          writer.setColor(i, j, color)
        }
      )
      println("Time taken: " + (System.nanoTime() - start)*1e-9)
    }
  }

  def juliaCount(z0: Complex): Int = {
    var cnt = 0
    var z = Complex(z0.r, z0.i)
    while (cnt < maxCount && z.magSqr < 9) {
      cnt += 1
      z = z * z + c
    }
    cnt
  }
}
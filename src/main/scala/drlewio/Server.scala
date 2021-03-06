package drlewio

import java.net.ServerSocket
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import scala.collection.mutable
import java.net.Socket
import java.util.concurrent.LinkedBlockingQueue

case class PlayerConnection(grid: Grid, sock: Socket, in: ObjectInputStream, out: ObjectOutputStream)

object Server extends App {
  val connections = mutable.Buffer[PlayerConnection]()
  val queue = new LinkedBlockingQueue[PlayerConnection]()

  val ss = new ServerSocket(8000)
  Future {
    while(true) {
      val sock = ss.accept()
      val ois = new ObjectInputStream(sock.getInputStream())
      val oos = new ObjectOutputStream(sock.getOutputStream())
      queue.put(PlayerConnection(new Grid, sock, ois, oos))
    }
  }

  val drawInterval = 0.05
  var drawDelay = 0.0
  var lastTime = -1L
  while(true) {
    while (!queue.isEmpty()) {
      connections += queue.take()
    }
    val time = System.nanoTime()
    if (lastTime >= 0) {
      val delay = (time - lastTime)/1e9
      drawDelay += delay
      // println(s"Delay = $delay")
      for (pc <- connections) {
        // First check if they have input to handle
        if (pc.in.available() > 0) {
          println("Reading key data.")
          pc.in.readInt() match {
            case KeyData.KeyPressed => pc.grid.keyPressed(pc.in.readInt())
            case KeyData.KeyReleased => pc.grid.keyReleased(pc.in.readInt())
            case x => println(s"Oops! Got $x for the type of key interaction.")
          }
        }

        // Then update the grid
        pc.grid.update(delay)

        // Lastly send the grid if it is time
        if (drawDelay > drawInterval) {
          val pg = pc.grid.buildPassable
          pc.out.writeObject(pg)
          pc.out.flush()
        }
      }
      if(drawDelay > drawInterval) drawDelay = 0.0
    }
    lastTime = time
  }
}
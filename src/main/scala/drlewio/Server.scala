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

  var lastTime = -1L
  while(true) {
    while (!queue.isEmpty()) {
      connections += queue.take()
    }
    val time = System.nanoTime()
    if (lastTime >= 0) {
      val delay = (time - lastTime)/1e9
      for (pc <- connections) {
        pc.grid.update(delay)
        val pg = pc.grid.buildPassable
        pc.out.writeObject(pg)
      }
    }
    lastTime = time
  }
}
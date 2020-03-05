package networking

import akka.actor.Actor
import java.net.Socket
import java.io.BufferedReader
import java.io.PrintStream

class User(name: String, sock: Socket, in: BufferedReader, out: PrintStream) extends Actor {
  import User._
  def receive = {
    case CheckInput =>
      if(in.ready) {
        val input = in.readLine()
        sender ! ChatManager.SendToAll(name + ": " + input)
      }
    case PrintMessage(msg) => out.println(msg)
    case m => println("Unhandled message in User: " + m)
  }
}

object User {
  case object CheckInput
  case class PrintMessage(msg: String)
}
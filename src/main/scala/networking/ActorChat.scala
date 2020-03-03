package networking

import akka.actor.ActorSystem
import akka.actor.Props
import java.net.ServerSocket
import java.io.PrintStream
import java.io.InputStreamReader
import java.io.BufferedReader
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object ActorChat extends App {
  val system = ActorSystem("ActorChat")
  val manager = system.actorOf(Props[ChatManager], "ChatManager")
  system.scheduler.schedule(1.seconds, 0.1.seconds, manager, ChatManager.CheckAllInputs)

  val ss = new ServerSocket(4040)
  while(true) {
    val sock = ss.accept()
    Future {
      val out = new PrintStream(sock.getOutputStream())
      out.println("What is your name?")
      val in = new BufferedReader(new InputStreamReader(sock.getInputStream()))
      val name = in.readLine()
      manager ! ChatManager.NewUser(name, sock, in, out)
    }
  }
}
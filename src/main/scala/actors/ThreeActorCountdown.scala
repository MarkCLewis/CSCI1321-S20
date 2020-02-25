package actors

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef

object ThreeActorCountdown {
  class CountingActor extends Actor {
    def receive = {
      case StartCounting(n, next, nextNext) =>
        println(n)
        next ! CountDown(n - 1, nextNext)
      case CountDown(n, next) =>
        println(n)
        if (n > 0) next ! CountDown(n - 1, sender)
        else context.system.terminate()
      case m => println("Unhandled message in Counting actor: " + m)
    }
  }

  case class StartCounting(n: Int, next: ActorRef, nextNext: ActorRef)
  case class CountDown(n: Int, next: ActorRef)

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Counting")
    val actor1 = system.actorOf(Props[CountingActor], "Actor1")
    val actor2 = system.actorOf(Props[CountingActor], "Actor2")
    val actor3 = system.actorOf(Props[CountingActor], "Actor3")
    actor1 ! StartCounting(10, actor2, actor3)
  }
}
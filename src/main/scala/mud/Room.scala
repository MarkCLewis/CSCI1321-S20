package mud

import akka.actor.Actor
import akka.actor.ActorRef

class Room(val name: String, val key: String, val description: String, 
        private var items: List[Item], private val exitNames: Array[String]) extends Actor {

    private var exits: Array[Option[ActorRef]] = null

    import Room._
    def receive = {
        case LinkRooms(links) => exits = exitNames.map(links.get)
        case PrintDescription => // sender ! Player.PrintMessage(desc())
        case GetExit(dir) => // sender ! Player.TakeExit(getExit(dir))
        case GetItem(itemName) => // sender ! Player.TakeItem(getItem(itemName))
        case DropItem(item) => dropItem(item)
        case m => println("Unhandled message in Room: " + m)
    }

    def desc(): String = {

        var north = ""
        if(exits(0) != None) north = "North "
        var south = ""
        if(exits(1) != None) south = "South "
        var east = ""
        if(exits(2) != None) east = "East "
        var west = ""
        if(exits(3) != None) west = "West "
        var up = ""
        if(exits(4) != None) up = "Up "
        var down = ""
        if(exits(5) != None) down = "Down"

        var exitString = north + south + east + west + up + down

        var inroom = ""

        for(index <- 0 until items.size){
            inroom = inroom + items(index).name + " "
        }

        name + "\n" + description + "\n" + "Exits: " + exitString + "\n" + "Items: " + inroom + "\n"
    }

    def getExit(dir: Int): Option[ActorRef] = {
        exits(dir)
    }

    def getItem(itemName: String): Option[Item] = {
        items.find(_.name.toLowerCase == itemName.toLowerCase) match {
            case Some(item) =>
                items = items.filter(_ != item)
                Some(item)
            case None => None
        }
    }

    def dropItem(item: Item): Unit = items ::= item

}

object Room {
    case class LinkRooms(links: Map[String, ActorRef])
    case object PrintDescription
    case class GetExit(dir: Int)
    case class GetItem(itemName: String)
    case class DropItem(item: Item)
}
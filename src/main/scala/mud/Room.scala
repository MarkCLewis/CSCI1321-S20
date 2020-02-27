package mud

import akka.actor.Actor
import akka.actor.ActorRef

class Room(val name: String, val key: String, val description: String, private var items: List[Item], private val _exit: Array[String]) extends Actor {

    def receive = {
        case m => println("Unhandled message in Room: " + m)
    }

    def exit(index: Int) = _exit(index)

    def desc(): String = {

        var north = ""
        if(exit(0) != "-1") north = "North "
        var south = ""
        if(exit(1) != "-1") south = "South "
        var east = ""
        if(exit(2) != "-1") east = "East "
        var west = ""
        if(exit(3) != "-1") west = "West "
        var up = ""
        if(exit(4) != "-1") up = "Up "
        var down = ""
        if(exit(5) != "-1") down = "Down"

        var exits = north + south + east + west + up + down

        var inroom = ""

        for(index <- 0 until items.size){
            inroom = inroom + items(index).name + " "
        }

        name + "\n" + description + "\n" + "Exits: " + exits + "\n" + "Items: " + inroom + "\n"
    }

    def getExit(dir: Int): Option[Room] = {
        if(exit(dir) == "-1"){
            None
        }
        else{
            Some(Room.rooms(exit(dir)))
        }
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
    
}
package de.bastigram.telegram.actors

import akka.actor.{Actor, Props}
import com.github.nikdon.telepooz.model.Message

class ChatActor extends Actor {
  override def receive = {
    case m : Message => println("received")
  }
}

object ChatActor {

  def props() : Props = Props(new ChatActor)

}
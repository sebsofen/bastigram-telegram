package de.bastigram.telegram.actors

import akka.Done
import akka.actor.{Actor, Props}
import com.github.nikdon.telepooz.model.Message

object ChatActorSupervisor {
  def props(): Props = Props(new ChatActorSupervisor())
}

class ChatActorSupervisor() extends Actor {

  override def receive = {

    case m: Message =>
      val msender = sender()
      val actorName = "chat:" + m.chat.id
      val chatActor = context
        .child(actorName)
        .getOrElse(context.actorOf(ChatActor.props(), actorName))
      chatActor ! m
      msender ! Done

  }
}

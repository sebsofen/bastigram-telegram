package de.bastigram.telegram

import akka.Done
import com.github.nikdon.telepooz.engine._
import com.github.nikdon.telepooz.model.Message
import de.bastigram.telegram.actors.ChatActorSupervisor
import scala.concurrent.duration._
import akka.pattern.ask
import scala.concurrent.{ExecutionContext, Future}

object Application extends App with Telepooz {

  implicit val are = new ApiRequestExecutor {}
  implicit val timeout = akka.util.Timeout(5 seconds)

  val receiver = new Reactor {

    val chatActorSupervisor =
      system.actorOf(ChatActorSupervisor.props(), "ChatSupervisor")
    val poller = new Polling

    instance.run((are, poller, this))
    override val reactions = new Reactions {
      override def react(m: Message)(
          implicit ec: ExecutionContext): Future[Done.type] =
        (chatActorSupervisor ? m).map(_ => Done)
    }
  }
}

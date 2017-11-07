package de.bastigram.telegram.actors

import akka.actor.ActorRef
import akka.event.{EventBus, ScanningClassification}
import com.github.nikdon.telepooz.model.Message

class MessagesBus extends EventBus with ScanningClassification {
  override type Event = Message
  override type Classifier = MessageChatIDClassifier
  override type Subscriber = ActorRef

  override protected def compareClassifiers(a: Classifier, b: Classifier): Int = a.toString compare b.toString

  override protected def compareSubscribers(a: Subscriber, b: Subscriber): Int = a.toString().compareTo(b.toString())

  override protected def matches(classifier: Classifier, event: Event): Boolean = classifier.classify(event)

  override protected def publish(event: Event, subscriber: Subscriber): Unit = subscriber ! event

}

abstract class MessageClassifier {
  def classify(msg: Message) : Boolean
}
case class MessageChatIDClassifier(chatId: Long) extends MessageClassifier {
  override def classify(msg: Message) = msg.chat.id == chatId
}



package com.knoldus.persistence

import akka.actor.Props
import akka.persistence.journal.Tagged
import akka.persistence.{PersistentActor, SnapshotOffer}
import com.knoldus.models._
import com.knoldus.persistence.CounterPersistentActor.Response

class CounterPersistentActor(id: String, tag: Option[String] = None) extends PersistentActor {

  override val persistenceId: String = id
  var state = State(count = 0)

  def updateState(event:Event) {
    event match {
      case Event(Increment(count)) => state = State(state.count + count)
      case Event(Decrement(count)) => state = State(state.count - count)
    }
  }

  override def receiveRecover: Receive = {
    case event: Event =>
      println(s"Actor is currently recovering its state")
      updateState(event)
    case SnapshotOffer(_, snapshot: State) =>
      println(s"Snapshot data: $snapshot")
      state = snapshot
  }

  override def receiveCommand: Receive = {
    case command @ Command(op) =>
      println(s"$command is under process")
      if(tag.isEmpty) {
        persist(Event(op)) { event =>
          updateState(event)
          sender() ! Response("Done Processing")
        }
      } else {
        persist(Tagged(Event(op), Set("tag2"))) { event =>
          updateState(event.payload.asInstanceOf[Event])
          sender() ! Response("Done Processing")
        }
      }
    case Checkpoint =>
      println(s"Current State: ${state.count}")
      sender() ! Response(s"Current State: ${state.count}")
  }

}

object CounterPersistentActor {

  def props(id: String) = Props(new CounterPersistentActor(id))

  case class Response(message: String)

}

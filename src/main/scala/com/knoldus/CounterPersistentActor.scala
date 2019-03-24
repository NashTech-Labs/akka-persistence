package com.knoldus

import akka.persistence.{PersistentActor, SnapshotOffer}
import com.knoldus.models._

class CounterPersistentActor extends PersistentActor {

  override val persistenceId: String = "counter-actor"
  var state = State(count = 0)

  def updateState(event:Event) = {
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
      persist(Event(op)) { event =>
        updateState(event)
      }
    case Checkpoint => println(s"Current State: ${state.count}")
  }

}

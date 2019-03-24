package com.knoldus

import akka.actor.{ActorSystem, Props}
import com.knoldus.models.{Checkpoint, Command, Decrement, Increment}
import com.knoldus.persistence.CounterPersistentActor

object Main extends App {

  val system = ActorSystem("PersistenceActor")

  val counterPersistentActor = system.actorOf(CounterPersistentActor.props("counter-actor"), "CounterPersistentActor")

  counterPersistentActor ! Command(Increment(3))
  counterPersistentActor ! Command(Increment(4))
  counterPersistentActor ! Command(Decrement(2))
  counterPersistentActor ! Checkpoint

  Thread.sleep(1000)

  system.terminate()

}

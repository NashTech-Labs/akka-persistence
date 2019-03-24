package com.knoldus.persistence

import akka.actor.{ActorSystem, PoisonPill}
import akka.testkit.{ImplicitSender, TestKit}
import com.knoldus.models._
import com.knoldus.persistence.CounterPersistentActor.Response
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class CounterPersistentActorSpec
  extends TestKit(ActorSystem("ShoppingCartActorSpec"))
    with WordSpecLike
    with Matchers
    with BeforeAndAfterAll
    with ImplicitSender {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "CounterPersistentActor" should {

    "be able to increment the count" in {
      val counterPersistentActor = system.actorOf(CounterPersistentActor.props("test-actor"))
      counterPersistentActor ! Command(Increment(5))
      expectMsg(Response("Done Processing"))

      counterPersistentActor ! PoisonPill

      val counterActor = system.actorOf(CounterPersistentActor.props("test-actor"))
      counterActor ! Checkpoint
      expectMsg(Response(s"Current State: 5"))

    }

    "be able to decrement the count" in {
      val counterPersistentActor = system.actorOf(CounterPersistentActor.props("test-actor"))
      counterPersistentActor ! Command(Decrement(5))
      expectMsg(Response("Done Processing"))

      counterPersistentActor ! PoisonPill

      val counterActor = system.actorOf(CounterPersistentActor.props("test-actor"))
      counterActor ! Checkpoint
      expectMsg(Response(s"Current State: -5"))
    }
  }

}

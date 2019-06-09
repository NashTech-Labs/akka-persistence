package com.knoldus.persistence.query

import akka.NotUsed
import akka.actor.ActorSystem
import akka.persistence.query.{EventEnvelope, PersistenceQuery}
import akka.persistence.query.journal.leveldb.scaladsl.LeveldbReadJournal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

object EventsByPersistenceIdQuery extends App {

  val system = ActorSystem("PersistentQuery")
  val queries = PersistenceQuery(system).readJournalFor[LeveldbReadJournal](LeveldbReadJournal.Identifier)

  implicit val mat = ActorMaterializer()(system)

  val eventsByPersistenceIdSource: Source[EventEnvelope, NotUsed] =
    queries.eventsByPersistenceId("counter-actor", 0L, Long.MaxValue)


  eventsByPersistenceIdSource.runForeach { event =>
    println("Event: " + event)
  }

}

package com.knoldus.persistence.query
import akka.NotUsed
import akka.actor.ActorSystem
import akka.persistence.query.PersistenceQuery
import akka.persistence.query.journal.leveldb.scaladsl.LeveldbReadJournal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

object PersistenceIdsQuery extends App {

  val system = ActorSystem("PersistentQuery")
  val queries = PersistenceQuery(system).readJournalFor[LeveldbReadJournal](LeveldbReadJournal.Identifier)

  implicit val mat = ActorMaterializer()(system)

  val persistenceIdsSource: Source[String, NotUsed] = queries.persistenceIds()

  persistenceIdsSource.runForeach { event =>
    println(event)
  }

}

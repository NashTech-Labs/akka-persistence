package com.knoldus.models

sealed trait Operation

case class Increment(counter: Int) extends Operation
case class Decrement(counter: Int) extends Operation

case object Checkpoint extends Operation

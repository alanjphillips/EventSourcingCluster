package com.alaphi.userservice

import akka.actor.{ActorRef, ActorSystem}

object Boot extends App {

  val system = ActorSystem("UserServiceCluster")

  val userRegion: ActorRef = UserShardingRegion.start(system, 10)

  // Pass userRegion to Kafka Consumer flow where it will deal with incoming messages

}

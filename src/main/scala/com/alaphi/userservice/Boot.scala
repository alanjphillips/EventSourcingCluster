package com.alaphi.userservice

import akka.actor.{ActorRef, ActorSystem}

object Boot extends App {

  val system = ActorSystem("UserServiceCluster")

  val userRegion: ActorRef = UserShardingRegion.start(system, 10)

  val consumer = system.actorOf(KafkaConsumerActor.props(userRegion))

}

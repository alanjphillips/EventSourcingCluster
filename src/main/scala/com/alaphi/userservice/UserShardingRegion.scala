package com.alaphi.userservice

import akka.actor.{ActorRef, ActorSystem}
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings, ShardRegion}

object UserShardingRegion {

  def start(system: ActorSystem, numberOfShards: Int): ActorRef = {
    ClusterSharding(system).start(
      typeName = "UserShardingRegion",
      entityProps = UserActor.props(),
      settings = ClusterShardingSettings(system),
      extractEntityId = idExtractor,
      extractShardId = shardResolver(numberOfShards))
  }

  def idExtractor: ShardRegion.ExtractEntityId = {
    case cmd: Command => (cmd.name, cmd)
  }

  def shardResolver(numberOfShards: Int): ShardRegion.ExtractShardId = {
    case cmd: Command => (math.abs(cmd.name.hashCode) % numberOfShards).toString
  }

}

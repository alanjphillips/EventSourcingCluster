Event Sourcing Cluster Demo
============================================
Horizontally scaled out use of Akka Cluster Sharding with Cassandra Cluster configured for persistence. Messages are consumed from Kafka and persisted to Cassandra using akka-persistence

Consumer-side
-------------
Multiple Kafka Partitions
Multiple Consuming Cluster Sharded microservices with Kafka consumer
Multiple Cassandra nodes

Producer-side
-------------
Http interface to take publish commands
Send generated Kafka messages using Kafka producer


Install docker, docker-machine and docker-compose. See docker docs on how to create machine in virtualbox
https://docs.docker.com/machine/drivers/virtualbox/

Create a VM called 'default'

> docker-machine create --driver virtualbox default

Or for a beefier machine

> docker-machine create --driver virtualbox --virtualbox-memory "8192" --virtualbox-cpu-count "2" default

Start up 'default' machine

> docker-machine start default

1) Connect to 'default' machine

> eval "$(docker-machine env default)"

2) CD into project and use SBT to build and publish to local Docker repo:

> sbt clean docker:publishLocal

3) Run docker compose to launch Cluster Sharded App and Cassandra which is used for persistence

> docker-compose up -d --no-recreate

> docker-compose scale kafka=2 cassandra-node=2

4) Connect to Cassandra and view persisted messages

>  cqlsh 192.168.99.100 --cqlversion="3.4.4"

cqlsh> describe tables

cqlsh> select * from akka.messages;
package com.zq.scala.并发编程Akka框架

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

/**
  * Created by zhangqiang on 2017/6/7.
  */
object 创建Actor extends App {

  //  定义一个Actor
  class StringActor extends Actor {
    val log = Logging(context.system, this)

    override def receive: Receive = {
      case s: String => log.info("received message " + s)
      case _ => log.info("received unknown message")
    }
  }

  // 创建ActorSystem
  val system = ActorSystem("StringSystem")

  // 构造actor实例
  val stringActor = system.actorOf(Props[StringActor], name = "StringActor")

  // 给stringActor发送字符串消息
  stringActor ! "Creating Actors with default constructor"

}

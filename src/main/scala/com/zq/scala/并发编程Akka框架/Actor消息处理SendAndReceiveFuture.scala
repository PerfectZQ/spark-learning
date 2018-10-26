package com.zq.scala.并发编程Akka框架

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.event.Logging
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * Created by zhangqiang on 2017/6/7.
  */
object Actor消息处理SendAndReceiveFuture extends App {

  case class BasicInfo(id: Int, name: String, age: Int)

  case class InterestInfo(id: Int, interest: String)

  case class Person(basicInfo: BasicInfo, interestInfo: InterestInfo)

  class BasicInfoActor extends Actor {

    // Stores the context for this actor, including self, and sender.
    // implicit val context: ActorContext
    val log = Logging(context.system, this)

    override def receive: Receive = {
      case id: Int => log.info(s"id = $id")
        // final def sender(): ActorRef = context.sender()
        sender !  BasicInfo(id, "Join", 19)
      case _ => log.info("Received unknown message")
    }
  }

  class InterestInfoActor extends Actor {
    val log = Logging(context.system, this)

    override def receive: Receive = {
      case id: Int => log.info(s"id = $id")
        sender !  InterestInfo(id, "篮球")
      case _ => log.info("Receive unknown message")
    }
  }

  class PersonActor extends Actor {
    val log = Logging(context.system, this)

    override def receive: Receive = {
      case person: Person => log.info(s"person = $person")
      case _ => log.info("Receive unknown message")
    }
  }

  class CombineActor extends Actor {
    // import scala.concurrent.duration._
    implicit val timeout: Timeout = Timeout(5 seconds)

    val basicInfoActor: ActorRef = context.actorOf(Props[BasicInfoActor], "BasicInfoActor")
    val interestInfoActor: ActorRef = context actorOf(Props[InterestInfoActor], "InterestInfoActor")
    val personActor: ActorRef = context actorOf(Props[PersonActor], "PersonActor")

    import akka.pattern.ask
    import akka.pattern.pipe

    override def receive: Receive = {
      case id: Int =>
        val combineResult: Future[Person] =
          for {
          // 向basicInfoActor发送Send-And-Receive-Future信息,mapTo方法将返回结果映射为BasicInfo类型
          // 需要 import scala.concurrent.ExecutionContext.Implicits.global
            basicInfo <- ask(basicInfoActor, id)
              .mapTo[BasicInfo]
            interestInfo <- ask(interestInfoActor, id)
              .mapTo[InterestInfo]
          } yield Person(basicInfo, interestInfo)

        // 将Future结果发送给PersonActor
        pipe(combineResult).to(personActor)
    }
  }

  val _system = ActorSystem("Send-And-Receive-Future")
  val combineActor = _system.actorOf(Props[CombineActor], "CombineActor")
  combineActor ! 100001
  Thread.sleep(5000)
  //  _system.shutdown()
}

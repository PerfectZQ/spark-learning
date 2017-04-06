package com.zq.scala.模式匹配

/**
  * Created by zhangqiang on 2017/1/6.
  *
  * 如果只定义一个普通的类，则需要手动定义这个类的伴生对象，并且要在伴生对象中实现unapply方法，才能使用这个类
  * 的构造函数模式匹配。但是，若是将类定义成样例类，编译器会自动帮我们创建其伴生对象并实现相应的unapply方法，
  * 从而避免大量手动编写代码所带来的程序复杂性，模式匹配与样例类是一堆孪生兄弟，在模式匹配中经常一起使用
  */
object 模式匹配与样例类和样例对象 extends App {

  /**
    * 模式匹配与样例类
    */
  // 当被 trait 被定义成 sealed 时，handleMessage 方法中缺少任意子类的匹配项，编译器都会发出警告
  // 重新编译的快捷键 ctrl + shift + f9
  sealed trait DeployMessage

  // 定义其三个子类
  case class RegisterWorker(id: String, host: String, port: Int) extends DeployMessage

  case class UnRegisterWorker(id: String, host: String, port: Int) extends DeployMessage

  case class HeartBeat(workerId: String) extends DeployMessage

  // handleMessage会处理所有可能的情况，穷举所有DeployMessage的子类
  def handleMessage(msg: DeployMessage) = {
    msg match {
      case RegisterWorker(id, host, port) => println(s"id = $id")
      case UnRegisterWorker(id, host, port) => println(s"id = $id")
      case HeartBeat(workerId) => println(s"workerId = $workerId")
      case RequestWorkerState => println("Request Worker State")
    }
  }

  handleMessage(RegisterWorker("204799", "127.0.0.1", 8080))
  handleMessage(UnRegisterWorker("204799", "127.0.0.1", 8080))

  /**
    * 模式匹配与样例对象
    * 在上面的代码中，我们给DeployMessage特质定义了三个子类，他们都有自己的成员域
    * 但在实际开发过程中，DeployMessage特质的子类可能不需要有自己的成员域，而只是
    * 用于消息的标识，如果此时仍然将其定义为case类的话，编译器会出警告，会告诉你
    * case classes without a parameter list are not allowed
    * 这时候推荐使用样例对象来声明RequestWorkerState
    */
  //  case class Something extends DeployMessage
  case object RequestWorkerState extends DeployMessage
  // case object 不需要像 case class 一样需要先创建对象
  handleMessage(RequestWorkerState)

  /**
    * 为什么推荐使用case object 而不推荐使用case class呢？
    * 1、在模式匹配应用时，case class需要先创建对象，而case object不需要
    * 2、case class会生成两个字节码文件，而case object只会生成一个
    * 3、case class会自动生成伴生对象，而且会自动实现apply和unapply方法，而case object不会
    * 综上，使用case object可以提升程序的执行速度，减少编译器的额外开销
    */
}

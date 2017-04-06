package com.zq.scala.面向对象编程下

import java.io.PrintWriter

/**
  * Created by zhangqiang on 2016/12/27.
  */
object 提前定义和懒加载 extends App {

  trait Logger {
    def log(msg: String): Unit
  }

  class Person

  /**
    * 第一种：很不优雅的-提前定义
    */
  trait FileLogger extends Logger {

    // 抽象成员变量
    val fileName: String

    val fileOutput = new PrintWriter(fileName)

    fileOutput.println("#")

    override def log(msg: String): Unit = {
      fileOutput.print(msg)
      fileOutput.flush()
    }
  }

  class Programmer extends Person with FileLogger {

    // 当调用Programmer构造函数的时候会因为构造函数的执行顺序问题，先调用父类的构造函数中的方法
    // 此时父类抽象成员变量fileName为空，然后执行val fileOutput = new PrintWriter(fileName)
    // 会报空指针异常，此时就需要提前定义与懒加载
    override val fileName: String = "file.log"
  }

  val p = new {
    override val fileName = "file.log"
  } with Programmer

  p.log("predefined variable")

  /**
    * 第二种： 优雅的-懒加载
    */
  trait LazyFileLogger extends Logger {

    // 抽象成员变量
    val fileName: String
    // 定义为懒加载执行，创建对象时不会执行这条语句，当真正使用变量fileOutput时才会执行
    lazy val fileOutput = new PrintWriter(fileName)

    //    fileOutput.println("#")

    override def log(msg: String) = {
      fileOutput.println(msg)
      fileOutput.flush()
    }
  }

  class AdvancedProgrammer extends Person with LazyFileLogger {
    override val fileName: String = "lazyFile.log"
  }

  val advancedProgrammer = new AdvancedProgrammer
  // 当执行这条语句时，AdvancedProgrammer已经对fileName完成了初始化，然后调用log方法，在log方法中使用了fileOutput变量
  // 然后真正执行 val fileOutput = new PrintWriter(fileName) 因此不会有空指针异常
  advancedProgrammer.log("lazy load")
}

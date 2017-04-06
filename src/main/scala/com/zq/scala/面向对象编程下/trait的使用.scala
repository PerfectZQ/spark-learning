package com.zq.scala.面向对象编程下

/**
  * Created by zhangqiang on 2016/12/27.
  */
object trait的使用 extends App {

  case class Person(var name: String, var age: Int, var id: Int)

  /**
    * 定义一个trait
    * trait与java中的interface不同，trait更接近java中的抽象类
    * 因为trait中可以定义抽象成员变量，抽象成员方法，具体成员变量和具体成员方法
    * 而interface只能定义具体成员变量和抽象成员方法
    *
    * 含有具体成员变量或方法的trait与只有抽象成员变量或方法的trait不同的是：PersonDAO最终会生成两个字节码文件
    * PersonDAO$class.class和PersonDAO.class
    */
  trait PersonDAO {

    // 抽象成员变量
    var aaa: Int
    // 具体成员变量
    var recordNum: Long = 0L

    // 抽象成员方法
    def add(p: Person)

    // 抽象成员方法
    def delete(id: Int)

    // 抽象成员方法
    def update(p: Person)

    // 抽象成员方法
    def select(id: Int)

    // 具体成员方法
    def print = println("具体成员方法")
  }

  class PersonDAOImpl extends PersonDAO {

    // 实现抽象成员变量和方法
    override var aaa: Int = _

    override def add(p: Person): Unit = println("invoking add()...")

    override def delete(id: Int): Unit = println("invoking delete()...")

    override def update(p: Person): Unit = println("invoking update()...")

    override def select(id: Int): Unit = println("invoking select()...")
  }

  /**
    * 混入trait的类对象构造
    */

  import java.io.PrintWriter

  trait Logger {
    println("Logger")

    def log(msg: String)
  }

  trait FileLogger extends Logger {

    // 构造方法的执行顺序
    // 如果有超类，则先调用超类的构造函数
    // 如果混入的trait有父trait，它会按照继承层次先调用父trait的构造函数
    // 如果同一层有多个父trait，则按顺序从左到右执行
    // 所有父类构造函数和父trait被构造完之后，才会构造本类的构造函数
    println("FileLogger")
    val fileOutput = new PrintWriter("file.log")
    fileOutput.println("#")

    def log(msg: String) = {
      fileOutput.print(msg)
      fileOutput.flush()
    }
  }

  // 匿名类
  new FileLogger {}.log("trait")
}

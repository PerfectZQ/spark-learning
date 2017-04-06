package com.zq.scala.面向对象编程上

/**
  * Created by zhangqiang on 2016/12/27.
  *
  * 匿名类仍然会单独生成相应的字节码文件
  */
object 匿名类 extends App{

  abstract class Person(var name: String, var age: Int) {
    // 抽象类中的方法声明
    def print: Unit
  }

  val person = new Person("Spider Man", 22) {
    // 抽象类中的方法声明
//    override def print(): Unit = println(name + " : " + age)
    override def print: Unit = println(s"Person($name,$age)")
  }

  person.print
}

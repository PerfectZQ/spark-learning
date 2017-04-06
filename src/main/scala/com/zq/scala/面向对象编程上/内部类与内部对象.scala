package com.zq.scala.面向对象编程上

/**
  * Created by zhangqiang on 2016/12/27.
  */
object 内部类与内部对象 extends App {

  /**
    * 伴生类Student
    */
  class Student (var name: String, private var age: Int) {

    // 内部类
    class Grade(var name: String) {
      def method(): Unit = {
        println("内部类中访问外部类的私有成员变量 age = " + age)
      }
    }

    // 内部对象
    object Utils1 {
      def print(name: String) = println(name)
    }

  }

  /**
    * 伴生对象Student
    */
  object Student {

    /**
      * 单例对象中的内部类Printer
      */
    class Printer {
      def print(name: String) = println(name)
    }

    /**
      * 伴生对象中的单例对象
      */
    object Utils2 {
      def print(name: String) = println(name)
    }

  }

  val student = new Student("join", 18)
  // 创建伴生类的内部类的对象
  val grade = new student.Grade("研究生一年级")
  println("new student.Grade(\"研究生一年级\").name:调用伴生类的内部对象" + grade.name)
  // 内部类中访问外部类的私有成员变量
  grade.method()
  // 创建伴生对象的内部类的对象
  var printer = new Student.Printer()
  printer.print("new Student.Printer().print:调用伴生对象的内部类的方法")
  // 调用伴生对象内部的单例对象的方法
  Student.Utils2.print("Student.Utils2.print:调用伴生对象内部的单例对象的方法")

}

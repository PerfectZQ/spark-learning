package com.zq.scala.模式匹配

/**
  * Created by zhangqiang on 2017/1/6.
  *
  *
  */
object for循环中的模式匹配 extends App {

  val map = Map("Java" -> "Hadoop", "Closure" -> "Storm", "Scala" -> "Spark")

  /**
    * 1、变量模式匹配
    * for循环中使用变量模式匹配给变量赋值的使用方法
    */
  for ((language, framework) <- map) {
    println(s"$framework is developed by $language language")
  }

  /**
    * 2、常量模式匹配
    * for循环中常量模式匹配的使用方法
    */
  for ((language, "Spark") <- map) {
    println(s"Spark is developed by $language language")
  }

  /**
    * 3、变量绑定模式匹配
    * for循环中变量绑定模式匹配的使用方法
    */
  for ((language, e@"Spark") <- map) {
    println(s"$e is developed by $language language")
  }

  /**
    * 4、类型模式匹配
    * for循环中的类型模式匹配的使用方法
    */
  for ((language, framework: String) <- Map("Java" -> "Hadoop".length, "Closure" -> "Storm", "Scala" -> "Spark".length)) {
    println(s"$framework is developed by $language language")
  }

  /**
    * 5、构造函数模式匹配
    * for循环中的构造函数模式匹配的使用方法
    */
  case class Dog(val name: String, val age: Int)

  for (Dog(name, age) <- List(Dog("weichao", 23))) {
    println(s"dog name : $name,age : $age")
  }

  /**
    * 6、序列模式匹配
    * for循环中的序列模式匹配的使用方法
    */
  for (List(first, _*) <- List(List(1), List(2, 3), List(4, 5, 6))) {
    println(s"first element is $first")
  }
}

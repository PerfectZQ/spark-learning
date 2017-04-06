package com.zq.scala.面向对象编程下

/**
  * Created by zhangqiang on 2016/12/27.
  *
  * trait与类的相似点：
  * trait可以像普通类一样，定义成员变量和成员方法，不论是抽象的还是具体的，trait在抽象程度上更接近于抽象类
  * 注：在定义trait的时候可以使用extends关键字
  *
  * trait与类的不同点:
  * 不论是普通类还是抽象类，都可以在类定义的时候使用主构造函数定义类的成员变量，但是trait不能
  * scala语言中的类不可以继承多个类，但是可以混入多个trait
  */
object trait与类 extends App {

  // 定义一个普通类
  class A {
    val msg: String = "this is a message"
  }

  trait B extends A {
    def printMsg(): Unit = println(msg)
  }

  // 匿名类
  new B {}.printMsg
}

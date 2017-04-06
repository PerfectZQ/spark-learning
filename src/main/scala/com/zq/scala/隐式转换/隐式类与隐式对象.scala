package com.zq.scala.隐式转换

/**
  * Created by zhangqiang on 2017/1/9.
  */
object 隐式类与隐式对象 extends App {

  /** 隐式类
    * 通过implicit关键字定义一个隐式类，隐式类的主构造函数接受String类型参数
    * 这样当一个String类型的变量调用隐式类中的方法时，就会进行String类型到隐
    * 式类类型的隐式转换
    * 注：隐式类的主构造函数参数有且只有一个，因为隐式转换是将一种类型转换成另
    * 外一种类型，源类型必须与目标类型一一对应
    */
  implicit class Dog(val name: String) {
    def bark = println(s"Dog $name is barking")
  }

  // String类型本身没有bark方法，此时进行了隐式转换
  "wc".bark

  /**
    * 隐式类的原理
    * 隐式类会最终被翻译成如下代码
    * class Dog(val name:String){
    * def bark = println(s"Dog $name is barking")
    * }
    * implicit def string2Dog(name:String) = new Dog(name)
    * 隐式类是通过隐式函数来实现的，隐式类的代码更简洁，但是也会使代码更加“迷幻”
    */


  trait Multiplicable[T] {
    def multiply(x: T): T
  }

  /**
    * 隐式对象:整型数据的相乘
    */
  implicit object MultiplicableInt extends Multiplicable[Int] {
    override def multiply(x: Int): Int = x * x
  }

  /**
    * 隐式对象：字符串数据的乘积
    */
  implicit object MultiplicableString extends Multiplicable[String] {
    override def multiply(x: String): String = x * 2
  }

  // 定义一个函数，函数具有泛型参数
  def multiply[T: Multiplicable](x: T) = {
    // implicitly方法，访问隐式对象
    val ev = implicitly[Multiplicable[T]]
    ev.multiply(x)
  }

  // 调用隐式对象MultiplicableInt中的multiply(x: Int)方法
  println(multiply(5))
  // 调用隐式对象MultiplicableString中的multiply(x: String)方法
  println(multiply("5"))
}

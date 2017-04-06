package com.zq.scala.隐式转换

/**
  * Created by zhangqiang on 2017/1/9.
  */
object 隐式参数和隐式值 extends App {
  /**
    * 隐式参数
    */
  // implicitly函数被定义在Predef对象中,其中的参数e也使用了implicit关键字修饰，这种形式的参数叫做隐式参数
  @inline def implicitly[T](implicit e: T) = e

  trait Multiplicable[T] {
    def multiply(x: T): T
  }

  implicit object MultiplicableInt extends Multiplicable[Int] {
    override def multiply(x: Int) = x * x
  }

  implicit object MultiplicableString extends Multiplicable[String] {
    override def multiply(x: String) = x * 2
  }

  // 使用隐式参数定义multiply函数,[T: Multiplicable]代表Multiplicable类的泛型
  def multiply[T: Multiplicable](x: T)(implicit ev: Multiplicable[T]) = ev.multiply(x)

  // 调用。他会在当前作用域查找类型为Multiplicable[T]的隐式对象，然后根据泛型T的类型决定是哪一个隐式
  // 对象，最后调用相应的方法
  println(multiply(5))
  println(multiply("5"))

  /**
    * 隐式值
    */

}

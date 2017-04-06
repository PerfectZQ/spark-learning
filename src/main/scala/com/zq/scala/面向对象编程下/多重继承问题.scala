package com.zq.scala.面向对象编程下

/**
  * Created by zhangqiang on 2016/12/27.
  *
  * scala中可以通过使用trait来实现多重继承，不过在实际使用时常常会遇到菱形继承的问题
  */
object 多重继承问题 extends App {

  /**
    * 菱形继承问题
    */
  trait A {
    def print: Unit

    // 解决菱形继承问题的常用方式
    val a = "Trait A"
    def solve(msg:String) = println(msg + a)
  }

  trait B1 extends A {
    var b1 = "Trait B1 "

    override def print = println(b1)

    // super关键字的使用方式也是一种惰性求值，super关键字调用的方法不会立即执行
    // 而是在真正被调用时执行，他的执行原理同样按照最右深度优先遍历算法进行，先将
    // B2、B1、A中的成员变量按序组装得到 b2+b1+a 最后调用print(msg + b2+b1+a)
    override def solve(msg: String): Unit = super.solve(msg + b1)
  }

  trait B2 extends A {
    var b2 = "Trait B2 "

    override def print = println(b2)

    override def solve(msg: String): Unit = super.solve(msg + b2)
  }

  class C extends B1 with B2;

  val c = new C
  // 继承的两个trait都存在print方法，实际上打印Trait B2。
  // 因为scala在解决方法调用的冲突问题时，会对类进行线性化，在存在多重继承是会使用
  // 最右深度优先遍历算法查找调用的方法，所以执行的B2中的print方法，没有执行B1中的print方法
  c.print

  // 为了解决菱形问题(只执行最右面深度的方法)
  c.solve("solve ")
}

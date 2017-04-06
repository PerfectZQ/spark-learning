package com.zq.scala.面向对象编程上

/**
  * Created by zhangqiang on 2016/12/26.
  *
  * 在Java中定义类的多个构造函数的时候，构造函数名必须与类名相同
  * 而在scala语言中定义辅助构造函数的时候通过this关键字来解决重命名类名也需要重命名构造函数名的问题
  */
object 辅助构造函数 extends App {

  /**
    * 默认主构造函数
    */
  class Person {
    private var name: String = null
    private var age: Int = 0

    // 辅助构造函数
    def this(name: String) {
      // 调用无参的默认主构造函数，必须首先要调用主构造函数，这个是必须的，否则会报错
      this()
      this.name = name
    }

    // 辅助构造函数也可以带默认参数
    def this(name: String, age: Int) {
      // 调用一个参数的辅助构造函数
      this(name)
      this.age = age
    }

    override def toString: String = name + " : " + age
  }

  /**
    * 将默认主构造函数定义为private，这样就可以调用带有默认参数的构造函数了
    */
  class Person_ private{}

  // 调用无参主构造函数
  val Person1 = new Person
  println(Person1)
  // 调用一个参数的辅助构造函数
  val Person2 = new Person("Super Man")
  println(Person2)
  // 调用两个参数的辅助构造函数
  val Person3 = new Person("Super Man", 800)
  println(Person3)

}

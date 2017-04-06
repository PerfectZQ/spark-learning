package com.zq.scala.面向对象编程上

/**
  * Created by zhangqiang on 2016/12/26.
  */
object 主构造函数 extends App {

  /**
    * 带主构造函数的类定义,可以带默认参数，这样在创建对象时可以不指定参数而使用默认值
    * 但是一旦有一个参数使用了默认值，其后的所有参数都应该有默认值，这样才能在构造对象的时候使用默认值
    * 通过反编译的字节码文件可以看到它做了3件事
    * 1、定义了一个类Person(java.lang.String, int)
    * 2、定义了name和age两个成员变量变量
    * 3、分别生成了两个成员变量的scala风格的getter和setter方法
    */
  class Person(var name: String, var age: Int = 18) {
    // println将作为主构建器中的一部分，在创建对象时被执行
    println("构造函数里面执行……")

    override def toString: String = name + " : " + age
  }

  /**
    * 私有主构造函数，这样主构造函数只能在类的内部调用,即只能在类的内部new(创建对象)
    */
  class Person_ private(var name: String = "default", var age: Int = 18)

  val person = new Person("Super Marry", 33)
  println(person.toString)

  // 使用默认参数创建Person对象
  val personByDefault = new Person("default")
  println(personByDefault.toString)
}

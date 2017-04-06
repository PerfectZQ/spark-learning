package com.zq.scala.模式匹配

/**
  * Created by zhangqiang on 2016/12/28.
  *
  * 常量模式、变量模式、构造函数模式、序列模式、元组模式、类型模式、变量绑定模式
  */
object 模式匹配7大类型 extends App {

  for (i <- 1 to 6) {
    i match {
      /** 常量模式 */
      case 1 => println("我是第一")

      /** 变量模式：变量模式可以匹配任何值，他可以与条件判断表达式(也称作守卫)结合起来使用，起到筛选的作用 */
      case x if (x % 2 == 1) => println("奇数")
      // 如果没有判定条件，变量模式会匹配之前没有被case匹配到的所有值
      case x => println(x)
      // 在实际应用中也要注意case语句的顺序问题，例如
      case x if (x % 2 == 0) => println("能被2整除")
      case x if (x % 3 == 0) => println("能被3整除")
      // 如果按上面的顺序，6即能被2整除，也能被3整除，哪条语句在前面就执行哪一条，后面的就都不执行了
    }
  }

  /**
    * 构造函数模式
    */
  // 定义一个case class
  case class Dog(val name: String, val color: String)



  // 利用构造函数创建对象, 只有case class才可以这样创建对象，普通类想通过非显式new创建对象需要使用伴生对象的apply方法
  val dog = Dog("weichao", "black")

  def patternMatching(x: AnyRef): Unit = x match {
    // 构造函数模式，用于对象进行解构(析构)
    case Dog(name, color) => println(s"Dog name = $name,color = $color")
  }

  patternMatching(dog)

  // 通配符匹配
  def patternMatching_(x: AnyRef): Unit = x match {
    // _匹配Dog类的name成员，但是程序中不需要使用name的值(即匹配但丢弃该值)
    case Dog(_, color) => println(s"Dog color = $color")
  }

  patternMatching_(dog)

  /**
    * 序列模式
    *
    * 匹配Seq[+A]类及子类集合的内容
    */
  val arrInt = Array(1, 2, 3, 4)

  def patternMatching_seq(x: AnyRef) = x match {

    // 只会匹配有两个元素的数组
    case Array(first, second) => println(s"first = $first,second = $second")
    // 匹配有三个元素的数组，第二个元素舍弃
    case Array(first, _, third) => println(s"first = $first,third = $third")
    // 会匹配所有任何数组元素大于等于4个的数组，_*代表之后的0到无数个元素，_*必须放在模式的最后，其他位置都是不正确的
    case Array(first, second, third, fourth, _*) => println(s"first = $first,fourth = $fourth")
  }

  patternMatching_seq(Array(1, 2, 3))
  patternMatching_seq(arrInt)

  /**
    * 元组模式
    */
  val tupleInt = (1, 2, 3, 4)

  def patternMatching_tuple(x: AnyRef) = x match {
    // 匹配两个元素的元组
    case (first, second) => println(s"first = $first,second = $second")
    // 匹配四个元素的元组
    case (first, _, third, _) => println(s"first = $first,third = $third")
    // 元组不允许使用_*
    // case (first, _, third, _*) => println(s"first = $first,third = $third")
    case _ =>
  }

  patternMatching_tuple(tupleInt)

  /**
    * 类型模式
    */
  class A

  class B extends A

  class C extends A

  val c = new C
  val b = new B

  def patternMatching_class(x: AnyRef) = x match {
    case x: String => println("String类型")
    case x: B => println("B类型")
    case x: A => println("A类型")
    case _ =>
  }

  patternMatching_class("aa")
  patternMatching_class(b)
  patternMatching_class(c)

  // 输出A类型 证明类型模式也具有多态性

  /**
    * 变量绑定模式
    * 返回的不是对象的成员变量值，而是匹配的到该模式的对象本身
    */
  def patternMatching_variableBind(x: AnyRef) = x match {
    // 变量绑定模式，匹配成功，则将整个对象复制给变量d
    // Dog(_,_)用来匹配包括两个成员变量的Dog对象
    case d@Dog(_, _) => println("变量绑定模式返回的值" + d)
    // 匹配两个元素的List，且第二个元素是第一个元素为4的List
    case list1@List(_, list2@List(4, _*)) => println("list1 = " + list1 + ",list2 = " + list2)
    case _ =>
  }

  val list = List(List(1, 2, 3), List(4))

  patternMatching_variableBind(dog)
  patternMatching_variableBind(list)

}

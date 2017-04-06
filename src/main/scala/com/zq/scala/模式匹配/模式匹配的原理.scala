package com.zq.scala.模式匹配

/**
  * Created by zhangqiang on 2016/12/28.
  *
  *
  */
object 模式匹配的原理 extends App {

  /**
    * 构造函数模式匹配原理
    *
    * 元组模式匹配也是通过unapply方法起作用的
    */

  // 定义一个普通的Dog类
  class Dog(val name: String, val age: Int)

  // Dog的伴生对象
  object Dog {
    // 手动实现unapply方法
    def unapply(arg: Dog): Option[(String, Int)] = {
      if (dog != null) Some(dog.name, dog.age)
      else None
    }
  }

  // 定义一个样例类Cat，当将类定义成case class之后，便不需要手动去编写伴生对象Cat并实现unapply方法
  // 因为一个类一旦被定义成case class后，编译器会自动帮我们生成该类的伴生对象并实现apply和unapply方法
  case class Cat(val name: String, val color: String)

  // 普通类只能通过显示的使用new来创建对象
  val dog: Dog = new Dog("weichao", 23)

  // 样例类可以省略new创建对象
  val cat = Cat("wangmaogang", "yellow")

  def patternMatching(x: AnyRef) = x match {
    // 如果是一个普通类，需要显式的定义Dog类的半生对象并在伴生对象中实现unapply方法，否则编译报错
    case Dog(name, age) => println(s"Dog name=$name,age=$age")
    case Cat(name, color) => println(s"Cat name=$name,color=$color")
    case _ =>
  }

  patternMatching(dog)

  /**
    * 序列模式匹配原理
    *
    * Array类的伴生对象中有两种重要的方法：apply方法以及unapplySeq方法
    * apply方法可以让开发人员直接通过Array(1,2,3,4)来无new的显式方式创建对象
    * unapplySeq方法可以让开发人员使用序列模式匹配，这也是为什么元组不可以使用_*的原因(元组使用unapply方法)
    * 在使用像case Array(first,_,three,_*)这样的序列模式匹配时，便会自动调用unapplySeq方法完成对序列的内容析取
    */

}

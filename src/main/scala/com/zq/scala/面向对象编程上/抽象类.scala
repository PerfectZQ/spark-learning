package com.zq.scala.面向对象编程上

/**
  * Created by zhangqiang on 2016/12/27.
  *
  * 抽象类是一种不能被实例化的类，抽象类中存在抽象成员变量或成员方法，这些成员变量或成员方法在子类中被具体化
  * 注意：与java不同的是scala的抽象类中不仅可以有抽象方法，还可以有抽象成员变量
  */
object 抽象类 extends App{
  /**
    * 普通类成员变量必须显式初始化，如果不初始化会提示类应声明为abstract
    * 抽象类反编译得到的字节码文件可以看出，抽象成员变量name只有相应的getter和setter方法，没有成员的变量的声明
    * 子类中初始化后，在子类中有成员变量的声明
    */
  abstract class Person{
    // 抽象类中的抽象成员变量
    var name:String
    // 抽象类中的抽象成员变量
    var age:Int
    // 抽象类中的抽象成员方法
    def walk()
  }

  /**
    * 子类继承抽象类的时候，如果子类是一个普通类，则需要在子类中对父类中的抽象成员变量进行初始化
    * 否则，子类也需要声明为抽象类
    */
  class Student extends Person{
    // 子类对父类中华的抽象成员变量进行初始化，使用override关键字
    override var name: String = _
    // 当然也可以省略override关键字
    var age: Int = _

    // 继承父类的抽象成员方法，也可以省略override关键字
    override def walk(): Unit = {
      println("Walk like a student")
    }
  }


}

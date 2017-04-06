package com.zq.scala.面向对象编程上

import scala.beans.BeanProperty

/**
  * Created by zhangqiang on 2016/12/26.
  *
  * 通过反编译字节码文件可以看到，Person类会自动生成5个方法，分别是scala风格的所有成员变量的getter和setter方法以及构造方法
  * 并且成员变量默认是public，只是scala中没有public关键字，但在反编译生成的java代码中是private的
  */
object 类定义与类成员的访问 {

  class Person {
    // 当不用private修饰符修饰时，生成的成员变量是private的但是getter和setter方法是public的
    // 类的成员变量必须初始化，如果不需要初始化，则需要将类定义成抽象类
    var name: String = null
    // 成员变量初始化时还可以使用占位符，即给变量赋默认的初始值AnyVal默认值为0，AnyRef为null
    // 并且显示使用private修饰符时，自动生成的getter和setter方法也是private的
    private var age: Int = _
    // 当类成员被声明为val时，将生成的字节码文件反编译后可以看到，它只会生成sex成员的getter方法，
    // 不会生成setter方法，因为val定义的常量不可以被修改
    val sex: String = null
    // 当需要java风格的getter和setter方法时，可以加上注解@BeanProperty,它除了会自动生成scala风格
    // 的getter和setter方法还会自动生成java风格的getter和setter方法，而且被@BeanProperty注解的变量不能用private修饰
    @BeanProperty
    var hobby: Array[String] = null

  }

  /**
    * 包含程序入口main方法或者混入了APP trait的object对象叫应用程序对象
    */
  object main {
    // * main函数必须定义在单例对象中才可以作为程序程序的执行入口
    def main(args: Array[String]): Unit = {
      // 创建对象,无参数构造函数可以省略括号
      val person = new Person
      // 类成员的访问 可以通过name()和name_=(java.lang.String)对成员变量进行操作
      // 显式的调用Person的setter方法
      person.name_=("join")
      // 调用getter方法 name默认是私有成员，在类外不能直接访问私有成员，下面的代码之所以合法
      // 是因为它其实调用的是setter和getter方法，只不过编译器为开发人员屏蔽了相应的细节，这种
      // 调用者并不知道是直接对成员变量直接访问的，还是通过setter方法对成员变量进行访问的方式被称为统一访问原则
      println("name = " + person.name)
      // 直接修改成员变量，实际上调用的是name_=(java.lang.String)方法
      person.name = "Join"
      println("name = " + person.name)
      // 对于生成java风格的setter和getter方法的成员变量可以直接使用java风格的setter和getter访问
      person.getHobby()
    }
  }

}

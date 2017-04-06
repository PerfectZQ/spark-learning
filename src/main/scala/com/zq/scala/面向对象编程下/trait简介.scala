package com.zq.scala.面向对象编程下

/**
  * Created by zhangqiang on 2016/12/27.
  *
  * 在scala语言中并没有提供java语言的interface关键字来定义接口，而提供的是trait(特质)关键字来封装
  * 成员方法和成员变量，在使用时通过extends或with关键字来混入(mix)定义的trait，trait与类继承最大的
  * 区别就是一个类只能有一个父类，但是它可以混入多个trait
  */
object trait简介 extends App {

  /**
    * 使用trait关键字来定义一个特质
    * 通过反编译可以看出Closeable trait最终是由java的interface实现的
    */
  trait Closeable {
    def close(): Unit
  }

  /**
    * 假设有一个文件类File，他需要实现自己的close方法已关闭文件流，此时就可以通过关键字extends将
    * Closeable trait混入，并对close方法进行实现
    * 注：混入的第一个trait必须使用extends关键字，其余的使用with关键字，否则会报错
    */
  class File(var name: String) extends Closeable {
    override def close(): Unit = println(s"File $name has been closed")
  }

  new File("config.txt").close()
}

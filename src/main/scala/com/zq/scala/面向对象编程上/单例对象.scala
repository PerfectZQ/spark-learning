package com.zq.scala.面向对象编程上

/**
  * Created by zhangqiang on 2016/12/26.
  *
  * 在实际应用中常常会有不创建对象就可以直接访问相应的成员变量或者方法的场景
  * Java语言提供了静态成员和静态方法来支持这一场景，但是scala并不支持静态类成员这一语法，
  * 而是通过单例对象来实现，实例如下
  */
// 通过object关键字来声明单例对象
// 单例对象extends App来定义应用程序对象，能够使代码更简洁，
// 查看trait App的源码可以看到，App实质上已经为我们定义了main函数，App依然是使用了main方法来作为程序的入口
object 单例对象 extends App {

  object SingletonObject {
    private var studentNo: Int = 0

    // 通过反编译后生成的字节码文件可以看出，他产生了两个字节码文件SingletonObject.class和SingletonObject$.class
    // 实质上是用的java语言的单例模式和静态类成员来实现的
    def uniqueStudentNo() = {
      studentNo += 1;
      // 返回值
      studentNo
    }
  }

  // 直接在继承了App的单例对象中写执行语句
  println(SingletonObject.uniqueStudentNo())

}


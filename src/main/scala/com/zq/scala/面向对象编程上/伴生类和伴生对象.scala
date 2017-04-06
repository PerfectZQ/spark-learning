package com.zq.scala.面向对象编程上

/**
  * Created by zhangqiang on 2016/12/26.
  *
  * 伴生对象和伴生类
  * 伴生类和伴生对象区别于其他类或对象十分重要的地方便是访问控制权限、无new创建对象及析构对象
  */
object 伴生类和伴生对象 extends App {


  // 伴生类 : class Student 叫 object Student的伴生类
  class Student {
    var name: String = null

    def getStudentNo = {
      Student.uniqueStudentNo()
      // 伴生类可以直接访问伴生对象中的私有成员变量(被private显式修饰的)
      Student.studentNo
    }
  }

  // 伴生对象 : object Student 叫做 class Student的伴生对象
  object Student {
    private var studentNo: Int = 0

    def uniqueStudentNo() = {
      studentNo += 1;
      // 返回值
      studentNo
    }

    // 直接访问伴生类的对象的私有成员(被private显式修饰的)
    def printStudentName = println(new Student().name)

    // apply方法，供非显式new创建对象时调用
    def apply(): Student = new Student()
  }

  // 实质上是调用的伴生对象的apply方法
  Array(1,2,3,4,5,6)

  // 无new方式创建对象，调用的是伴生对象的apply方法,必须加括号，否则是object Student
  val student = Student()
  student.name = "super marry"
  print(student.name)
}

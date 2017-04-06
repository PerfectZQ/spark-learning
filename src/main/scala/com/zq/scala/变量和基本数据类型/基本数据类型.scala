package com.zq.scala.变量和基本数据类型

/**
  * Created by zhangqiang on 2017/1/4.
  *
  * 基本数据类型：Byte、Short、Int、Long、Float、Double、Char、Boolean
  * scala中所有值类型都是对象，与java的基本数据类型不同，首字母必须大写
  */
object 基本数据类型 extends App{
  val a:Int = 1;
  // 可以直接使用对象的方法
  println(a.toString)
  // 也可以这样~
  println(1.toString)
}

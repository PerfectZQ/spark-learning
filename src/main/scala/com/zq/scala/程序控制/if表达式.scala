package com.zq.scala.程序控制

/**
  * Created by zhangqiang on 2017/1/5.
  *
  * 与java、c++、c等高级程序设计语言的if条件判断语句不同，scala中的if语句可以作为表达式使用
  * 表达式具有返回值，可以直接赋给变量
  */
object if表达式 extends App {
  val x = if ("hello" == "hello") true else false;
  println(x)
}

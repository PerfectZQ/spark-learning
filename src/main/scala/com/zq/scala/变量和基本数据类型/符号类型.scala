package com.zq.scala.变量和基本数据类型

/**
  * Created by zhangqiang on 2017/1/4.
  *
  * 符号类型(Symbol) 定义时需要'符号
  */
object 符号类型 extends App {
  // 定义一个符号类型的变量s，变量s会被默认识别为Symbol类型
  val s = 'start
  // 显示声明符号类型
  val s_ : Symbol = 'start
  // 符号类型主要起标识作用，常用与模式匹配、内容判断中
  // for example
  if (s == s_) println(true) // 使用 == 来比较变量时，比较的是变量的内容而非引用
  else println(false)
  // 直接输出符号类型的变量会按原样输出
  println("s = " + s)
}

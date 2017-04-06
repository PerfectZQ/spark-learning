package com.zq.scala.变量和基本数据类型

/**
  * Created by zhangqiang on 2017/1/4.
  *
  * 元组类型是scala的一种特殊类型，元组是不同类型值的聚集，可以将不同类型的值放在一个变量中保存
  */
object 元组类型 extends App {
  // 元组的定义如下
  val tuple = ("hello", "china", "beijing")
  // 访问元组
  println(tuple._1 + "," + tuple._2)
  // 使用时还可以提取元组的内容到变量中
  val (a, b, c) = tuple
  // 访问元组
  println(a + "," + b)
}

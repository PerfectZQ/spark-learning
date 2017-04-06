package com.zq.scala.函数

/**
  * Created by zhangqiang on 2016/12/26.
  */
class 函数柯里化 {
  // 定义一个高阶函数
  def higherOrderFunction(factor: Int) = (x: Double) => factor * x

  // 定义一个柯里化函数
  def multiply(factor: Int)(x: Double) = factor * x

  def main(args: Array[String]): Unit = {
    // 高阶函数调用:
    // 方式1,与柯里化的函数调用方式十分相似
    higherOrderFunction(50)(10)
    // 方式2
    val f = higherOrderFunction(50)
    f(10)
    // 柯里化函数调用，柯里化函数不是高阶函数，不能像高阶函数的方式2一样，multiply(50)不会返回一个函数对象，会编译报错
    multiply(50)(10)
  }

}

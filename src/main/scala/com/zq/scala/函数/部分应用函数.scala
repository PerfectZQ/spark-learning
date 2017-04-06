package com.zq.scala.函数

/**
  * Created by zhangqiang on 2016/12/26.
  */
object 部分应用函数 {
  // 定义一个普通函数
  def product(x1: Int, x2:Int, x3:Int) = x1 * x2 * x3

  def main(args: Array[String]): Unit = {
    val 柯里化 = new 函数柯里化()

    // 生成一个参数的部分应用函数
    val paf = 柯里化.multiply(50) _
    // 调用部分应用函数
    paf(10)
    // 生成两个参数的部分应用函数
    val paf_ = 柯里化.multiply _
    // 现在就可以像高阶函数一样使用
    paf_(50)(10)
    // 也可以返回函数对象
    val f = paf_(50)
    f(10)
    // 不只柯里化函数有部分应用函数，普通的函数也可以使用部分应用函数
    // 生成一个参数的部分应用函数
    def product_1 = product(_:Int,2,3)
    // 调用一个参数的部分应用函数
    product_1(2)
    // 生成多个参数的部分应用函数
    def product_2 = product(_:Int,_:Int,3)
    def product_3 = product _
    // 调用多个参数的部分应用函数
    product_2(2,3)
    product_3(1,2,3)

  }
}

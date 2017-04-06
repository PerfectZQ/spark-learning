package com.zq.scala.函数

/**
  * Created by zhangqiang on 2016/12/26.
  */
object 偏函数 {

  // 在定义函数时使用PartialFucntion[-A,B] -A表示输入参数的类型，
  // B表示返回值的类型,这种方式等同于将变量定义成ParialFunction[-A,B]类型
  def receive: PartialFunction[Any, String] = {
    case x: Int => "Int Type"
    case x: String => "String Type"
    case _ => "Other Type"
  }

  def main(args: Array[String]): Unit = {
    val sample = 1 to 10
    // 将变量定义为一个偏函数类型，只处理为偶数的值
    val isEven: PartialFunction[Int, String] = {
      case x if (x % 2 == 0) => x + " is even"
    }
    println(isEven(12))
    val isOdd: PartialFunction[Int, String] = {
      case x if (x % 2 == 1) => x + " is Odd"
    }
    println(isOdd(11))
    // orElse将参数作用域合并
    val numbers = sample.map(isEven orElse isOdd)
    numbers.foreach(println)

    println(receive("asd"))
  }
}

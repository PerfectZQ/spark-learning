package com.zq.scala.模式匹配

/**
  * Created by zhangqiang on 2016/12/28.
  *
  * Java switch语句 case语句后面要加break语句，否则会意外陷入分支，并且case语句后面不能接表达式
  * scala的模式匹配剋有效的避免java switch语句意外陷入另外一个分支的情况，其次匹配代码更简洁
  * 更符合人脑的思维方式，scala的模式匹配中case语句还可以加入表达式
  */
object 模式匹配简介 extends App {
  for (i <- 1 to 6) {
    i match {
      case 1 => println(1)
      case x if (x % 2 == 0) => println(s"$x 能够被2整除")
      // 其他情况不进行操作
      case _ =>
    }
  }

  // 函数中使用模式匹配，模式匹配结果作为函数返回值
  def patternMatching(x: Int) = x match {
    case x if (x % 2 == 0) => "偶数"
    case _ => "奇数"
  }

  println(patternMatching(5))
}

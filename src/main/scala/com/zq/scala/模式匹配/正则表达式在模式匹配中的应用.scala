package com.zq.scala.模式匹配

import scala.util.matching.Regex

/**
  * Created by zhangqiang on 2017/1/5.
  *
  * 正则表达式和模式匹配结合
  */
object 正则表达式在模式匹配中的应用 extends App {
  /**
    * 提取模式的分组值
    */
  // 正则表达式
  val dateRegex:Regex =
  """(\d\d\d\d)-(\d\d)-(\d\d)""".r
  // 待匹配的字符串
  val text = "2015-12-31 2016-02-20"
  // 提取模式的分组信息
  for (date <- dateRegex.findAllIn(text)) {
    date match {
      case dateRegex(year, month, day) => println(s"match 语句中的模式匹配： year=$year,month=$month,day=$day")
      case _ =>
    }
  }
  // 与前面for循环的代码作用相同
  for (dateRegex(year, month, day) <- dateRegex.findAllIn(text)) {
    println(s"for循环中的正则表达式模式匹配：year=$year,month=$month,day=$day")
  }

  // findFirstMatchIn 返回值类型是Option[Match]
  dateRegex.findFirstMatchIn(text) match {
    case Some(dateRegex(year,month,day)) => println(s"findFirstMatchIn 与模式匹配 ：year=$year,month=$month,day=$day")
    case None =>
  }

  // findFirstIn 返回值类型是Option[String]
  dateRegex.findFirstIn(text) match {
    case Some(dateRegex(year,month,day)) => println(s"findFirstIn 与模式匹配 ：year=$year,month=$month,day=$day")
    case None =>
  }
}

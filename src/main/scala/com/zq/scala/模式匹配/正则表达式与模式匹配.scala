package com.zq.scala.模式匹配

/**
  * Created by zhangqiang on 2017/1/5.
  */
object 正则表达式与模式匹配 extends App {

  /**
    * 调用Java语言提供的API进行正则表达式处理
    */

  import java.util.regex.Pattern

  // 正则表达式待匹配的字符串
  val line = "Hadoop has benn the most popular big data " +
    "processing tool since 2005-11-21"

  // 正则表达式，用于匹配年-月-日这样的日期
  val regex = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)"

  // 根据正则表达式创建 Pattern 对象
  val pattern = Pattern.compile(regex)

  // 创建Matcher对象
  val m = pattern.matcher(line)
  if (m.find()) {
    println(m.group(0))
    println(m.group(1))
    println(m.group(2))
    println(m.group(3))
  } else {
    println("未找到匹配项")
  }

  println("--------------------分割线-------------------下面是 scala's API")

  /**
    * 使用Java语言提供的API能够满足大部分正则表达式的应用场景但是scala自身提供的正则表达式API更灵活，代码更简洁
    * 更重要的是scala提供的API能够很好的与模式匹配相结合
    * 在scala中有两种创建正则表达式的方式：
    */

  import scala.util.matching.Regex
  import scala.util.matching.Regex.MatchIterator

  // 1、通过r方法直接将字符串转换成正则表达式对象，对应的类为scala.util.matching.Regex
  // 原理：首先通过隐式转换将字符串转换成scala.collection,immutable.StringLike对象，然后
  // 调用该对象中的r方法将字符串转换为scala.util.matching.Regex对象
  val dateP1: Regex =
  """(\d\d\d\d)-(\d\d)-(\d\d)""".r

  // 2、直接显式调用scala.util.matching.Regex构造函数创建
  val dateP2 = new Regex("""(\d\d\d\d)-(\d\d)-(\d\d)""")

  /**
    * 使用scala正则表达式，需要掌握以下几个重要的方法：
    */

  // def findAllIn(source:CharSequence):MatchIterator
  // 该方法用于匹配字符串中所有与输入模式相匹配的字符，并返回MatchIterator对象
  // MatchIterator是一种特殊的scala.collection.Iterator迭代器，可以使用for循环迭代输出所有匹配的模式
  println("findAllIn")
  for (date <- dateP1.findAllIn("2005-11-21 2017-01-05")) {
    println(date)
  }

  // def findAllMatchIn(source:CharSequence):Iterator[Match]
  // 以Iterator[Match]形式返回所有匹配的模式
  println("findAllMatchIn")
  for (date <- dateP1.findAllMatchIn("2005-11-21 2017-01-05")) {
    // date是Match对象，groupCount返回的是匹配模式的分组数
    println(date.groupCount)
  }

  // def findFirstIn(source:CharSequence):Option[String]
  // 返回匹配成功的第一个字符串，匹配成功返回Option[String]，失败返回None
  println("findFirstIn")
  val copyright: String = dateP1 findFirstIn "Date of this document: 2017-01-05" match {
    // final case class Some[+A](x: A) extends Option[A]
    case Some(date) => "Copyright " + date
    case None => "No copyright"
  }
  println(copyright)

  // def findFirstMatchIn(source:CharSequence):Iterator[Match]
  // 返回匹配成功的每一个字符串，以Match对象形式返回，匹配成功返回Option[Match]，失败则返回None
  // 与findFirstIn方法的区别在于他能够获取更多关于匹配的信息，如分组数、匹配字符串的索引等信息
  println("findFirstMatchIn")
  val dateRegex = new Regex("""(\d\d\d\d)-(\d\d)-(\d\d)""", "year", "month", "day")
  val result = dateRegex findFirstMatchIn ("2005-11-21 2017-01-05") match {
    case Some(m) => "year : " + m.group("year")
    case None => "匹配失败"
  }
  println(result)

  // def replaceAllIn(target: CharSequence, replacer: (Match) => String): String
  // 使用replacer函数对所有模式匹配成功的字符串进行替换，replacer函数的输入参数为Match，返回值类型是String
  println("replaceAllIn(target: CharSequence, replacer: (Match) => String): String")
  val replace = dateRegex.replaceAllIn("From 2012-09-01 to 2016-07-01",
    m => m.group("year") + "/" + m.group("month") + "/" + m.group("day"))
  println(replace)
  // 此方法还有重要在重载函数 def replaceAllIn(target: CharSequence, replacement: String): String
  // 作用与第上面的方法类似，只不过第二个参数不是函数而是直接指定要替换的字符串
  println("replaceAllIn(target: CharSequence, replacement: String): String")
  val regex_ = new Regex("-")
  val replace_ = regex_.replaceAllIn("From 2012-09-01 to 2016-07-01", "/")
  println(replace_)
  // def replaceFirstIn(target: CharSequence, replacement: String): String
  // 替换第一个匹配成功的字符串
  println("def replaceFirstIn(target: CharSequence, replacement: String): String")
  println(regex_.replaceFirstIn("From 2012-09-01 to 2016-07-01", "/"))
}

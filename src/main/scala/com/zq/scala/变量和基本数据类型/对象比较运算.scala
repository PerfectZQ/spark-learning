package com.zq.scala.变量和基本数据类型

/**
  * Created by zhangqiang on 2017/1/4.
  *
  * scala中的对象比较和java中的对象比较不同，scala是基于内容比较，而java是依据对象引用比较（即对象的物理内存地址是否一样）
  * 基于内容比较是scala的重要特点之一
  */
object 对象比较运算 extends App {
  val s1 = new String("abc");
  val s2 = new String("abc");
  println(s1 == s2) // 结果是true
  println(s1.equals(s2)) // 在scala中String的equals方法和==相同
  // 如果想比较内存地址（引用）是否为是相同的，可以使用eq方法
  println(s1.eq(s2))

  /**
    * 字符串运算操作
    *
    * scala中定义的String类型实际上就是java.lang.String类型，因此可以调用java中String类型的所有方法
    * 除此之外，也可以调用以下方法
    * 这些方法在java中是不存在的，实际上scala会将String类型对象阻焊换成StringOps类型对象
    * 在遇到reverse、map、drop、slice方法调用时编译器会自动进行隐式转换
    */

  val s = "hello"

  // 反转符
  println(s.reverse)
  // 丢弃字符 (丢弃前n个字符)
  println(s.drop(3))
  // 获取一定范围内的子串， 左开右闭[1,4)
  println(s.slice(1, 4))


}

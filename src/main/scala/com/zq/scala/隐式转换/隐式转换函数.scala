package com.zq.scala.隐式转换

/**
  * Created by zhangqiang on 2017/1/9.
  *
  * 隐式转换是scala提供的一种强大的语法特性，是学习scala必须要掌握的技能
  * 与scala的模式匹配一样，scala隐式转换也是无处不在
  * scala会默认引入scala包及PreDef对象，其中包含了大量的隐式函数
  */
object 隐式转换函数 extends App {
  // 自定义隐式转换函数-将Float类型变量赋给Int类型变量
  implicit def float2int(x:Float) = x.toInt
  // 定义完成之后，下面的代码可以编译通过的原因：编译器发现赋值对象的类型与最终类型不匹配时，会在
  // 当前作用域范围内查找能将float转换成int类型的隐式转换函数，而float2int正好满足要求，因此可以
  // 编译通过，隐式函数的函数名可以是任意的，隐式转换与函数名无关，只与函数签名(输入参数类型与返回
  // 值类型)有关，如果在当前作用域范围内存在函数签名相同但是函数名称不同的两个隐式函数，在进行隐式
  // 转换的时候会报错，提示有歧义
  val a:Int = 2.55f
  println(a)
}

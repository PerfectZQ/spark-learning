package com.zq.spark

import java.io.File

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 值函数学习
  * Created by zhangqiang on 2016/12/20.
  */
object 值函数 {
  def main(args: Array[String]): Unit = {

    // 防止hadoop找不到文件报错
    val path = new File(".").getCanonicalPath()
    System.getProperties().put("hadoop.home.dir", path);
    new File("./bin").mkdirs();
    new File("./bin/winutils.exe").createNewFile();

    val sparkConf = new SparkConf().setAppName("run something").setMaster("local")
    val sparkContext = new SparkContext(sparkConf)
    val list = List("k1"->1,"k1"->1,"k2"->2,"k3"->1,"k3"->3,"k4"->2)
    val data = sparkContext.parallelize(list)
    println("ordinary : " + data.reduce((a,b)=>(a._1+b._1,a._2+b._2)))
    println("reduceByKey : ")
    data.reduceByKey(_+_).collect().foreach(a=>{
      println(a)
    })
  }

  // 如果需要return关键字指定返回值，则必须显示的指定函数的返回值类型
  // 当函数存在递归调用的时候必须显示的指定函数的返回值类型
  /**
    * 求最大公约数
    *
    * @param x
    * @param y
    * @return
    */
  def gcd(x: Int, y: Int): Int = if (x % y == 0) y else gcd(y, x % y)

}

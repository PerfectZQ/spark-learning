package com.zq.spark

import java.io.File

import org.apache.spark.sql.{Row, SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zhangqiang on 2016/12/21.
  */
object SparkSQL新API {


  def main(args: Array[String]): Unit = {

    /**
      * 防止在windows local模式下启动spark, hadoop找不到文件报错
      */
    val path = new File(".").getCanonicalPath()
    System.getProperties().put("hadoop.home.dir", path);
    new File("./bin").mkdirs();
    new File("./bin/winutils.exe").createNewFile();

    /**
      * Spark2.0新特性，SparkSession实质上是SQLContext和HiveContetx的结合，未来还可能加入StreamContext
      * SparkSession内部封装了sparkContext，所以计算实际上是由sparkContext完成的。
      */
    val sparkSession: SparkSession = SparkSession.builder.master("local").appName("run something").getOrCreate()
    // 将数据源转换成一个DataFrame类型的对象
    val df = sparkSession.read.json("file:///F:\\spark\\spark-2.0.2-bin-hadoop2.6\\examples\\src\\main\\resources\\people.json")
    println("df.show:")
    df.show
    // 以树的形式打印DataFrame的Schema
    println("df.printSchema:")
    df.printSchema
    println("df.select(df(\"name\"), df(\"age\") + 1).show")
    df.select(df("name"), df("age") + 1).show
    val row = df.first
    println("df.first : " + row.toString)


  }
}

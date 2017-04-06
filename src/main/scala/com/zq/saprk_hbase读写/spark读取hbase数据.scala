package com.zq.saprk_hbase读写

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{HBaseAdmin, Result}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.sql.SparkSession

/**
  * Created by zhangqiang on 2017/1/5.
  */
object spark读取hbase数据 extends App {

  val spark = SparkSession.builder().master("local").appName("spark demo").getOrCreate();
  val sc = spark.sparkContext

  // HBaseConfiguration 永远是 read in
  val tableName = "t_person"
  val hConf = HBaseConfiguration.create()
  hConf.set("hbase.zookeeper.quorum", "s12181,s12191,s12192")
  hConf.set(TableInputFormat.INPUT_TABLE, tableName)

  // read in
  val rs = sc.newAPIHadoopRDD(hConf, classOf[TableInputFormat], classOf[ImmutableBytesWritable], classOf[Result])
  /**
    * 方式1：
    */
  rs.foreach(x => {
    // 获取rowkey
    println(Bytes.toString(x._2.getRow))
    // 通过列族和列名获取列
    println(Bytes.toInt(x._2.getValue("base_info".getBytes, "age".getBytes)))
  })

  /**
    * 方式2：元组模式匹配
    */
  rs.foreach { case (_, result) =>
    // 获取rowkey
    println(Bytes.toString(result.getRow))
    // 通过列族和列名获取列
    println(Bytes.toInt(result.getValue("base_info".getBytes, "age".getBytes)))
  }
  // 相当于下面方式的简写
  rs.foreach(x => x match {
    case (_, result) =>
      // 获取rowkey
      println(Bytes.toString(result.getRow))
      // 通过列族和列名获取列
      println(Bytes.toInt(result.getValue("base_info".getBytes, "age".getBytes)))
  })
}

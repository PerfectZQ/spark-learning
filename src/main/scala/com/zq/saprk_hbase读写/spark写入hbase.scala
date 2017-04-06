package com.zq.saprk_hbase读写

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapred.JobConf
import org.apache.spark.SparkConf
import org.apache.spark.rdd.PairRDDFunctions
import org.apache.spark.sql.SparkSession

/**
  * Created by zhangqiang on 2017/1/5.
  */
object spark写入hbase extends App {
  val conf = new SparkConf()
  // 创建SparkSession对象
  val spark = SparkSession.builder().master("local").appName("spark sql").config(conf).getOrCreate();
  // 创建sparkContext对象
  val sc = spark.sparkContext

  // HBaseConfiguration 永远是 read in
  val tableName = "t_person"
  val hbaseConf = HBaseConfiguration.create()
  hbaseConf.set("hbase.zookeeper.quorum", "s12181,s12191,s12192")
  hbaseConf.set(TableInputFormat.INPUT_TABLE, tableName)

  // JobConf 是 write out
  // val hbaseAdmin = new HBaseAdmin(hbaseConf)
  val jobConf = new JobConf(hbaseConf, this.getClass)
  jobConf.setOutputFormat(classOf[TableOutputFormat])
  jobConf.set(TableOutputFormat.OUTPUT_TABLE, tableName)

  val pairs = sc.parallelize(List(("p_0000010", "12")))

  def convert(data: (String, String)) = {
    val p = new Put(Bytes.toBytes(data._1))
    p.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"), Bytes.toBytes(data._2))
    (new ImmutableBytesWritable, p)
  }

  // 保存数据到hbase数据库中
  new PairRDDFunctions(pairs.map(convert)).saveAsHadoopDataset(jobConf)
}

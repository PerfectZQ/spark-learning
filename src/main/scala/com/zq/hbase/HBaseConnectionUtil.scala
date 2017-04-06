package com.zq.hbase

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory}

/**
  * Created by ZhangQiang on 2017/1/11.
  */
object HBaseConnectionUtil {
  //常规方式获取链接
  def getHbaseConn(): Connection = {
    //获取配置
    val conf = HBaseConfiguration.create();
    conf.set("hbase.zookeeper.property.clientPort", "2181")
    conf.set("hbase.zookeeper.quorum", "s12181,s12191,s12192");
    val connection = ConnectionFactory.createConnection(conf);
    connection
  }

  //释放连接
  def releaseConn(connection: Connection): Unit =
  {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch {
      case ex: Exception => ex.getMessage
    }
  }
}

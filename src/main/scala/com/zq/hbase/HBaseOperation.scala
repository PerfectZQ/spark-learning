package com.zq.hbase

import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor, TableName}

/**
  * Created by ZhangQiang on 2017/1/11.
  */
object HBaseOperation {

  /**
    * 如果表不存在在，则创建一张表
    *
    * @param connection HBase连接会话
    * @param tableName  要创建的表名
    * @param families   列簇名数组
    */
  def createHTable(connection: Connection, tableName: String, families: Array[String]): Unit = {

    val table = TableName.valueOf(tableName)

    //Hbase表模式管理器
    val admin = connection.getAdmin
    //如果需要创建表
    if (!admin.tableExists(table)) {
      //创建Hbase表模式
      val tableDescriptor = new HTableDescriptor(table)

      families.foreach(family => {
        tableDescriptor.addFamily(new HColumnDescriptor(Bytes.toBytes(family)))
      })

      //创建表
      admin.createTable(tableDescriptor)
      println("create done.")
    }

  }

  //删除表
  def deleteHTable(connection: Connection, tableName: String): Unit = {
    //本例将操作的表名
    val table = TableName.valueOf(tableName)
    //Hbase表模式管理器
    val admin = connection.getAdmin
    if (admin.tableExists(table)) {
      admin.disableTable(table)
      admin.deleteTable(table)
    }

  }

  //插入记录
  def insertHTable(connection: Connection, tableName: String, family: String, column: String, key: String, value: String): Unit = {
    var table: Table = null
    try {
      val userTable = TableName.valueOf(tableName)
      table = connection.getTable(userTable)
      //准备key 的数据
      val p = new Put(key.getBytes)
      //为put操作指定 column 和 value
      p.addColumn(family.getBytes, column.getBytes, value.getBytes())
      //验证可以提交两个clomun？？？？不可以
      // p.addColumn(family.getBytes(),"china".getBytes(),"JAVA for china".getBytes())
      //提交一行
      table.put(p)
    } finally {
      if (table != null) table.close()
    }
  }

  //基于KEY查询某条数据
  def getAResult(connection: Connection, tableName: String, family: String, column: String, key: String): Unit = {
    var table: Table = null
    try {
      val userTable = TableName.valueOf(tableName)
      table = connection.getTable(userTable)
      val g = new Get(key.getBytes())
      val result = table.get(g)
      val value = Bytes.toString(result.getValue(family.getBytes(), column.getBytes()))
      println("key:" + value)
    } finally {
      if (table != null) table.close()
    }

  }

  //删除某条记录
  def deleteRecord(connection: Connection, tableName: String, family: String, column: String, key: String): Unit = {
    var table: Table = null
    try {
      val userTable = TableName.valueOf(tableName)
      table = connection.getTable(userTable)
      val d = new Delete(key.getBytes())
      d.addColumn(family.getBytes(), column.getBytes())
      table.delete(d)
      println("delete record done.")
    } finally {
      if (table != null) table.close()
    }
  }

  //扫描记录
  def scanRecord(connection: Connection, tableName: String, family: String, column: String): Unit = {
    var table: Table = null
    var scanner: ResultScanner = null
    try {
      val userTable = TableName.valueOf(tableName)
      table = connection.getTable(userTable)
      val s = new Scan()
      s.addColumn(family.getBytes(), column.getBytes())
      scanner = table.getScanner(s)
      println("scan...for...")
      var result: Result = scanner.next()
      while (result != null) {
        println("Found row:" + result)
        println("Found value: " + Bytes.toString(result.getValue(family.getBytes(), column.getBytes())))
        result = scanner.next()
      }
    } finally {
      if (table != null)
        table.close()
      scanner.close()
    }
  }
}

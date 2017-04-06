package com.zq.spark.RDD转换成DataFrame

import java.io.File

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by zhangqiang on 2016/12/23.
  */
object RDD转化为DataFrame {

  // 使用case定义schema(不能超过22个属性)，实现Person接口,必须在方法的作用于之外，即成员变量位置
  // 除非此object继承APP，scala会自动识别语句，并决定如何执行
  case class Person(name: String, age: Int)

  def main(args: Array[String]): Unit = {
    /**
      * 防止在windows local模式下启动spark, hadoop找不到文件报错
      */
    val path = new File(".").getCanonicalPath()
    System.getProperties().put("hadoop.home.dir", path);
    new File("./bin").mkdirs();
    new File("./bin/winutils.exe").createNewFile();

    /**
      * 将RDD转换成为DataFrame有两种方式
      * 以反射机制推断RDD模式
      * 以编程方式定义RDD模式
      */

    // 获取sparkSession
    val sparkSession: SparkSession = SparkSession.builder.master("local").appName("run something").getOrCreate()

    /**
      * 一、以反射机制推断RDD模式
      * 1、必须创建case类，因为只有case类才能隐式转换为DataFrame
      * 2、必须生成DataFrame，进行注册临时表的操作
      * 3、必须在内存中register成临时表，才能供查询使用
      */

    // For implicit conversions from RDDs to DataFrames
    // 必须在获取sqlContext(sparkSession)对象的语句之后
    import sparkSession.implicits._

    // Create an RDD of Person objects from a text file, convert it to a Dataframe
    val peopleDF = sparkSession.sparkContext
      .textFile("file:///F:\\spark\\spark-2.0.2-bin-hadoop2.6\\examples\\src\\main\\resources\\people.txt")
      .map(_.split(","))
      .map(attributes => Person(attributes(0), attributes(1).trim.toInt))
      .toDF()
    // Register the DataFrame as a temporary view
    peopleDF.createOrReplaceTempView("people")
    // SQL statements can be run by using the sql methods provided by Spark
    val teenagersDF = sparkSession.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")
    // The columns of a row in the result can be accessed by field index
    teenagersDF.map(teenager => "Name by field index : " + teenager(0)).show()
    // or by field name
    teenagersDF.map(teenager => "Name by field name : " + teenager.getAs[String]("name")).show()

    // No pre-defined encoders for Dataset[Map[K,V]], define explicitly
    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]
    // Primitive types and case classes can be also defined as
    // implicit val stringIntMapEncoder: Encoder[Map[String, Any]] = ExpressionEncoder()

    // row.getValuesMap[T] retrieves multiple columns at once into a Map[String, T]
    teenagersDF.map(teenager => teenager.getValuesMap[Any](List("name", "age"))).collect().foreach(map => println("map : " + map))
    // Array(Map("name" -> "Justin", "age" -> 19))

    /**
      * 一、以编程方式定义RDD模式
      * 1、从原始RDD中创建一个RowS的RDD
      * 2、创建一个表示为StructType类型的Schema，匹配在第一步创建的RDD的RowS的结构
      * 3、通过SQLContext提供的createDataFrame方法，应用Schema到RowS的RDD
      */

    // Create an RDD
    val peopleRDD = sparkSession.sparkContext.textFile("file:///F:\\spark\\spark-2.0.2-bin-hadoop2.6\\examples\\src\\main\\resources\\people.txt")
    // The schema is encoded in a string
    val schemaString = "name age"

    // Generate the schema based on the string of schema
    val fields = schemaString.split(" ")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)

    // Convert records of the RDD (people) to Rows
    val rowRDD = peopleRDD
      .map(_.split(","))
      .map(attributes => Row(attributes(0), attributes(1).trim))

    // Apply the schema to the RDD
    val peopleDF_ = sparkSession.createDataFrame(rowRDD, schema)

    // Creates a temporary view using the DataFrame
    peopleDF_.createOrReplaceTempView("people")

    // SQL can be run over a temporary view created using DataFrames
    val results = sparkSession.sql("SELECT name FROM people")

    // The results of SQL queries are DataFrames and support all the normal RDD operations
    // The columns of a row in the result can be accessed by field index or by field name
    results.map(attributes => "Name: " + attributes(0)).show()
  }
}

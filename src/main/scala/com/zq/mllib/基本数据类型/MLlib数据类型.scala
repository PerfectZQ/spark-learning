package com.zq.mllib.基本数据类型

import java.io.File

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by zhangqiang on 2016/12/28.
  *
  * 本地向量、标记点、本地矩阵、分布式矩阵、行矩阵、索引矩阵、三元组矩阵
  */
object MLlib数据类型 extends App {

  /**
    * 防止在windows local模式下启动spark, hadoop找不到文件报错
    */
  val path = new File(".").getCanonicalPath()
  System.getProperties().put("hadoop.home.dir", path);
  new File("./bin").mkdirs();
  new File("./bin/winutils.exe").createNewFile();

  /**
    * 创建SparkSession
    */
  val sparkSession: SparkSession = SparkSession.builder.master("local").appName("mllib数据类型").getOrCreate()

  /**
    * 本地向量
    * 基类是Vector，MLlib提供DenseVector(密集型向量)和sparseVector(稀疏型向量)两种实现
    */
  // 导入MLlib
  import org.apache.spark.mllib.linalg.{Vector, Vectors}

  // 创建(1.0,0.0,3.0)的密集向量
  val denseVector: Vector = Vectors.dense(1.0, 0.0, 3.0)
  // 创建(1.0,0.0,3.0)的数组类型的稀疏向量
  // 参数：向量的大小、下标索引、下表索引对应的值，其余补零
  val sparseArrVector: Vector = Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0))
  // 创建(1.0,0.0,3.0)的序列化的稀疏向量
  // 参数：向量的大小、二元元组类型的队列 Seq((下标索引,下标索引对应的值)(下标索引,下标索引对应的值))
  val sparseSeqVector: Vector = Vectors.sparse(3, Seq((0, 1.0), (2, 3.0)))

  /**
    * 标记点
    * 标记点是由一个本地向量(密集或稀疏)和一个标签(Int型和Double型)组成，在MLlib中主要被应用于
    * 回归和分类这样的的监督学习算法中
    * 例如：在二分类中，标记1表示正向，标记0表示反向
    * 多元分类中，标签的索引(下标)从0开始递增，如0,1,2,3...
    * 在回归计算问题中，标签可能是Double类型的值
    */

  import org.apache.spark.mllib.regression.LabeledPoint

  // 通过一个正相关的标签和一个密集的特征向量创建一个标记点
  val pos = LabeledPoint(1.0, Vectors.dense(1.0, 0.0, 3.0))
  // 通过一个负相关的标签和一个稀疏的特征向量创建一个标记点
  val neg = LabeledPoint(0.0, Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0)))

  /**
    * 稀疏数据
    * 系数数据是实践中很常用的训练数据，MLlib可以读取存储为LIBSVB格式的数据，
    * 其每一行代表一个带有标签的稀疏特征向量，格式如下
    * label index1:value1 index2:value2 ...
    * 其中label是标签值，index是索引，其值从1开始递增，加载完成之后索引被转换为从0开始
    * MLUtils.loadLibSVMFile读取以LIBSVM格式存储的训练实例如下
    */

  import org.apache.spark.mllib.util.MLUtils

  val examples: RDD[LabeledPoint] = MLUtils.loadLibSVMFile(sparkSession.sparkContext,"""file:///F:\spark\spark-2.0.2-bin-hadoop2.6\data\mllib\sample_libsvm_data.txt""")
  //  examples.collect().foreach(println)

  /**
    * 本地矩阵
    * 本地矩阵是由(Int类型行索引,Int类型列索引,Double值类型)组成，存放在单机中
    * mllib支持密集矩阵，密集矩阵的值以列优先的存储方式存储在一个Double类型的数组中，矩阵如下
    * 1.0 2.0
    * 3.0 4.0
    * 5.0 6.0
    * 这个3行2列的矩阵存储在一个一维数组[1.0,3.0,5.0,2.0,4.0,6.0]中
    * 本地矩阵的基类是Matrix，mllib只一共了一种实现：DenseMatrix
    */

  import org.apache.spark.mllib.linalg.Matrices

  // 创建一个密集矩阵((1.0,2.0),(3.0,4.0),(5.0,6.0))
  // 参数：行数、列数、由矩阵转换而来的列优先的一维数组
  val denseMatrix = Matrices.dense(3, 2, Array(1.0, 3.0, 5.0, 2.0, 4.0, 6.0))
  // 创建一个稀疏矩阵((9.0,0.0),(0.0,8.0),(0.0,6.0))
  // 第一个参数numRows:行数
  // 第二个参数numCols:列数
  // 第三个参数colPtrs:(the index corresponding to the start of a new column),
  //  如:第一列有(1-0)=1个数，行索引是0，第二列有(3-1)=2个数，行索引分别是2和1
  //  所以colPtrs的大小为numCols+1
  //  colPtrs数组的最后一位代表矩阵的非零元素的个数，必须和rowIndices、values数组的大小相同
  //  当第一个数colPtrs.head不为零，会取最后的(colPtrs.last-colPtrs.head)个值，其余的值被忽略掉了
  // 第四个参数rowIndices:每个非零元素对应的行索引，有几个非零元素就有几个行索引
  // 第五个参数values:非零值，数组大小和rowIndices一定相同
  val sparseMatrix = Matrices.sparse(3, 2, Array(0, 1, 3), Array(0, 2, 1), Array(9, 6, 8))
  println(sparseMatrix.toString())

  /**
    * 分布式矩阵
    * 分布式矩阵由(long类型行索引，Long类型列索引，Double类型值)组成，分布存储在一个或多个RDD中
    * 因为要缓存矩阵的大小，所以分布还是矩阵底层的RDD必须是确定的，选择正确的格式来存储巨大的分布式矩阵是非常重要的，
    * 否则会导致错误的出现，另外转换分布式矩阵的格式需要全局shuffle，这个代价亦是相当的昂贵
    * mllib实现了三种分布式矩阵
    */

  /** 基础的：行矩阵RowMatrix，面向行的分布式矩阵，行索引无具体的意义，每行是一个本地向量
    * RowMatrix可以由RDD[Vector]实例创建 */

  /* // 一个本地向量的RDD[Vector]
   val vectorRdd: RDD[Vector] = _
   // 从一个RDD[Vector]创建一个行矩阵
   val rowMatrix = new RowMatrix(vectorRdd)
   // 获取行矩阵的行和列
   val m = rowMatrix.numRows()
   val n = rowMatrix.numCols()*/

  /** 行索引矩阵IndexRowMatrix，与RowMatrix相似，但是不同的是其行索引有意义，本质上是一个含有索引信息的行数据集合
    * 每一行由Long类型行索引和本地向量组成.IndexRowMatrix可以由RDD[IndexedRow]实例创建，IndexedRow的格式是(Long,Vector) */

  /* // 创建一个行索引的RDD[IndexedRow]
   val indexedRowRDD: RDD[IndexedRow] = _
   // 从一个行索引的RDD[IndexedRow]创建一个行索引矩阵
   val indexedRowMatrix = new IndexedRowMatrix(indexedRowRDD)
   // 获取行索引矩阵的行和列的方式同上
   // Drop行索引，讲一个行索引矩阵转换成一个普通的行矩阵
   val rowMatrix_ = indexedRowMatrix.toRowMatrix()*/

  /** 三元组矩阵CoordinateMatrix，其实体集合是RDD，每个实体是一个(Long类型行索引，Long类型列索引，Double类型值)的三元组
    * 只有当矩阵是一个行与列特别多的系数矩阵时，才会使用CoordinateMatrix，一个CoordinateMatrix可以从RDD[MatrixEntry]
    * 实例创建，MatrixEntry是(Long,Long,Double)的封装类 */

  /*  // 一个矩阵的RDD[MatrixEntry]
    val entries: RDD[MatrixEntry] = _
    // 创建一个三元组矩阵
    val coordinateMatrix = new CoordinateMatrix(entries)
    // 将三元组矩阵转换成一个(行是稀疏向量)行索引矩阵
    val indexedRowMatrix_ = coordinateMatrix.toIndexedRowMatrix()*/

  /** 因为要缓存矩阵的大小，分布式矩阵底层的RDD必须是确定的，如果使用非确定的RDD会导致错误的出现 */
}


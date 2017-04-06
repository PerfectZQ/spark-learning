package com.zq.mllib.分类和回归

import java.io.File

import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.sql.SparkSession

/**
  * Created by zhangqiang on 2017/2/6.
  */
object 分类决策树 extends App {

  /**
    * 防止在windows local模式下启动spark, hadoop找不到文件报错
    */
  val path = new File(".").getCanonicalPath()
  System.getProperties().put("hadoop.home.dir", path);
  new File("./bin").mkdirs();
  new File("./bin/winutils.exe").createNewFile();

  val sparkSession = SparkSession.builder().master("local").appName("Classification Decision Tree").getOrCreate()
  val sc = sparkSession.sparkContext
  // loadLibSVMFile将 LIBSVM格式的二进制标识数据转换成一个DataFrame[LabeledPoint]
  //  val data = sparkSession.read.format("libsvm").load("file:///usr/zhangqiang/spark-2.0.2/data/mllib/sample_libsvm_data.txt")
  // loadLibSVMFile将 LIBSVM格式的二进制标识数据转换成一个RDD[LabeledPoint]
  val data = MLUtils.loadLibSVMFile(sc, "F:\\spark\\spark-2.0.2-bin-hadoop2.6\\data\\mllib\\sample_libsvm_data.txt")
  // 将一个RDD随机分成两个比例是7:3的RDD
  val splits = data.randomSplit(Array(0.7, 0.3))
  val (trainingData, testData) = (splits(0), splits(1))

  /** 训练决策树模型
    * train decision tree model
    * 空的分类特征信息说明所有的特征是连续的，为0。
    * empty categoricalFeatureInfo indicates all features are continuous
    */
  // 分类数量
  val numClasses = 2
  // 分类特征信息类型格式
  val categoricalFeaturesInfo = Map[Int, Int]()
  // 信息增益方式，分裂指标 --- Spark MLlib 决策树支持三种方式：
  // 分类决策树的(Classification) gini(获益系数),entropy(熵)
  // 回归决策树的(Regression 观察值是连续的) variance(方差)
  // 计算（杂质）不纯度的方式
  val impurity = "gini"
  // 树最大深度
  val maxDepth = 5
  // 最大叶子节点数
  val maxBins = 32
  // 决策树类型：分类决策树 Classification，回归决策树 Regression
  //  val algo = "Classification"
  // 训练模型
  val model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo, impurity, maxDepth, maxBins)
  println(s"model's depth is ${model.depth},model's numNodes is ${model.numNodes}")

  // 通过测试数据集评估模型，并计算错误率
  // Evaluating model on test instances an compute test error
  val labelAndPreds = testData.map(point => {
    // model 在yarn client 和 spark standalone 模式下运行会抛出空指针异常
    val prediction = model.predict(point.features)
    println("label : " + point.label + "\nprediction : " + prediction)
    (point.label, prediction)
  })
  // 计算错误率
  val testError = labelAndPreds.filter(tuple => tuple._1 != tuple._2).map(tuple => println(s"")).count().toDouble / testData.count
  println(s"testError: $testError")
  // 输出调试数据信息
  println("Learned classification tree model:\n" + model.toDebugString)

  // 保存树模型
  model.save(sc, "hdfs:///data-zhangqiang/classificationModel")
  // 加载模型
  val sameModel = DecisionTreeModel.load(sc, "hdfs:///data-zhangqiang/classificationModel")
}

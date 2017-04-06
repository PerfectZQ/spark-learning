package com.zq.mllib

import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.stat.{MultivariateStatisticalSummary, Statistics}

/**
  * Created by zhangqiang on 2016/12/29.
  *
  * 基本统计方法包括：汇总统计(summary statistics)、相似性分析(correlations)、分层抽样(stratified sampling)、
  * 假设检验(hypothesis testing)、随机数生成(random data generation)、核密度估计(kernel density estimation)
  * 在mllib的统计包里实现了KolmogorovSmirnov检验，用以检验两个经验分布是否不同或一个经验分布于另一个理想分布是否不同
  */
object 基本统计方法 extends App{

  /*// 一个向量RDD
  val observations:RDD[Vector] = _
  // 计算列汇总统计
  val summary:MultivariateStatisticalSummary = Statistics.colStats(observations)
  // 包括每一列平均值的密集向量
  println(summary.mean)
  // 列方差
  println(summary.variance)
  // 每一行的非零
  println(summary.numNonzeros)*/

}

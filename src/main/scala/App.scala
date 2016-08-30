import com.datastax.spark.connector._
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

// define main method (scala entry point)
object App {

  def main(args: Array[String]) {

    // initialize the spark context
    val conf = new SparkConf()
      .setAppName("HelloHawkular")
      .setMaster("local")
      .set("spark.cassandra.connection.host", "127.0.0.1")
    val sc = new SparkContext(conf)

    // this RDD represents the cassandra table
    val rdd: RDD[CassandraRow] = sc.cassandraTable("hawkular_metrics", "data")
    println(rdd.count)
    println(rdd.first)

    // prepare the RDDs from the cassandra SST table by filtering only the relevant data
    val feedId = "e69a19f7-76e7-4fd2-8ed5-864d1570f3ff"
    val metric1 = "Total Memory"
    val metric2 = "Available Memory"
    val total: RDD[Double] = rdd.filter(x => {
        val metricId = x.getString("metric")
        metricId.contains(feedId) && metricId.contains(metric1)
      })
      .map(_.getDouble("n_value"))
      .repartition(8)

    val free: RDD[Double] = rdd.filter(x => {
        val metricId = x.getString("metric")
        metricId.contains(feedId) && metricId.contains(metric2)
      })
      .map(_.getDouble("n_value"))
      .repartition(8)

    // create a new RDD based on other ones
    val used: RDD[Double] = total.zip(free).map(e => e._1 - e._2)

    // calculate the correlation between two RDDs
    val correlation: Double = Statistics.corr(used, free, "pearson")
    println(s"Correlation is: $correlation")

    // clustering
    val usedMemoryVector = used.map(x => Vectors.dense(x))
    val numClusters = 3
    val numIterations = 20
    val clusters: KMeansModel = KMeans.train(usedMemoryVector, numClusters, numIterations)
    println("Cluster centers:")
    clusters.clusterCenters.foreach(println)

    // terminate spark context
    sc.stop()

  }
}
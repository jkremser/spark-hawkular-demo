import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

// define main method (scala entry point)
object App {
  def main(args: Array[String]) {

    // initialise spark context
    val conf = new SparkConf().setAppName("HelloWorld").setMaster("local")
    val sc = new SparkContext(conf)

    // do stuff
    println("Hello, world!")

    // terminate spark context
    sc.stop()

  }
}
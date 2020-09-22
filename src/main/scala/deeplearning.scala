import java.awt.image.BufferedImage

import ai.djl.Application
import ai.djl.modality.Classifications
import ai.djl.repository.zoo.{Criteria, ModelZoo}
import ai.djl.training.util.ProgressBar
import javax.imageio.ImageIO
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf


object deeplearning {

  def main(args: Array[String]): Unit = {

  val conf = new SparkConf()
    .setAppName("Simple Image Classification")
    .setMaster("local[*]")
    .setExecutorEnv("MXNET_ENGINE_TYPE", "NaiveEngine")
  //The NaiveEngine argument is required for multi-threaded inference in MXNet. If using PyTorch or TensorFlow, the following line can be removed
  val sc = new SparkContext(conf)

  val partitions = sc.binaryFiles("/Users/sachinarora/Downloads/flow.jpg")

    partitions.collect()

  // Start assign work for each worker node
  val result = partitions.mapPartitions( partition => {
    // before classification
    val criteria = Criteria.builder
      .optApplication(Application.CV.IMAGE_CLASSIFICATION)
      .setTypes(classOf[BufferedImage], classOf[Classifications])
      .optFilter("dataset", "imagenet")
      .optFilter("layers", "50")
      .optProgress(new ProgressBar)
      .build
    val model = ModelZoo.loadModel(criteria)
    val predictor = model.newPredictor()
    // classification
    partition.map(streamData => {
      val img = ImageIO.read(streamData._2.open())
      predictor.predict(img).toString
    })
  })

  result.collect().foreach(print)
  result.saveAsTextFile("output directory to write output")

}
}
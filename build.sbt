name := "deeplearning"

version := "0.1"

scalaVersion := "2.11.0"


libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.0"
libraryDependencies += "ai.djl" % "api" % "0.5.0"
libraryDependencies += "ai.djl" % "repository" % "0.4.1"
// Using MXNet Engine
libraryDependencies += "ai.djl.mxnet" % "mxnet-model-zoo" % "0.5.0"
libraryDependencies += "ai.djl.mxnet" % "mxnet-native-auto" % "1.6.0"
// https://mvnrepository.com/artifact/ai.djl.mxnet/mxnet-engine
libraryDependencies += "ai.djl.mxnet" % "mxnet-engine" % "0.5.0"


package utils

import com.typesafe.config.ConfigFactory

class DataCenterConfigHelper(simulation:String, model:String) {

  val conf = ConfigFactory.load(model)
  val path = simulation + "."+"dataCenter"
  val cost: Double = conf.getDouble(path +"."+"cost")
  val costPerMemory = conf.getDouble(path +"."+"costPerMemory")
  val costPerStorage = conf.getDouble(path +"."+"costPerStorage")
  val costPerBw = conf.getDouble(path +"."+"costPerBw")
  val os = conf.getString(path +"."+"os")
}

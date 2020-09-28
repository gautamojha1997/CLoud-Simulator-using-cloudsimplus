package utils

import com.typesafe.config.ConfigFactory

/*This class is used to take datacenter specs values from config file*/

class DataCenterConfigHelper(simulation:String, model:String) {

  val conf = ConfigFactory.load(model) //load the config file
  val path = simulation + "."+"dataCenter" //get the path of the simulation config
  val cost: Double = conf.getDouble(path +"."+"cost") //get the cost per sec
  val costPerMemory = conf.getDouble(path +"."+"costPerMemory") // get cost per memory
  val costPerStorage = conf.getDouble(path +"."+"costPerStorage") // get cost per storage
  val costPerBw = conf.getDouble(path +"."+"costPerBw") // get cost per band width
  val os = conf.getString(path +"."+"os") // get the os type
}

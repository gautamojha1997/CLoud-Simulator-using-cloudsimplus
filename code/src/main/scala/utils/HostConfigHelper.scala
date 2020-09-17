package utils

import com.typesafe.config.ConfigFactory

class HostConfigHelper(simulation:String,model:String) {

  val conf = ConfigFactory.load(model)
  val path = simulation + "."+"host"
  val mips = conf.getInt(path+"."+"mips")
  val ram  = conf.getInt(path+"."+"ram")
  val storage = conf.getInt(path+"."+"storage")
  val bw = conf.getInt(path+"."+"bw")
  val cores = conf.getInt(path+"."+"cores")
  val numberofhost = conf.getInt(path+"."+"numberofhost")
}

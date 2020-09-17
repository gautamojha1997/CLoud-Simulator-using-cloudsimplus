package utils

import com.typesafe.config.ConfigFactory

class VmConfigHelper(simulation:String, model:String) {
  val conf = ConfigFactory.load(model)
  val path = simulation + "."+"vm"
  val mips = conf.getInt(path+"."+"mips")
  val size = conf.getInt(path+"."+"size")
  val ram = conf.getInt(path+"."+"ram")
  val bw = conf.getInt(path+"."+"bw")
  val pesNumber = conf.getInt(path+"."+"pesNumber")
  val numberofvm = conf.getInt(path+"."+"numberofvm")
}

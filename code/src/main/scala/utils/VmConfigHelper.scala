package utils

import com.typesafe.config.ConfigFactory
/*This class is used to take Vm specs values from config file*/
class VmConfigHelper(simulation:String, model:String) {
  val conf = ConfigFactory.load(model)//load the config file
  val path = simulation + "."+"vm" //get the path of the simulation config
  val mips = conf.getInt(path+"."+"mips") //get mips for vm
  val size = conf.getInt(path+"."+"size") // get storage size for vm
  val ram = conf.getInt(path+"."+"ram") //get ram for vm
  val bw = conf.getInt(path+"."+"bw") // get bw for vm
  val pesNumber = conf.getInt(path+"."+"pesNumber") // get number of cores for vm
  val numberofvm = conf.getInt(path+"."+"numberofvm") //get number of vm
}

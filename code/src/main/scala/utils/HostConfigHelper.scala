package utils

import com.typesafe.config.ConfigFactory
/*This class is used to take Host specs values from config file*/
class HostConfigHelper(simulation:String,model:String) {

  val conf = ConfigFactory.load(model) //load the config file
  val path = simulation + "."+"host" //get the path of the simulation config
  val mips = conf.getInt(path+"."+"mips") //get mips for host
  val ram  = conf.getInt(path+"."+"ram") //get ram for host
  val storage = conf.getInt(path+"."+"storage") // get storage for host
  val bw = conf.getInt(path+"."+"bw") //get bw for host
  val cores = conf.getInt(path+"."+"cores") // get number of cores for host
  val numberofhost = conf.getInt(path+"."+"numberofhost") // get number of hosts
}

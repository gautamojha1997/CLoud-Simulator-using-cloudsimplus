package utils

import com.typesafe.config.ConfigFactory

/*Class used to take Cloudlet specs values from config files which is helpful in simulations*/
class CloudletConfigHelper(simulation: String, model: String) {

  val conf = ConfigFactory.load(model) //load the config file
  val path = simulation + "."+"cloudLet" //get the path of the simulation config
  val length = conf.getInt(path+"."+"length") //get length of cloudlet
  val pesNumber = conf.getInt(path+"."+"pesNumber") //get number of cores of cloudlet
  val numberofCL = conf.getInt(path+"."+"numberofCL") //get number of cloudlet
  val utilization = conf.getString(path+ "." +"utilization") //get utilization type of cloudlet
}

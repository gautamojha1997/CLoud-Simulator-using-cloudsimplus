package utils

import com.typesafe.config.ConfigFactory


class CloudletConfigHelper(simulation: String, model: String) {

  val conf = ConfigFactory.load(model)
  val path = simulation + "."+"cloudLet"
  val length = conf.getInt(path+"."+"length")
  val pesNumber = conf.getInt(path+"."+"pesNumber")
  val numberofCL = conf.getInt(path+"."+"numberofCL")
}

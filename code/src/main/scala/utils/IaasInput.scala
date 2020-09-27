package utils

import org.slf4j.{Logger, LoggerFactory}

class IaasInput {

  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  val helper: DataCenterHelper = new DataCenterHelper

  println("Enter Number of Vms for IAAS:")
  var iaasNumVms = scala.io.StdIn.readInt()
  println("Enter the OS required:")
  var os = scala.io.StdIn.readLine()

  println("Enter Vm MIPS for IAAS:")
  var vmips = scala.io.StdIn.readLong()
  println("Enter Vm Size for IAAS:")
  var vsize = scala.io.StdIn.readLong()
  println("Enter Vm Ram for IAAS:")
  var vram = scala.io.StdIn.readLong()
  println("Enter Vm BW for IAAS:")
  var vbw = scala.io.StdIn.readLong()
  println("Enter Vm Cores for IAAS:")
  var vcores = scala.io.StdIn.readLong()




}

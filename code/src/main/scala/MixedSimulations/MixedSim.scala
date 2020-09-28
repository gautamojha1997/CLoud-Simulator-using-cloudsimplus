package MixedSimulations

import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyBestFit
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.hosts.Host
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared
import org.cloudbus.cloudsim.vms.Vm
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.{Logger, LoggerFactory}
import utils.{CloudletConfigHelper, DataCenterConfigHelper, DataCenterHelper, HostConfigHelper, IaasHelper, IaasInput, PaasHelper, PaasInput, SaasHelper, VmConfigHelper}

import collection.JavaConverters._
/*This is the Mixed Simulations class which creates
one broker and 3 data centers and runs 3 services viz. IAAS, PAAS, SAAS.*/
object MixedSim extends App {


  //Logging start of simulation
  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  logger.info("Starting Mixed Simulation")

  //accessing the config files
  val conf = ConfigFactory.load("MixedSimulations")

  //Creates a CloudSim object to initialize the simulation.
  logger.info("Instantiating Cloudsim")
  val cloudsim = new CloudSim

  val helper: DataCenterHelper = new DataCenterHelper


  /*Creates a Broker that will act on behalf of a cloud user (customer). The Broker assigns cloudlet to the service related DataCenter*/
  logger.info("Creating Broker")
  val broker = helper.createBroker(cloudsim)

  //Instantiating Helper classes to run services simulations (Iaas, Saas, Paas)
  val paasHelper: PaasHelper = new PaasHelper
  val saasHelper: SaasHelper = new SaasHelper
  val iaasHelper: IaasHelper = new IaasHelper

  val cloudlets_iaas: List[Cloudlet] =  iaasHelper.iaas()
  val cloudlets_paas: List[Cloudlet] = paasHelper.paas()
  val cloudlets_saas: List[Cloudlet] =  saasHelper.saas()

  //starting cloudsim
  cloudsim.start

  //printing status for the simluation.
  logger.info("Printing the simulation result!")
  new CloudletsTableBuilder(broker.getCloudletFinishedList).build()
  logger.info("Overall Cost for this IAAS simulation - " + cloudlets_iaas.map(helper.overallCost).sum)
  logger.info("Overall Cost for this  PAAS simulation - " + cloudlets_paas.map(helper.overallCost).sum)
  logger.info("Overall Cost for this SAAS simulation - " + cloudlets_saas.map(helper.overallCost).sum)
}

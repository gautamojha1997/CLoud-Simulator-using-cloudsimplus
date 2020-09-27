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

object MixedSim extends App {


  //Logging start of simulation
  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  logger.info("Starting Mixed Simulation")

  //accessing the config files
  val conf = ConfigFactory.load("MixedSimulations")

  //Creates a CloudSim object to initialize the simulation.
  val cloudsim = new CloudSim

  val helper: DataCenterHelper = new DataCenterHelper


  /*Creates a Broker that will act on behalf of a cloud user (customer).*/
  val broker = helper.createBroker(cloudsim)

  val paasHelper: PaasHelper = new PaasHelper
  val saasHelper: SaasHelper = new SaasHelper
  val iaasHelper: IaasHelper = new IaasHelper

  iaasHelper.iaas()
  paasHelper.paas()
  saasHelper.saas()

  //starting cloudsim
  cloudsim.start

  //printing status for the simluation.
  new CloudletsTableBuilder(broker.getCloudletFinishedList).build()
}

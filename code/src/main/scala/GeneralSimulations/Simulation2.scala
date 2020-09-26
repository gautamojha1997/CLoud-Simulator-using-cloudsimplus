package GeneralSimulations

import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyBestFit, VmAllocationPolicyWorstFit}
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.hosts.Host
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerSpaceShared
import org.cloudbus.cloudsim.vms.Vm
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.{Logger, LoggerFactory}
import utils._

import collection.JavaConverters._

object Simulation2 extends App {

  //Logging start of simulation
  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  logger.info("Starting Simulation 2")

  //accessing the config files
  val conf = ConfigFactory.load("GeneralSimulations")


  //Creates a CloudSim object to initialize the simulation.
  val cloudsim = new CloudSim

  val helper: DataCenterHelper = new DataCenterHelper

  /*Creates a Broker that will act on behalf of a cloud user (customer).*/

  val broker = helper.createBroker(cloudsim)

  var numVms = conf.getInt("simulation2.vm.numberofvm")
  var numHosts = conf.getInt("simulation2.host.numberofhost")
  var numcl = conf.getInt("simulation2.cloudLet.numberofCL")


  //Creating Host
  val hosttemp: HostConfigHelper = new HostConfigHelper(simulation = "simulation1", model = "GeneralSimulations")
  val hostList: List[Host] = List.tabulate(numHosts)(i => helper.createHost(hosttemp, new VmSchedulerSpaceShared))


  //Creates a Datacenter with a list of Hosts.
  //Uses a VmAllocationPolicySimple by default to allocate VMs
  val dc0_temp: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "simulation2", model = "GeneralSimulations")
  //var dc0 = helper.createSimpleDc(dc0_temp, cloudsim, hostList.asJava, new VmAllocationPolicyBestFit)
  val dc0 = helper.createNetworkDc(dc0_temp, cloudsim,hostList.asJava, new VmAllocationPolicyWorstFit)

  //Network Topology
  val topology = "topology.brite"
  helper.configureNetwork(topology, cloudsim, dc0, broker)


  //Creates VMs to run applications.
  val vmtemp: VmConfigHelper = new VmConfigHelper(simulation = "simulation1", model = "GeneralSimulations")
  val vmList: List[Vm] = List.tabulate(numVms)(i => helper.createVms(vmtemp))

  //Creating cloudlets variable and list and calling createCloudlets()
  val cltemp: CloudletConfigHelper = new CloudletConfigHelper(simulation = "simulation1", model = "GeneralSimulations")
  val cloudlets: List[Cloudlet] = List.tabulate(numcl)(i => helper.createCloudLets(cltemp))

  //submitting vmlist and cloudletlist to broker.
  broker.submitVmList(vmList.asJava)
  broker.submitCloudletList(cloudlets.asJava)

  //starting cloudsim
  cloudsim.start

  //printing status for the simluation.
  new CloudletsTableBuilder(broker.getCloudletFinishedList).build()


}

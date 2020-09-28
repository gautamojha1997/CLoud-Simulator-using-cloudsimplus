package GeneralSimulations

import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyBestFit, VmAllocationPolicyRoundRobin}
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.hosts.Host
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared
import org.cloudbus.cloudsim.vms.Vm
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.{Logger, LoggerFactory}
import utils._

import collection.JavaConverters._
/*This is a general simulation class to obtain normal simulation result based on the config values and policies.*/
object Simulation1 extends App {

  //Logging start of simulation
  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  logger.info("Starting Simulation 1")

  //Accessing the config files
  val conf = ConfigFactory.load("GeneralSimulations")


  //Creates a CloudSim object to initialize the simulation.
  logger.info("Instantiating Cloudsim")
  val cloudsim = new CloudSim

  val helper: DataCenterHelper = new DataCenterHelper

  /*Creates a Broker that will act on behalf of a cloud user (customer).*/
  logger.info("Creating Broker")
  val broker = helper.createBroker(cloudsim)

  //Getting number of vm, host and cloudlets from config files
  var numVms = conf.getInt("simulation1.vm.numberofvm")
  var numHosts = conf.getInt("simulation1.host.numberofhost")
  var numcl = conf.getInt("simulation1.cloudLet.numberofCL")


  //Creating Host
  logger.info("Creating host")
  val hosttemp: HostConfigHelper = new HostConfigHelper(simulation = "simulation1", model = "GeneralSimulations")
  val hostList: List[Host] = List.tabulate(numHosts)(i => helper.createHost(hosttemp, new VmSchedulerTimeShared))

  //Creates a Datacenter with a list of Hosts.
  //Uses a VmAllocationPolicyRoundRobin to allocate VMs
  logger.info("Creating Datacenter")
  val dc0_temp: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "simulation1", model = "GeneralSimulations")
  var dc0 = helper.createSimpleDc(dc0_temp, cloudsim, hostList.asJava, new VmAllocationPolicyRoundRobin)


  //Network Topology
  val topology = "topology.brite"
  helper.configureNetwork(topology, cloudsim, dc0, broker)


  logger.info("Creating Vm and Cloulets")
  //Creates VMs to run applications.
  val vmtemp: VmConfigHelper = new VmConfigHelper(simulation = "simulation1", model = "GeneralSimulations")
  val vmList: List[Vm] = List.tabulate(numVms)(i => helper.createVms(vmtemp))

  //Creating cloudlets variable and list and calling createCloudlets()
  val cltemp: CloudletConfigHelper = new CloudletConfigHelper(simulation = "simulation1", model = "GeneralSimulations")
  val cloudlets: List[Cloudlet] = List.tabulate(numcl)(i => helper.createCloudLets(cltemp))


  //submitting vmlist and cloudletlist to broker.
  logger.info("Submitting List of Vms and Cloudlets to broker.")
  broker.submitVmList(vmList.asJava)
  broker.submitCloudletList(cloudlets.asJava)

  //starting cloudsim
  cloudsim.start

  //printing status for the simluation.
  logger.info("Printing the simulation result!")
  new CloudletsTableBuilder(broker.getCloudletFinishedList).build()


  logger.info("Overall Cost for this simulation - " + cloudlets.map(helper.overallCost).sum)
}

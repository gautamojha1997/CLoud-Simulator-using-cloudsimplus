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
import utils.{CloudletConfigHelper, DataCenterConfigHelper, DataCenterHelper, HostConfigHelper, VmConfigHelper}

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

  /*....IAAS IMPLEMENTATION....*/
  println("Enter Number of Vms for IAAS:")
  var iaasNumVms = scala.io.StdIn.readInt()
  var iaasNumHosts = conf.getInt("mixed.host.numberofhost")
  var iaasNumcl = conf.getInt("mixed.cloudLet.numberofCL")

  println("Enter the OS for IAAS:")
  var os = scala.io.StdIn.readLine()
  //Creating Host
  val hosttemp_iaas: HostConfigHelper = new HostConfigHelper(simulation = "mixed", model = "MixedSimulations")
  val hostList_iaas: List[Host] = List.tabulate(iaasNumHosts)(i => helper.createHost(hosttemp_iaas, new VmSchedulerTimeShared))

  //Creates a Datacenter with a list of Hosts.
  //Uses a VmAllocationPolicySimple by default to allocate VMs
  val dc0_temp: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "iaas", model = "MixedSimulations")
  var dc0 = helper.createIaasDc(dc0_temp, cloudsim, hostList_iaas.asJava, new VmAllocationPolicyBestFit,os)

  //Creates VMs to run applications.
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

  val vmList_iaas: List[Vm] = List.tabulate(iaasNumVms)(i => helper.createIaasVms(vmips,vram, vbw, vsize, vcores))

  //Creating cloudlets variable and list and calling createCloudlets()
  val cltemp: CloudletConfigHelper = new CloudletConfigHelper(simulation = "mixed", model = "MixedSimulations")
  val cloudlets_iaas: List[Cloudlet] = List.tabulate(iaasNumcl)(i => helper.createCloudLets(cltemp))

  cloudlets_iaas.map(i => i.assignToDatacenter(dc0))

  //submitting vmlist and cloudletlist to broker.
  broker.submitVmList(vmList_iaas.asJava)
  broker.submitCloudletList(cloudlets_iaas.asJava)


  /*....PAAS IMPLEMENTATION....*/
  println("Choose the programming language for Paas- 1. Scala, 2. Java, 3. Python, 4. JavaScript : ")
  var plang = scala.io.StdIn.readLine()

  println("Choose the Framework according to programming language - 1. Akka Http, 2. Spring Boot, 3. DJango, 4. NodeJs :")
  var framework = scala.io.StdIn.readLine()

  println("Choose the DataBase Management Software - 1. MongoDB, 2. MYSQL, 3. PostgresSQL :")
  var db = scala.io.StdIn.readLine()

  var paasNumVms = conf.getInt("mixed.vm.numberofvm")
  var paasNumHosts = conf.getInt("mixed.host.numberofhost")
  var paasNumcl = conf.getInt("mixed.cloudLet.numberofCL")

  //Creating Host
  val paasHosttemp: HostConfigHelper = new HostConfigHelper(simulation = "mixed", model = "MixedSimulations")
  val paasHostList: List[Host] = List.tabulate(paasNumHosts)(i => helper.createHost(paasHosttemp, new VmSchedulerTimeShared))

  //Creates a Datacenter with a list of Hosts.
  //Uses a VmAllocationPolicySimple by default to allocate VMs
  val dc0_temp_paas: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "paas", model = "MixedSimulations")
  var dc1 = helper.createSimpleDc(dc0_temp_paas, cloudsim, paasHostList.asJava, new VmAllocationPolicyBestFit)

  //Creates VMs to run applications.
  val vmtemp_paas: VmConfigHelper = new VmConfigHelper(simulation = "mixed", model = "MixedSimulations")
  val vmList_paas: List[Vm] = List.tabulate(paasNumVms)(i => helper.createVms(vmtemp_paas))


  //Creating cloudlets variable and list and calling createCloudlets()
  val cltemp_paas: CloudletConfigHelper = new CloudletConfigHelper(simulation = "mixed", model = "MixedSimulations")
  val cloudlets_paas: List[Cloudlet] = List.tabulate(paasNumcl)(i => helper.createCloudLets(cltemp_paas))

  cloudlets_paas.map(i => i.assignToDatacenter(dc1))

  //submitting vmlist and cloudletlist to broker.
  broker.submitVmList(vmList_paas.asJava)
  broker.submitCloudletList(cloudlets_paas.asJava)




  /*....SAAS IMPLEMENTATION....*/

  var numVms_saas = conf.getInt("mixed.vm.numberofvm")
  var numHosts_saas = conf.getInt("mixed.host.numberofhost")

  var numcl_saas = conf.getInt("mixed.cloudLet.numberofCL")


  //Creating Host
  val hosttemp_saas: HostConfigHelper = new HostConfigHelper(simulation = "mixed", model = "MixedSimulations")
  val hostList_saas: List[Host] = List.tabulate(numHosts_saas)(i => helper.createHost(hosttemp_saas, new VmSchedulerTimeShared))

  //Creates a Datacenter with a list of Hosts.
  //Uses a VmAllocationPolicySimple by default to allocate VMs
  val dc0_temp_saas: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "saas", model = "MixedSimulations")
  var dc2 = helper.createSimpleDc(dc0_temp_saas, cloudsim, hostList_saas.asJava, new VmAllocationPolicyBestFit)



  //Creates VMs to run applications.
  val vmtemp_saas: VmConfigHelper = new VmConfigHelper(simulation = "mixed", model = "MixedSimulations")
  val vmList_saas: List[Vm] = List.tabulate(numVms_saas)(i => helper.createVms(vmtemp_saas))

  //Creating cloudlets variable and list and calling createCloudlets()
  val cltemp_saas: CloudletConfigHelper = new CloudletConfigHelper(simulation = "mixed", model = "MixedSimulations")
  val cloudlets_saas: List[Cloudlet] = List.tabulate(numcl_saas)(i => helper.createCloudLets(cltemp_saas))

  cloudlets_saas.map(i => i.assignToDatacenter(dc2))

  //submitting vmlist and cloudletlist to broker.
  broker.submitVmList(vmList_saas.asJava)
  broker.submitCloudletList(cloudlets_saas.asJava)

  //starting cloudsim
  cloudsim.start

  //printing status for the simluation.
  new CloudletsTableBuilder(broker.getCloudletFinishedList).build()

}

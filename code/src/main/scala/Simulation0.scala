import java.util

import org.slf4j.{Logger, LoggerFactory}
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyBestFit, VmAllocationPolicyRandom, VmAllocationPolicyRoundRobin, VmAllocationPolicyWorstFit}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletSchedulerTimeShared
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModelDynamic, UtilizationModelFull}
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import utils.{CloudletConfigHelper, DataCenterConfigHelper, DataCenterHelper, HostConfigHelper, VmConfigHelper}
import collection.mutable._
import collection.JavaConverters._
import org.cloudbus.cloudsim.network.topologies.BriteNetworkTopology
import org.cloudbus.cloudsim.network.topologies.NetworkTopology

object Simulation0 extends App {

  //Logging start of simulation
  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  logger.info("Starting Simulation 0")

  //accessing the config files
  val conf = ConfigFactory.load("GeneralSimulations")


  //Creates a CloudSim object to initialize the simulation.
  val cloudsim = new CloudSim

  val helper: DataCenterHelper = new DataCenterHelper

  /*Creates a Broker that will act on behalf of a cloud user (customer).*/

  val broker = helper.createBroker(cloudsim)

  var numVms = conf.getInt("simulation0.vm.numberofvm")
  var numHosts = conf.getInt("simulation0.host.numberofhost")
  val numdcs = conf.getInt("simulation0.dataCenter.numberofDc")
  var numcl = conf.getInt("simulation0.cloudLet.numberofCL")
  //Creating hostPes
  /*val hostPes = new util.ArrayList[Pe](1)
  //Uses a PeProvisionerSimple by default to provision PEs for VMs
  hostPes.add(new PeSimple(conf.getInt("simulation0.host0.mips")))*/

  //Creating Host
  /*val host0: Host = createHost()
  val hostList = new util.ArrayList[Host](1)
  hostList.add(host0)*/
  val hostList = new util.ArrayList[Host](1)
  while(numHosts>0){
    numHosts -= 1
    val hosttemp : HostConfigHelper = new HostConfigHelper(simulation = "simulation0",model = "GeneralSimulations")
    val host = helper.createHost(hosttemp)
    hostList.add(host)
  }

  val hlist : Seq[Host] = hostList.asScala
  //Creates a Datacenter with a list of Hosts.
  //Uses a VmAllocationPolicySimple by default to allocate VMs
  val dc_type = conf.getString("simulation0.dataCenter.type")
  val dc0_temp : DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "simulation0",model = "GeneralSimulations")
  //val dc0 = helper.createNetworkDc(dc0_temp, cloudsim,hostList, new VmAllocationPolicyWorstFit)

  var dc0 = helper.createSimpleDc(dc0_temp, cloudsim,hostList, new VmAllocationPolicyBestFit)
  /*if(dc_type=="simple"){
    dc0 = helper.createSimpleDc(dc0_temp, cloudsim,hostList, new VmAllocationPolicyBestFit)
  } else if(dc_type=="network"){
    dc0 = helper.createNetworkDc(dc0_temp, cloudsim,hostList, new VmAllocationPolicyBestFit)
  }*/



  val topology = "topology.brite"
  helper.configureNetwork(topology,cloudsim,dc0,broker)

  //val dc0 = new DatacenterSimple(cloudsim, hostList, new VmAllocationPolicyBestFit())


  //Creates VMs to run applications.
  /*val vm : Vm = createVms()
  val vmList = new util.ArrayList[Vm](1)
  vmList.add(vm)*/
  val vmList = new util.ArrayList[Vm](1)
  while (numVms>0){
    numVms -= 1
    val vmtemp : VmConfigHelper = new VmConfigHelper(simulation = "simulation0",model = "GeneralSimulations")
    val vm = helper.createVms(vmtemp)
    vmList.add(vm)
  }




  //Creating cloudlets variable and list and calling createCloudlets()
  /*val length = conf.getInt("simulation0.cloudLet0.length")
  val pesNum = conf.getInt("simulation0.cloudLet0.pesNumber")
  val clouldlet0: Cloudlet = createCloudlets(length,pesNum)
  val cloudlets = new util.ArrayList[Cloudlet](1)
  cloudlets.add(clouldlet0)*/
  val cloudlets = new util.ArrayList[Cloudlet](1)
  while (numcl>0){
    numcl -= 1
    val cltemp : CloudletConfigHelper = new CloudletConfigHelper(simulation = "simulation0",model = "GeneralSimulations")
    val cloudlet = helper.createCloudLets(cltemp)
    cloudlets.add(cloudlet)
  }
  //method which returns host creation
 /* def createHost(): Host ={
    //Uses ResourceProvisionerSimple by default for RAM and BW provisioning
    //Uses VmSchedulerSpaceShared by default for VM scheduling
    val host0 = new HostSimple(conf.getInt("simulation0.host0.ram"), conf.getInt("simulation0.host0.bw"), conf.getInt("simulation0.host0.storage"), hostPes)
    host0
  }*/

  //method which returns vm creation
  /*def createVms(): Vm ={
    //Uses a CloudletSchedulerTimeShared by default to schedule Cloudlets
    val vm0 = new VmSimple(conf.getInt("simulation0.vm0.mips"), conf.getInt("simulation0.vm0.pesNumber"))
    vm0.setRam(conf.getInt("simulation0.vm0.ram")).setBw(conf.getInt("simulation0.vm0.bw")).setSize(conf.getInt("simulation0.vm0.size"))
    vm0
  }*/

  //method which returns cloudlet creation
  /*def createCloudlets(len:Int,pes:Int): Cloudlet ={
    //Creates Cloudlets that represent applications to be run inside a VM.
    //UtilizationModel defining the Cloudlets use only 50% of any resource all the time
    val utilizationModel = new UtilizationModelDynamic(0.5)
    val cloudlet0 = new CloudletSimple(len,pes, utilizationModel)
    cloudlet0
  }*/

  //submitting vmlist and cloudletlist to broker.
  broker.submitVmList(vmList)
  broker.submitCloudletList(cloudlets)

  //starting cloudsim
  cloudsim.start

  //printing status for the simluation.
  new CloudletsTableBuilder(broker.getCloudletFinishedList).build()
}

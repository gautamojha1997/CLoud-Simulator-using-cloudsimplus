package utils


import java.util

import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyAbstract, VmAllocationPolicyBestFit}
import org.cloudbus.cloudsim.brokers.{DatacenterBroker, DatacenterBrokerSimple}
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.network.NetworkDatacenter
import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}

import scala.jdk.CollectionConverters
import collection.JavaConverters._
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.network.topologies.NetworkTopology
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.vm.{VmSchedulerAbstract, VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModel, UtilizationModelDynamic, UtilizationModelFull, UtilizationModelStochastic}
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}

import scala.collection.mutable.ListBuffer
import org.cloudbus.cloudsim.network.topologies.BriteNetworkTopology
import org.cloudbus.cloudsim.network.topologies.NetworkTopology
import org.cloudbus.cloudsim.schedulers.cloudlet.{CloudletSchedulerSpaceShared, CloudletSchedulerTimeShared}

/*The class is used as a helper class for the main simulations which implements
required to run the simulations and which is instantiated in the simulation class.*/

class DataCenterHelper {

  //This method returns broker for the simulations.
  def createBroker(cloudsim:CloudSim): DatacenterBroker = {
    new DatacenterBrokerSimple(cloudsim)
  }

  //This method returns simple DataCenter for the simulations.
  def createSimpleDc(dc:DataCenterConfigHelper,cloudSim: CloudSim, hostList:util.List[Host], policy: VmAllocationPolicyAbstract): Datacenter ={
    val dc0 = new DatacenterSimple(cloudSim,hostList,policy)
    dc0.getCharacteristics.setCostPerSecond(dc.cost).setCostPerMem(dc.costPerMemory).setCostPerBw(dc.costPerBw).setCostPerStorage(dc.costPerStorage).setOs(dc.os)
    dc0
  }

  //This method returns Network DataCenter for the simulations.
  def createNetworkDc(dc:DataCenterConfigHelper,cloudSim: CloudSim, hostList:util.List[Host], policy: VmAllocationPolicyAbstract): Datacenter ={
    val dc1 = new NetworkDatacenter(cloudSim,hostList,policy)
    dc1.getCharacteristics.setCostPerSecond(dc.cost).setCostPerMem(dc.costPerMemory).setCostPerBw(dc.costPerBw).setCostPerStorage(dc.costPerStorage).setOs(dc.os)
    dc1
  }

  //This methods sets network topology for the simulations.
  def configureNetwork(topology:String,cloudSim: CloudSim,dc0: Datacenter,broker: DatacenterBroker): Unit ={
    val networkTopology = BriteNetworkTopology.getInstance(topology)
    cloudSim.setNetworkTopology(networkTopology)
    //maps CloudSim entities to BRITE entities//maps CloudSim entities to BRITE entities

    //Datacenter will correspond to BRITE node 0
    var briteNode = 0
    networkTopology.mapNode(dc0.getId, briteNode)
    //Broker will correspond to BRITE node 3//Broker will correspond to BRITE node 3

    briteNode = 3
    networkTopology.mapNode(broker.getId, briteNode)
  }

  //This method returns host for the simulations.
  def createHost(host:HostConfigHelper, policy : VmSchedulerAbstract): Host = {
    //Creating hostPes
    val hostPes: List[Pe] = List.tabulate(host.cores)(i => new PeSimple(host.mips))
    val host0 = new HostSimple(host.ram,host.bw,host.storage,hostPes.asJava).setVmScheduler(policy)
    host0
  }

  //This method returns Vm for the simulations.
  def createVms(vm:VmConfigHelper): Vm ={
    val vm0 = new VmSimple(vm.mips,vm.pesNumber)
    vm0.setRam(vm.ram).setBw(vm.bw).setSize(vm.size)
    vm0
  }

  //This method returns IAAS Vm for the IAAS simulation.
  def createIaasVms(mips: Long, ram: Long, bw : Long, size : Long, pesNumber: Long): Vm = {
    val vm0 = new VmSimple(mips,pesNumber)
    vm0.setRam(ram).setBw(bw).setSize(size)
    vm0
  }

  //This method returns IAAS DataCenter for the IAAS simulation.
  def createIaasDc(dc:DataCenterConfigHelper,cloudSim: CloudSim, hostList:util.List[Host], policy: VmAllocationPolicyAbstract, os: String): Datacenter = {
    val dc0 = new DatacenterSimple(cloudSim,hostList,policy)
    dc0.getCharacteristics.setCostPerSecond(dc.cost).setCostPerMem(dc.costPerMemory).setCostPerBw(dc.costPerBw).setCostPerStorage(dc.costPerStorage).setOs(os)
    dc0
  }

  //This method returns cloudlet for simulations.
  def createCloudLets(cl:CloudletConfigHelper): Cloudlet ={

    var utilization = cl.utilization
    var utilizationModelDynamic: UtilizationModelDynamic = new UtilizationModelDynamic(0.5)
    var utilizationModelFull: UtilizationModelFull  = new UtilizationModelFull()

    if (utilization == "dynamic"){
      val cloudlet0 = new CloudletSimple(cl.length,cl.pesNumber, utilizationModelDynamic)
      cloudlet0
    }
    else {
      val cloudlet0 = new CloudletSimple(cl.length,cl.pesNumber, utilizationModelFull)
      cloudlet0
    }

  }

  //This method returns overall cost for simulations.
  def overallCost(cloudlet : Cloudlet): Double ={
    var cost: Double = 0.0

    if(cloudlet.isFinished){
      cost +=  cloudlet.getCostPerSec() * cloudlet.getActualCpuTime() * cloudlet.getCostPerBw()
    }

    cost
  }
}

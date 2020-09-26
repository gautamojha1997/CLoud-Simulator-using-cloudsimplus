package utils


import java.util

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
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModelDynamic, UtilizationModelFull, UtilizationModelStochastic}
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}

import scala.collection.mutable.ListBuffer
import org.cloudbus.cloudsim.network.topologies.BriteNetworkTopology
import org.cloudbus.cloudsim.network.topologies.NetworkTopology
import org.cloudbus.cloudsim.schedulers.cloudlet.{CloudletSchedulerSpaceShared, CloudletSchedulerTimeShared}

class DataCenterHelper {


  def createBroker(cloudsim:CloudSim): DatacenterBroker = {
    new DatacenterBrokerSimple(cloudsim)
  }

  def createSimpleDc(dc:DataCenterConfigHelper,cloudSim: CloudSim, hostList:util.List[Host], policy: VmAllocationPolicyAbstract): Datacenter ={
    val dc0 = new DatacenterSimple(cloudSim,hostList,policy)
    dc0.getCharacteristics.setCostPerSecond(dc.cost).setCostPerMem(dc.costPerMemory).setCostPerBw(dc.costPerBw).setCostPerStorage(dc.costPerStorage).setOs(dc.os)
    dc0
  }

  def createNetworkDc(dc:DataCenterConfigHelper,cloudSim: CloudSim, hostList:util.List[Host], policy: VmAllocationPolicyAbstract): Datacenter ={
    val dc1 = new NetworkDatacenter(cloudSim,hostList,policy)
    dc1.getCharacteristics.setCostPerSecond(dc.cost).setCostPerMem(dc.costPerMemory).setCostPerBw(dc.costPerBw).setCostPerStorage(dc.costPerStorage).setOs(dc.os)
    dc1
  }

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

  def createHost(host:HostConfigHelper, policy : VmSchedulerAbstract): Host = {
    //Creating hostPes
    val hostPes: List[Pe] = List.tabulate(host.cores)(i => new PeSimple(host.mips))
    val host0 = new HostSimple(host.ram,host.bw,host.storage,hostPes.asJava).setVmScheduler(policy)
    host0
  }

  def createVms(vm:VmConfigHelper): Vm ={
    val vm0 = new VmSimple(vm.mips,vm.pesNumber)
    vm0.setRam(vm.ram).setBw(vm.bw).setSize(vm.size)
    vm0
  }


  def createIaasVms(mips: Long, ram: Long, bw : Long, size : Long, pesNumber: Long): Vm = {
    val vm0 = new VmSimple(mips,pesNumber)
    vm0.setRam(ram).setBw(bw).setSize(size)
    vm0
  }

  def createIaasDc(dc:DataCenterConfigHelper,cloudSim: CloudSim, hostList:util.List[Host], policy: VmAllocationPolicyAbstract, os: String): Datacenter = {
    val dc0 = new DatacenterSimple(cloudSim,hostList,policy)
    dc0.getCharacteristics.setCostPerSecond(dc.cost).setCostPerMem(dc.costPerMemory).setCostPerBw(dc.costPerBw).setCostPerStorage(dc.costPerStorage).setOs(os)
    dc0
  }


  //val SEED = 1234567
  def createCloudLets(cl:CloudletConfigHelper): Cloudlet ={
    val utilizationModel = new UtilizationModelDynamic(0.5)
    //val utilizationModel = new UtilizationModelStochastic(SEED)
    val cloudlet0 = new CloudletSimple(cl.length,cl.pesNumber, utilizationModel)
    cloudlet0
  }

}

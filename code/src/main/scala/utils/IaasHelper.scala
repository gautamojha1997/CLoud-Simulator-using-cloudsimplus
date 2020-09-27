package utils

import MixedSimulations.MixedSim.{broker, cloudsim, conf, helper}
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyBestFit
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.hosts.Host
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared
import org.cloudbus.cloudsim.vms.Vm
import collection.JavaConverters._

class IaasHelper {

  def iaas(): Unit ={
    val input_helper : IaasInput = new IaasInput

    val iaasNumVms = input_helper.iaasNumVms
    val iaasNumHosts = conf.getInt("mixed.host.numberofhost")
    val iaasNumcl = conf.getInt("mixed.cloudLet.numberofCL")


    val os = input_helper.os
    //Creating Host
    val hosttemp_iaas: HostConfigHelper = new HostConfigHelper(simulation = "mixed", model = "MixedSimulations")
    val hostList_iaas: List[Host] = List.tabulate(iaasNumHosts)(i => helper.createHost(hosttemp_iaas, new VmSchedulerTimeShared))

    //Creates a Datacenter with a list of Hosts.
    //Uses a VmAllocationPolicySimple by default to allocate VMs
    val dc0_temp: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "iaas", model = "MixedSimulations")
    val dc0 = helper.createIaasDc(dc0_temp, cloudsim, hostList_iaas.asJava, new VmAllocationPolicyBestFit, os)

    //Creates VMs to run applications.
    val vmips = input_helper.vmips

    val vsize = input_helper.vsize

    val vram = input_helper.vram

    val vbw = input_helper.vbw

    val vcores = input_helper.vcores

    val vmList_iaas: List[Vm] = List.tabulate(iaasNumVms)(i => helper.createIaasVms(vmips,vram, vbw, vsize, vcores))

    //Creating cloudlets variable and list and calling createCloudlets()
    val cltemp: CloudletConfigHelper = new CloudletConfigHelper(simulation = "mixed", model = "MixedSimulations")
    val cloudlets_iaas: List[Cloudlet] = List.tabulate(iaasNumcl)(i => helper.createCloudLets(cltemp))

    cloudlets_iaas.map(i => i.assignToDatacenter(dc0))

    //submitting vmlist and cloudletlist to broker.
    broker.submitVmList(vmList_iaas.asJava)
    broker.submitCloudletList(cloudlets_iaas.asJava)
  }
}

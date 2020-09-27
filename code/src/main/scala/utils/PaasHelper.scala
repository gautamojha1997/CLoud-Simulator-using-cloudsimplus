package utils

import MixedSimulations.MixedSim.{broker, cloudsim, conf, helper}
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyBestFit
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.hosts.Host
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared
import org.cloudbus.cloudsim.vms.Vm
import collection.JavaConverters._

class PaasHelper {

  def paas(): Unit ={
    val input_helper : PaasInput = new PaasInput

    var plang = input_helper.plang

    var framework = input_helper.framework

    var db = input_helper.db

    val paasNumVms = conf.getInt("mixed.vm.numberofvm")
    val paasNumHosts = conf.getInt("mixed.host.numberofhost")
    val paasNumcl = conf.getInt("mixed.cloudLet.numberofCL")

    //Creating Host
    val paasHosttemp: HostConfigHelper = new HostConfigHelper(simulation = "mixed", model = "MixedSimulations")
    val paasHostList: List[Host] = List.tabulate(paasNumHosts)(i => helper.createHost(paasHosttemp, new VmSchedulerTimeShared))

    //Creates a Datacenter with a list of Hosts.
    //Uses a VmAllocationPolicySimple by default to allocate VMs
    val dc0_temp_paas: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "paas", model = "MixedSimulations")
    val dc1 = helper.createSimpleDc(dc0_temp_paas, cloudsim, paasHostList.asJava, new VmAllocationPolicyBestFit)

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
  }
}

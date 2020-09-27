package utils

import MixedSimulations.MixedSim.{broker, cloudsim, conf, helper}
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyBestFit
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.hosts.Host
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared
import org.cloudbus.cloudsim.vms.Vm
import collection.JavaConverters._

class SaasHelper {

  def saas(): Unit ={

    val numVms_saas = conf.getInt("mixed.vm.numberofvm")
    val numHosts_saas = conf.getInt("mixed.host.numberofhost")

    val numcl_saas = conf.getInt("mixed.cloudLet.numberofCL")


    //Creating Host
    val hosttemp_saas: HostConfigHelper = new HostConfigHelper(simulation = "mixed", model = "MixedSimulations")
    val hostList_saas: List[Host] = List.tabulate(numHosts_saas)(i => helper.createHost(hosttemp_saas, new VmSchedulerTimeShared))

    //Creates a Datacenter with a list of Hosts.
    //Uses a VmAllocationPolicySimple by default to allocate VMs
    val dc0_temp_saas: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "saas", model = "MixedSimulations")
    val dc2 = helper.createSimpleDc(dc0_temp_saas, cloudsim, hostList_saas.asJava, new VmAllocationPolicyBestFit)



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
  }
}

package utils

import MixedSimulations.MixedSim.{broker, cloudsim, conf, helper}
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyBestFit
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.hosts.Host
import org.cloudbus.cloudsim.schedulers.vm.{VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.vms.Vm
import org.slf4j.{Logger, LoggerFactory}

import collection.JavaConverters._
/*This Class is a helper class which is used by MixedSim class to run SAAS simulation.*/
class SaasHelper {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  def saas(): List[Cloudlet] ={


    logger.info("Building SAAS Simulation")

    //Getting number of vm, host and cloudlets from config files
    val numVms_saas = conf.getInt("mixed.vm.numberofvm")
    val numHosts_saas = conf.getInt("mixed.host.numberofhost")

    val numcl_saas = conf.getInt("mixed.cloudLet.numberofCL")


    //Creating Host
    logger.info("Creating host")
    val hosttemp_saas: HostConfigHelper = new HostConfigHelper(simulation = "mixed", model = "MixedSimulations")
    val hostList_saas: List[Host] = List.tabulate(numHosts_saas)(i => helper.createHost(hosttemp_saas, new VmSchedulerSpaceShared()))

    //Creates a Datacenter with a list of Hosts.
    //Uses a VmAllocationPolicySimple by default to allocate VMs
    logger.info("Creating Datacenter")
    val dc0_temp_saas: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "saas", model = "MixedSimulations")
    val dc2 = helper.createSimpleDc(dc0_temp_saas, cloudsim, hostList_saas.asJava, new VmAllocationPolicyBestFit)


    logger.info("Creating Vm and Cloulets")
    //Creates VMs to run applications.
    val vmtemp_saas: VmConfigHelper = new VmConfigHelper(simulation = "mixed", model = "MixedSimulations")
    val vmList_saas: List[Vm] = List.tabulate(numVms_saas)(i => helper.createVms(vmtemp_saas))

    //Creating cloudlets variable and list and calling createCloudlets()
    val cltemp_saas: CloudletConfigHelper = new CloudletConfigHelper(simulation = "mixed", model = "MixedSimulations")
    val cloudlets_saas: List[Cloudlet] = List.tabulate(numcl_saas)(i => helper.createCloudLets(cltemp_saas))

    //Assigning the cloudlet to saas Datacenter
    cloudlets_saas.map(i => i.assignToDatacenter(dc2))

    //submitting vmlist and cloudletlist to broker.
    logger.info("Submitting List of Vms and Cloudlets to broker.")
    broker.submitVmList(vmList_saas.asJava)
    broker.submitCloudletList(cloudlets_saas.asJava)


    logger.info("SAAS Simulation built")
    cloudlets_saas
  }
}

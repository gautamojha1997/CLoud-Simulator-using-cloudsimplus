import GeneralSimulations.Simulation1.{cloudsim, dc0_temp, helper, hostList, hosttemp, numHosts}
import MixedSimulations.MixedSim.{helper, iaasNumVms, vbw, vcores, vmips, vram, vsize}
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyBestFit
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared
import org.cloudbus.cloudsim.vms.Vm
import org.scalatest.FunSuite
import org.slf4j.{Logger, LoggerFactory}
import utils.{CloudletConfigHelper, DataCenterConfigHelper, DataCenterHelper, HostConfigHelper, VmConfigHelper}

import collection.JavaConverters._

class DataCenterHelperTest extends FunSuite{

  val cloudsim = new CloudSim
  val helper: DataCenterHelper = new DataCenterHelper

  val broker = helper.createBroker(cloudsim)

  /*Testing whether broker object is not null and the helper.createBroker returns valid broker*/
  test("helper.createBrokerReturnsValidBroker"){
    val b = new DatacenterBrokerSimple(cloudsim)
    assert(b!=null)
  }

  val hosttemp: HostConfigHelper = new HostConfigHelper(simulation = "simulation1", model = "GeneralSimulations")
  val hostList: List[Host] = List.tabulate(4)(i => helper.createHost(hosttemp, new VmSchedulerTimeShared))


  test("helper.createHostReturnsValidHost"){


    assert(hosttemp!=null)

    val hostPes: List[Pe] = List.tabulate(3)(i => new PeSimple(1000))
    val host0 = new HostSimple(25,1000,20000,hostPes.asJava)
    assert(host0!=null)
    assert(hostList.size === 4)


  }


  test("helper.createVmsReturnsValidVmList"){
    val vmtemp: VmConfigHelper = new VmConfigHelper("simulation1", "GeneralSimulations")
    assert(vmtemp!=null)
    val vm0 : Vm = helper.createVms(vmtemp)
    assert(vm0!=null)
    val vmList: List[Vm] = List.tabulate(3)(i => helper.createVms(vmtemp))
    assert(vmList.size === 3)
  }


  test("helper.createSimpleDcReturnsValidDc"){
    val dc0_temp: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "simulation1", model = "GeneralSimulations")
    assert(dc0_temp!=null)
    val dc0 : Datacenter = helper.createSimpleDc(dc0_temp, cloudsim, hostList.asJava, new VmAllocationPolicyBestFit)
    assert(dc0!=null)
  }

  test("helper.createNetworkDcReturnsValidDc"){
    val dc1_temp: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "simulation1", model = "GeneralSimulations")
    assert(dc1_temp!=null)
    val dc1 : Datacenter = helper.createNetworkDc(dc1_temp, cloudsim, hostList.asJava, new VmAllocationPolicyBestFit)
    assert(dc1!=null)
  }

  test("helper.createIaasVmsReturnsValidVmForIaas"){
    val vm0 : Vm = helper.createIaasVms(1000,1024,500,10000,2)
    assert(vm0!=null)

    val vmList_iaas: List[Vm] = List.tabulate(5)(i => helper.createIaasVms(1000,1024,500,10000,2))
    assert(vmList_iaas.size === 5)
  }

  test("helper.createIaasDcReturnsValidDcForIaas"){
    val dc1_temp: DataCenterConfigHelper = new DataCenterConfigHelper(simulation = "iaas", model = "MixedSimulations")
    assert(dc1_temp!=null)
    val dc1 : Datacenter = helper.createIaasDc(dc1_temp, cloudsim, hostList.asJava, new VmAllocationPolicyBestFit, "Linux")
    assert(dc1!=null)
  }

  test("helper.createCloudLets"){
    val cltemp: CloudletConfigHelper = new CloudletConfigHelper(simulation = "simulation1", model = "GeneralSimulations")
    assert(cltemp!=null)

    val cloudlet0 : Cloudlet = helper.createCloudLets(cltemp)
    assert(cloudlet0!=null)

    val cloudlets: List[Cloudlet] = List.tabulate(6)(i => helper.createCloudLets(cltemp))
    assert(cloudlets.size === 6)
  }


}

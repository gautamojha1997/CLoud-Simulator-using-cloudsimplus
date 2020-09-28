**CS 441 : HW1 Description: Create cloud simulators for evaluating executions of applications in cloud datacenters with different characteristics and deployment models.**

The Project aims to make simulations of cloud Datacenters, and it's components. The simulation is done using Cloudsim Plus framework and Scala. 
The implementation consists of general simulations and mixed simulations for services like IAAS, PAAS, and IAAS. 
The simulations depict various Vm Allocation Policy, configuration and logger usage.  

# Instructions to run the simulations

Clone the project

```git clone https://ojhagautam97@bitbucket.org/cs441-fall2020/gautamkumar_ojha_hw1.git ```


Now the project can be run using intellij or cmd.
## For cmd : 
1. Navigate to cd gautamkumar_ojha_hw1\code .
2. Run the simulations with the command : sbt clean compile run. 
3. Run the test cases using the command : sbt clean compile test. 


## For Intellij: 
1. Open the cloned project in intellij.
2. Directly run all the simulations to see each simulation results.


# Code Structure
1. The project  consists of three folders viz. GeneralSimulations, MixedSimulations and Utils.
2. GeneralSimulations consists of 3 Simulations which depicts the usage of cloudsim plus framework based on different VmAllocationPolicy, configurations and VmSchedulingPolicy.
3. MixedSimulations consists of a simulation class consisting of a broker and 3 datacenters for iaas, saas and paas.
4. Utils consists of files which are helper classes for the simulations.

# Configuration Files
There are two config files for general and mixed simulations.

* GeneralSimulations.conf consists of following values for each simulation. 

    * (Example of simulation0)
    
```
simulation0 : {
 
   host : {
     numberofhost : 2
     ram : 2048
     storage : 1000000
     bw : 20000
     mips : 20000
     cores : 1
   }
   dataCenter : {
     cost : 3.5
     costPerMemory : 0.3
     costPerStorage : 0.0025
     costPerBw : 0.07
     os : "Linux"
   }
   vm:{
     numberofvm : 1
     mips : 1000
     size : 10000
     ram : 1024
     bw : 1000
     pesNumber : 1
   }
 
   cloudLet:{
     numberofCL: 3
     length : 400000
     pesNumber : 2
     utilization : full
   }
 }
```

MixedSimulations.conf consists all components values similar to general simulations but differentiating only in datacenters based on services.

```
iaas : {
      dataCenter : {
        cost : 5.0
        costPerMemory : 0.05
        costPerStorage : 0.006
        costPerBw : 0.06
        os : "Linux"
    }
  }
  
  paas : {
      dataCenter : {
        cost : 3.0
        costPerMemory : 0.05
        costPerStorage : 0.003
        costPerBw : 0.05
        os : "Windows"
    }
  }
  
  saas : {
      dataCenter : {
        cost : 2.0
        costPerMemory : 0.02
        costPerStorage : 0.004
        costPerBw : 0.04
        os : "Mac"
    }
  }
```

# General Simulations Observation and Results

## Simulation 0 : 
It consists of **single datacenter with 2 hosts, 1 vm and 3 cloudlets**. The simulation uses **VmAllocationPolicyBestFit** for vm allocation and **VmSchedulerSpaceShared** for Vm Scheduling. 

### Result of Simulation 0:


```


                                         SIMULATION RESULTS

Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime
      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds
-----------------------------------------------------------------------------------------------------
       0|SUCCESS| 2|   0|        1| 0|        1|     400000|          2|       12|      2411|    2399
       1|SUCCESS| 2|   0|        1| 0|        1|     400000|          2|       12|      2411|    2399
       2|SUCCESS| 2|   0|        1| 0|        1|     400000|          2|       12|      2411|    2399
-----------------------------------------------------------------------------------------------------
INFO  Overall Cost for this simulation - 1764.07791

```


**The Overall Cost for simulation0 : 1764**


## Simulation 1 : 
It consists of **single datacenter with 3 hosts, 2 vm and 4 cloudlets**. The simulation uses **VmAllocationPolicyRoundRobin** for vm allocation and **VmSchedulerTimeShared** for Vm Scheduling. 

### Result of Simulation 1:


```

                                         SIMULATION RESULTS

Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime
      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds
-----------------------------------------------------------------------------------------------------
       0|SUCCESS| 2|   0|        2| 0|        1|     100000|          1|       12|       811|     799
       2|SUCCESS| 2|   0|        2| 0|        1|     100000|          1|       12|       811|     799
       1|SUCCESS| 2|   1|        2| 1|        1|     100000|          1|       12|       811|     799
       3|SUCCESS| 2|   1|        2| 1|        1|     100000|          1|       12|       811|     799
-----------------------------------------------------------------------------------------------------
INFO  Overall Cost for this simulation - 480.06600000000003
```


**The Overall Cost for simulation1 - 480**


## Simulation 2 : 
It consists of **single datacenter with 4 hosts, 3 vm and 4 cloudlets**. The simulation uses **VmAllocationPolicyWorstFit** for vm allocation and **VmSchedulerSpaceShared** for Vm Scheduling. 

### Result of Simulation 2:


```

                                         SIMULATION RESULTS

Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime
      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds
-----------------------------------------------------------------------------------------------------
       1|SUCCESS| 2|   1|        2| 1|        1|     100000|          1|       12|       411|     399
       2|SUCCESS| 2|   2|        2| 2|        1|     100000|          1|       12|       411|     399
       0|SUCCESS| 2|   0|        2| 0|        1|     100000|          1|       12|       811|     799
       3|SUCCESS| 2|   0|        2| 0|        1|     100000|          1|       12|       811|     799
-----------------------------------------------------------------------------------------------------
INFO  Overall Cost for this simulation - 120.01080000000002
```


**The Overall Cost for simulation2 - 120**

#### Observations for general simulations:
- It is seen in three simulations the overall cost, execution, finish and start time doesn't change drastically on the basis of Vm Allocation 
and Scheduling policy most of the times it remains same.
- But changing the values in the config files drastically changes the overall cost, execution, finish and start time.
- Also changing the utilization model type from dynamic to full changes the overall cost, execution, finish and start time. For simulation 1 the cost changes to 240.0 along with change in execution, finish and start time.
- The general simulations gives overall idea of cloudsim plus framework and how it can be used to simulate cloud data centers.


# Mixed Simulation Observation and Results

## MixedSimulation:
It consists of **single broker with 3 Datacenters for Iaas, Paas and Saas** wherein the cloudlets are assigned to the 3 Datacenters. 
### Result of MixedSim:


```
INFO  Starting Mixed Simulation
INFO  Instantiating Cloudsim
INFO  Creating Broker
INFO  Building IAAS Simulation
Enter Number of Vms for IAAS:
2
Enter the OS required:
Linux
Enter Vm MIPS for IAAS:
1000
Enter Vm Size for IAAS:
10000
Enter Vm Ram for IAAS:
512
Enter Vm BW for IAAS:
1000
Enter Vm Cores for IAAS:
2
INFO  Creating host
INFO  Creating Datacenter
INFO  Creating Vm and Cloulets
INFO  Submitting List of Vms and Cloudlets to broker.
INFO  IAAS Simulation built
INFO  Building PAAS Simulation
Choose the programming language for Paas- 1. Scala, 2. Java, 3. Python, 4. JavaScript : 
2
Choose the Framework according to programming language - 1. Akka Http, 2. Spring Boot, 3. DJango, 4. NodeJs :
2
Choose the DataBase Management Software - 1. MongoDB, 2. MYSQL, 3. PostgresSQL :
2
INFO  Creating host
INFO  Creating Datacenter
INFO  Creating Vm and Cloulets
INFO  Submitting List of Vms and Cloudlets to broker.
INFO  PAAS Simulation built
INFO  Building SAAS Simulation
INFO  Creating host
INFO  Creating Datacenter
INFO  Creating Vm and Cloulets
INFO  Submitting List of Vms and Cloudlets to broker.
INFO  SAAS Simulation built


                                         SIMULATION RESULTS

Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime
      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds
-----------------------------------------------------------------------------------------------------
       6|SUCCESS| 4|   0|        2| 6|        2|     300000|          8|        0|     14400|   14400
      15|SUCCESS| 4|   0|        2| 6|        2|     300000|          8|        0|     14400|   14400
      24|SUCCESS| 4|   0|        2| 6|        2|     300000|          8|        0|     14400|   14400
      33|SUCCESS| 4|   0|        2| 6|        2|     300000|          8|        0|     14400|   14400
      42|SUCCESS| 4|   0|        2| 6|        2|     300000|          8|        0|     14400|   14400
      51|SUCCESS| 4|   0|        2| 6|        2|     300000|          8|        0|     14400|   14400
       7|SUCCESS| 4|   1|        2| 7|        2|     300000|          8|        0|     14400|   14400
      16|SUCCESS| 4|   1|        2| 7|        2|     300000|          8|        0|     14400|   14400
      25|SUCCESS| 4|   1|        2| 7|        2|     300000|          8|        0|     14400|   14400
      34|SUCCESS| 4|   1|        2| 7|        2|     300000|          8|        0|     14400|   14400
      43|SUCCESS| 4|   1|        2| 7|        2|     300000|          8|        0|     14400|   14400
      52|SUCCESS| 4|   1|        2| 7|        2|     300000|          8|        0|     14400|   14400
       8|SUCCESS| 4|   2|        2| 8|        2|     300000|          8|        0|     14400|   14400
      17|SUCCESS| 4|   2|        2| 8|        2|     300000|          8|        0|     14400|   14400
      26|SUCCESS| 4|   2|        2| 8|        2|     300000|          8|        0|     14400|   14400
      35|SUCCESS| 4|   2|        2| 8|        2|     300000|          8|        0|     14400|   14400
      44|SUCCESS| 4|   2|        2| 8|        2|     300000|          8|        0|     14400|   14400
      53|SUCCESS| 4|   2|        2| 8|        2|     300000|          8|        0|     14400|   14400
       0|SUCCESS| 2|   0|        2| 0|        2|     300000|          8|        0|     16800|   16800
       9|SUCCESS| 2|   0|        2| 0|        2|     300000|          8|        0|     16800|   16800
      18|SUCCESS| 2|   0|        2| 0|        2|     300000|          8|        0|     16800|   16800
      27|SUCCESS| 2|   0|        2| 0|        2|     300000|          8|        0|     16800|   16800
      36|SUCCESS| 2|   0|        2| 0|        2|     300000|          8|        0|     16800|   16800
      45|SUCCESS| 2|   0|        2| 0|        2|     300000|          8|        0|     16800|   16800
      54|SUCCESS| 2|   0|        2| 0|        2|     300000|          8|        0|     16800|   16800
       1|SUCCESS| 2|   1|        2| 1|        2|     300000|          8|        0|     16800|   16800
      10|SUCCESS| 2|   1|        2| 1|        2|     300000|          8|        0|     16800|   16800
      19|SUCCESS| 2|   1|        2| 1|        2|     300000|          8|        0|     16800|   16800
      28|SUCCESS| 2|   1|        2| 1|        2|     300000|          8|        0|     16800|   16800
      37|SUCCESS| 2|   1|        2| 1|        2|     300000|          8|        0|     16800|   16800
      46|SUCCESS| 2|   1|        2| 1|        2|     300000|          8|        0|     16800|   16800
      55|SUCCESS| 2|   1|        2| 1|        2|     300000|          8|        0|     16800|   16800
       2|SUCCESS| 2|   2|        2| 2|        2|     300000|          8|        0|     16800|   16800
      11|SUCCESS| 2|   2|        2| 2|        2|     300000|          8|        0|     16800|   16800
      20|SUCCESS| 2|   2|        2| 2|        2|     300000|          8|        0|     16800|   16800
      29|SUCCESS| 2|   2|        2| 2|        2|     300000|          8|        0|     16800|   16800
      38|SUCCESS| 2|   2|        2| 2|        2|     300000|          8|        0|     16800|   16800
      47|SUCCESS| 2|   2|        2| 2|        2|     300000|          8|        0|     16800|   16800
      56|SUCCESS| 2|   2|        2| 2|        2|     300000|          8|        0|     16800|   16800
       3|SUCCESS| 3|   0|        2| 3|        2|     300000|          8|        0|     16800|   16800
      12|SUCCESS| 3|   0|        2| 3|        2|     300000|          8|        0|     16800|   16800
      21|SUCCESS| 3|   0|        2| 3|        2|     300000|          8|        0|     16800|   16800
      30|SUCCESS| 3|   0|        2| 3|        2|     300000|          8|        0|     16800|   16800
      39|SUCCESS| 3|   0|        2| 3|        2|     300000|          8|        0|     16800|   16800
      48|SUCCESS| 3|   0|        2| 3|        2|     300000|          8|        0|     16800|   16800
      57|SUCCESS| 3|   0|        2| 3|        2|     300000|          8|        0|     16800|   16800
       4|SUCCESS| 3|   1|        2| 4|        2|     300000|          8|        0|     16800|   16800
      13|SUCCESS| 3|   1|        2| 4|        2|     300000|          8|        0|     16800|   16800
      22|SUCCESS| 3|   1|        2| 4|        2|     300000|          8|        0|     16800|   16800
      31|SUCCESS| 3|   1|        2| 4|        2|     300000|          8|        0|     16800|   16800
      40|SUCCESS| 3|   1|        2| 4|        2|     300000|          8|        0|     16800|   16800
      49|SUCCESS| 3|   1|        2| 4|        2|     300000|          8|        0|     16800|   16800
      58|SUCCESS| 3|   1|        2| 4|        2|     300000|          8|        0|     16800|   16800
       5|SUCCESS| 3|   2|        2| 5|        2|     300000|          8|        0|     16800|   16800
      14|SUCCESS| 3|   2|        2| 5|        2|     300000|          8|        0|     16800|   16800
      23|SUCCESS| 3|   2|        2| 5|        2|     300000|          8|        0|     16800|   16800
      32|SUCCESS| 3|   2|        2| 5|        2|     300000|          8|        0|     16800|   16800
      41|SUCCESS| 3|   2|        2| 5|        2|     300000|          8|        0|     16800|   16800
      50|SUCCESS| 3|   2|        2| 5|        2|     300000|          8|        0|     16800|   16800
      59|SUCCESS| 3|   2|        2| 5|        2|     300000|          8|        0|     16800|   16800
-----------------------------------------------------------------------------------------------------
INFO  Overall Cost for this IAAS simulation - 62352.73344
INFO  Overall Cost for this  PAAS simulation - 59832.702240000006
INFO  Overall Cost for this SAAS simulation - 57312.67104000001
```

#### Observations for Mixed simulation:
- The Mixed Simulation consists of 3 services IAAS, PAAS and SAAS. The overall cost for IAAS being the most 62352, for PAAS 59832, and for SAAS 57312.
- For IAAS the client can specify their own vm specs which is done by eliciting the specs from the user.
- For PAAS the client can specify the working environment specs which is done by eliciting the specs from the user.
- The Vm Allocation and Scheduling policy used in this case is similar to ones used in General Simulations.


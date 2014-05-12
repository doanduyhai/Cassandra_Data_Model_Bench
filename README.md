## About

 This is a test webapp to bench and highlight the effect of modeling on Cassandra CPU usage during intensive read

 The assumptions are:

 1. Data to be read are static (written once, never updated/deleted)
 2. Data to be read are small (<10Mb total)
 3. 2 modeling styles: compact (all data as JSON) or flattened (one CQL3 column per object field)

### Installation

 First clone this project to your local machine with `git clone https://github.com/doanduyhai/Cassandra_Data_Model_Bench.git`

 Then create a test keyspace, create the column families & tables using `cassandra-cli` for **Thrift**
 and `cqlsh` for **CQL3** data

 The schema and data scripts are located in the **cassandra_scripts** folder

 Compile and build the webapp with  `mvn clean package`. The generated `stress-cql3.war` file is in
 the `./target` folder.

 Then deploy the webapp to a tomcat. Do not forget to configure the `on.properties` config file and start the webapp
 with the VM argument `-Don.config.file=<path_to_on.properties>`

### The data model

 The data and column families to be bench are referential data, with a tiny size (total < 10Mb).

 They can be modeled as compact JSON (all the entity is serialized to JSON client-side then saved in C*)
 or in flattened form using as many CQL3 columns as there are fields in the entity

 Example of compact schema:

 ```sql
   CREATE TABLE offers_json (
      partition_key text,
      id text,
      payload text,
      PRIMARY KEY (partition_key, id));
 ```

 The flattened version:

  ```sql
  CREATE TABLE offers_flat(
      pricing text,
      display_name text,
      duration int,
      features text,
      feature0_enabled boolean,
      code text,
      duration_type text,
      partition_key text,
      id text,
      PRIMARY KEY(partition_key, id));
  ```

### Web Application

  The webapp exposes a simple REST API to trigger calls to Cassandra.
  For more details on this API, hava a look into the `com.secretclient.stress.resource.Resource.java` file

### Benching

  The Cassandra cluster has 3 nodes with the following hardware specs:

  * CPU: Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz 6 cores
  * Memory: 32 Gb RAM
  * Disk: 7200 rpm RAID 10. 1Tb of capacity

The bench has been done with **[Gatling]** in **Scala** for its high performance features.
 All **Gatling** scenarios can be found in the folder `gatling_scenarios`.

We ran all scenarios:

 * during **1 hour**
 * with **1000 users**
 * ramp of 10 seconds
 * the webapp is deployed on 2 Tomcats
 * load balancing done by **Gatling**
 * for each user
     * issue 4 REST calls with [2,10] milli seconds of pause between each call
     * loop on those 4 calls during **1 hour** with [100,200] milli secs of pause between each loop


<br/>

We read data using **Thrift** and **CQL3** to eliminate any bias related to either API.

 During the bench, we monitor CPU and disk usage by collecting samples every 10 secs with:

 ```bash
  vmstat -tn 10 > /tmp/vmcpustat.txt
  iostat -dtm 10 > /tmp/iostat.txt &
 ```
 At the end of the bench. All CPU stats files on 3 nodes are processed using the shell script
 `reformat.sh` in the `shell_scripts` folder

### Results

 The results are quite straightforward. First the I/O usage is very low since the whole tables
 can fit in memory. What is interesting is the CPU usage.

 The flattened data model consumes 4 times as many CPU cycles as the compact version, either using
 **Thrift** or **CQL3** API.

 The gatling HTML results show quite satisfactory response time (see folder `gatling_results`).
 The issue is clearly not on latency but on **high CPU usage**

 Deactivating data compression or enabling row cache does not change the results. **Indeed the data is so small that it
 stays in memory, we never hit disks. Consequently we can eliminate any issue related to I/O**

 The CPU data are formatted and graphs are generated. The final graphs can be found in the folder
 `graphs`. Raw data for graphs generations are in `raw_data`


### Analysis

 The data is modeled in both cases as wide rows. There are only 4 physical partitions and their distribution is (using `nodetool getendpoints`):

  * 3 partitions on node1
  * 1 partition on node2
  * 0 partition on node3

During the test, we use a RoundRobin load balancing strategy on client side (via Hector/Java driver).
 That explains the difference in term of CPU usage between node1, node2 and node3.
 Basically node1 has the highest CPU usage because it hosts the data, node3 the lowest because it only plays the role of coordinator.

 Interestingly, using **Thrift** API to request **CQL3** data grants the same result, with slighly a little bit less CPU usage (5% max). This
 confirms that the

 What we can see is that having flattened data model costs more CPU than having compact data.

 Right now the only **hints** we have to explain this CPU usage difference is:

 1. Slice queries used to fetch data cost more CPU than single read by column name, especially if C* has to fetch
 column_index_size_in_kb (64kb) of data each time

 2. The decoding of **`CompositeType`** costs more CPU for **CQL3** than for **Thrift**

 3. Since the Cell type for all **CQL3** table is forced to **`BytesType`** to support any type of data, so ser/deser may have a cost on CPU.



[Gatling]: https://github.com/excilys/gatling

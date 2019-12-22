# spark-ts-examples
### Fixed Scala examples for Spark 2.4 (at 2019-12-22)

Description
-----------

Examples showing how to use the `spark-ts` time series library for Apache Spark.

~~Minimum~~ Requirements
--------------------

* Apache Spark 2.4.4
* Java 1.8
* Maven 3

### Tested Setup
My local environment:
 - Apache Spark 2.4.4
 - Java 1.8.0_231
 - Apache Maven 3.6.2
 
Amazon EMR:
 - Amazon EMR 5.28.0
    * Spark 2.4.4 on Hadoop 2.8.5 YARN with Ganglia 3.7.2 and Zeppelin 0.8.2
 - Java 1.8.0_221 (?)
 - Apache Maven 3.6.3
With manual build via SSH.

Using this Repo
---------------

### Only Scala!
I absolutely couldn't get ``spark-ts`` running with Python. So only the scala examples where adjusted for Spark 2.


### Building

We use [Maven](https://maven.apache.org/) for building Java / Scala. 
To compilethe example jar run:

```bash
# navigate to the `jvm` directory and
cd jvm

# Use Maven to build .jar
mvn package
```


### Running

To submit one of the Java or Scala examples to a local Spark cluster, run the following command
from the `jvm` directory:

    cd jvm

    # Run Scala example Stocks
    spark-submit --class com.cloudera.tsexamples.Stocks target/spark-ts-examples-0.0.1-SNAPSHOT-jar-with-dependencies.jar

    # Run Scala example ARIMA
    spark-submit --class com.cloudera.tsexamples.SingleSeriesARIMA target/spark-ts-examples-0.0.1-SNAPSHOT-jar-with-dependencies.jar

You can substitute any of the Scala ~~or Java~~ example classes as the value for the `--class`
parameter.

Use with Amazon EMR:
It is not possible to load data for EMR Spark from "local" filesystem of EC2. So you have to store the samples in an S3 bucket.

You have to do this only once for all EMR Cluster you create!

Copy example data from folder ``spark-ts-examples/data`` **once** to S3 bucket ``Amazon S3/<S3 Bucket ID>/<path to your data>``.
Then adjust the filepath in ``Stocks`` and  ``SingleSeriesARIMA`` to your S3 data!

---------------

**I could not get Python running!**

~~To submit a Python example, run the following command from the `python` directory:~~

~~spark-submit --driver-class-path PATH/TO/sparkts-0.3.0-jar-with-dependencies.jar Stocks.py~~~

~~The `--driver-class-path` parameter value must point to the Spark-TS JAR file, which can be
downloaded from the spark-timeseries [Github repo](https://github.com/sryza/spark-timeseries).~~



### FAQ

Install Apache Maven 3.6.3 on Amazon EMR
Connect via SSH, then install ``mvn`` command via:
```bash
# Download
cd /opt
sudo wget https://www-eu.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
# Extract and link
sudo tar xzf apache-maven-3.6.3-bin.tar.gz
sudo ln -s apache-maven-3.6.3 maven
# Setup Environment Variables
cd ~ # Back to home
# Add env to maven.sh
echo "export M2_HOME=/opt/maven
export PATH=\${M2_HOME}/bin:\${PATH}" > maven.sh
sudo mv maven.sh /etc/profile.d/maven.sh
# Reload env variables:
source /etc/profile.d/maven.sh
```
See [ How To Install Apache Maven on CentOS/RHEL 8/7/6](https://tecadmin.net/install-apache-maven-on-centos/)

### Changelog (by S. Uhrmann)
 - 2019-12-22
     - Use ``spark-ts`` Maven artifact in version 0.4.1, that is compatible with Spark 2.
     - Use Scala 2.11 (last version supported by Spark 2.4)
     - Adjust ``SingleSeriesARIMA.scala`` to load file via ``SparkContext`` to also read example data from amazon S3 bucket

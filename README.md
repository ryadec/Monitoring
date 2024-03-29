### Spark Streaming Monitoring Tools

Add these files to your Spark Streaming project to receive health emails about your stream. 

It is currently configured to send out an <strong>HOURLY</strong> email with metrics about your stream's health (no matter what)
And it will separately send out any alerts for issues every 15 minutes <strong> i.e. Scheduling Delay, Batch Delay, Process Stopped, etc. </strong>

The following is a sample email generated from this project.

![Sample Email](https://github.com/ryadec/Monitoring/blob/master/SampleEmail.png)

It is currently configured to randomly pick a batch each hour and send you the status and some metrics around it.

You will also receiver alerts when jobs are delayed, fail, are killed, restarted, etc.

# To Include
This email client is dependent on the Apache Commons email project. Please add the following to your pom.xml or download and include with your spark job.

```xml
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-email</artifactId>
            <version>1.4</version>
            <type>jar</type>
        </dependency>
```
# Example Code

This is example setup code for Spark 1.6
This can be done in different ways in later version of spark 2.x and documentation should be checked for your specific version

```scala
    val SERVER_SOCKET_IP = "my.server.address"
    val SERVER_SOCKET_PORT =  50000 //<Enter PORT NAME>
    val DURATION = 10   //in seconds

    val sparkconf = new SparkConf().setAppName("My App Name")
    
    //Add Spark Monitor to your sparkConf before you create the spark context & streaming context
    sparkconf.set("spark.extraListeners",classOf[SparkMonitor].getName)
    
    @transient val sc = new SparkContext(sparkconf)
    @transient val ssc = new StreamingContext(sc, Durations.seconds(DURATION))
    
    class JobWrapper(sc:SparkContext) extends java.io.Serializable {  val job = Job.getInstance(sc.hadoopConfiguration) }

    val lines = ssc.socketTextStream(SERVER_SOCKET_IP, SERVER_SOCKET_PORT)
    
    //ADD StreamMonitor to your Spark Streaming context here
    ssc.addStreamingListener(new StreamMonitor(ssc))
```




# To Edit
Please edit the HTML to your liking--adding your company logo or any other links and materials that are useful

The full HTML is included to download and edit to your liking. 
Afterward please add everything before the body to the EmailUtil.scala file in the contentLeft field.
Please then add everything after the body to the contentRight field.

The body of the email will be dynamically generated from the monitoring methods and inserted into the email template.


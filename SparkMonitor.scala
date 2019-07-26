import java.util.Date
import org.apache.spark.scheduler._

/**
  * Created by Ryan DeCosmo on 6/30/17.
  */
class SparkMonitor extends SparkListener {
  //We can use this class to monitor cluster based events
  val email = EmailUtil.LastEmailTime
  val errorEmail = EmailUtil.LastErrorEmailTime

  override def onTaskGettingResult(taskGettingResult: SparkListenerTaskGettingResult): Unit = {
    if(taskGettingResult.taskInfo.gettingResultTime > 300000){
      val title = "<h1 class=\"h1\">Task Not Completing!</h1>"
      val details = "<strong>Details: </strong><br>"
      val body = title+details+"<br> <strong> Task Getting Result Time: </strong>"+ taskGettingResult.taskInfo.gettingResultTime+
        "<br> <strong> ID: </strong>"+ taskGettingResult.taskInfo.id+
        "<br> <strong> Status: </strong>"+ taskGettingResult.taskInfo.status
      if((new Date().getTime - EmailUtil.LastErrorEmailTime.getTime) > 900000l ) {
        EmailUtil.sendEmail(applicationStart.appName+" Task Not Completing!", body)
        EmailUtil.LastErrorEmailTime = new Date()
      }
    }
  }

  override def onUnpersistRDD(unpersistRDD: SparkListenerUnpersistRDD): Unit = {}

  override def onApplicationStart(applicationStart: SparkListenerApplicationStart): Unit = {
    val title = "<h1 class=\"h1\">Stream Started!</h1>"
    val details = "<strong>Details: </strong> A new instance of the Spark stream for <em> "+applicationStart.appName+" </em> has been submitted. Either there has been an update, or the app has been restarted by a user. <br>"
    val body = title+details+"<br> This email was sent because the app is running under a new App ID. <br> <strong> App ID: </strong>"+ applicationStart.appId.get +
      "<br> <strong> App Name: </strong>"+ applicationStart.appName +
      "<br> <strong> App Start Time: </strong>"+ new Date(applicationStart.time)
    EmailUtil.sendEmail(applicationStart.appName+" New Instance Running!",body) 
  }

  override def onApplicationEnd(applicationEnd: SparkListenerApplicationEnd): Unit = {}

  override def onExecutorMetricsUpdate(executorMetricsUpdate: SparkListenerExecutorMetricsUpdate): Unit = {}


}

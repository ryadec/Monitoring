
import java.util.Date

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.scheduler.{StreamingListenerBatchCompleted, StreamingListenerBatchStarted, StreamingListenerOutputOperationCompleted, StreamingListenerOutputOperationStarted, _}

/**
  * Created by Ryan DeCosmo on 6/30/17.
  */
class StreamMonitor(sc: StreamingContext) extends StreamingListener {

  val email = EmailUtil.LastEmailTime
  val errorEmail = EmailUtil.LastErrorEmailTime

  override def onReceiverStarted(receiverStarted: StreamingListenerReceiverStarted ): Unit = {}

  override def onReceiverError(error: StreamingListenerReceiverError):Unit = {
    val name = error.receiverInfo.name
    val streamId = error.receiverInfo.streamId
    val lastErrorMessage = error.receiverInfo.lastErrorMessage
    val lastError = error.receiverInfo.lastError
    val location = error.receiverInfo.location
    val active = error.receiverInfo.active
    val executorId = error.receiverInfo.executorId
    val lastErrorTime = new Date(error.receiverInfo.lastErrorTime)
    val title = "<h1 class=\"h1\">Receiver Error!</h1>"
    val details = "<strong>Details: </strong><br>"

    val body = title+details+
      "<br> <strong>Name: </strong>"+ name +
      "<br> <strong>Stream ID: </strong>"+streamId+
      "<br> <strong>Executor ID: </strong>"+executorId+
      "<br> <strong>Last Error: </strong>"+ lastError+
      "<br> <strong>Last Error Message: </strong>"+lastErrorMessage+
      "<br> <strong>Last Error Time: </strong>"+lastErrorTime+
      "<br> <strong>Location: </strong>"+location+
      "<br> <strong>Active: </strong>"+active
    if((new Date().getTime - EmailUtil.LastErrorEmailTime.getTime) > 900000l ) {
      EmailUtil.sendEmail("RECEIVER ERROR!", body)
      EmailUtil.LastErrorEmailTime = new Date()
    }
  }
  override def onReceiverStopped(stopped: StreamingListenerReceiverStopped):Unit = {
    val name = stopped.receiverInfo.name
    val streamId = stopped.receiverInfo.streamId
    val lastErrorMessage = stopped.receiverInfo.lastErrorMessage
    val lastError = stopped.receiverInfo.lastError
    val location = stopped.receiverInfo.location
    val active = stopped.receiverInfo.active
    val executorId = stopped.receiverInfo.executorId
    val lastErrorTime = new Date(stopped.receiverInfo.lastErrorTime)
    val title = "<h1 class=\"h1\">Receiver Stopped!</h1>"
    val details = "<strong>Details: </strong><br>"

    val body = title + details +
      "<br> <strong>Name: </strong>" + name +
      "<br> <strong>Stream ID: </strong>" + streamId +
      "<br> <strong>Executor ID: </strong>" + executorId +
      "<br> <strong>Last Error: </strong>" + lastError +
      "<br> <strong>Last Error Message: </strong>" + lastErrorMessage +
      "<br> <strong>Last Error Time: </strong>" + lastErrorTime +
      "<br> <strong>Location: </strong>" + location +
      "<br> <strong>Active: </strong>" + active
    if ((new Date().getTime - EmailUtil.LastErrorEmailTime.getTime) > 900000l) {
      EmailUtil.sendEmail("RECEIVER STOPPED! ", body)
    }
  }
  override def onBatchSubmitted(batchSubmitted: StreamingListenerBatchSubmitted ): Unit = {
    /*    val schedulingDelay = batchSubmitted.batchInfo.schedulingDelay
        val processingDelay = batchSubmitted.batchInfo.processingDelay
        val totalDelay = batchSubmitted.batchInfo.totalDelay
        val numRecords = batchSubmitted.batchInfo.numRecords
        var ids = ""
        batchSubmitted.batchInfo.outputOperationInfos.keysIterator.foreach(num => {
          ids = ids + batchSubmitted.batchInfo.outputOperationInfos.get(num).get.id + "<br>"
        })
        val title = "<h1 class=\"h1\">Batch Submitted!</h1>"
        val details = "<strong>Details: </strong><br>"
        val body = title+details+
                  "<br> <strong>Scheduling Delay: </strong>"+ schedulingDelay.get +
                  "<br> <strong>Processing Delay: </strong>"+ processingDelay.get +
                  "<br> <strong>Num Records: </strong>"+numRecords+
                  "<br> <strong>IDs: </strong>"+ids
        sendEmail("Alert!",body) */
  }

  override def onBatchStarted(batchStarted: StreamingListenerBatchStarted): Unit = {
    //checkin batch delay and scheduling delay here
    if (batchStarted.batchInfo.totalDelay.getOrElse(0l) > 180000l || batchStarted.batchInfo.schedulingDelay.getOrElse(0l) > 180000l) {
      val submissionTime = new Date(batchStarted.batchInfo.submissionTime)
      val schedulingDelay = batchStarted.batchInfo.schedulingDelay.get
      val processingDelay = batchStarted.batchInfo.processingDelay.get
      val processingStartTime = new Date(batchStarted.batchInfo.processingStartTime.get)
      val processingEndTime = new Date(batchStarted.batchInfo.processingEndTime.get)
      val totalDelay = batchStarted.batchInfo.totalDelay.get

      val title = "<h1 class=\"h1\">Batch Started!</h1>"
      val details = "<strong>Details: </strong><br>"
      val body = title + details +
        "<br> <strong>Submission Time: </strong>" + submissionTime +
        "<br> <strong>Scheduling Delay: </strong>" + schedulingDelay + " ms " +
        "<br> <strong>Processing Start Time: </strong>" + processingStartTime +
        "<br> <strong>Processing End Time: </strong>" + processingEndTime +
        "<br> <strong>Processing Delay: </strong>" + processingDelay + " ms " +
        "<br> <strong>Total Delay: </strong>" + totalDelay + " ms "
      if((new Date().getTime - EmailUtil.LastErrorEmailTime.getTime) > 900000l ) {
        EmailUtil.sendEmail("Alert! ", body)
        EmailUtil.LastErrorEmailTime = new Date()
      }
    }
  }
  
  //This is our HOURLY alert, if all is well, we send on completion of a healthy batch,
  //if not, error emails should have been going out
  override def onBatchCompleted(batchCompleted: StreamingListenerBatchCompleted) {
    val submissionTime  = new Date(batchCompleted.batchInfo.submissionTime)
    val schedulingDelay  = batchCompleted.batchInfo.schedulingDelay.get
    val processingDelay  = batchCompleted.batchInfo.processingDelay.get
    val processingStartTime  = new Date(batchCompleted.batchInfo.processingStartTime.get)
    val processingEndTime  = new Date(batchCompleted.batchInfo.processingEndTime.get)
    val numRecords  = batchCompleted.batchInfo.numRecords


    if((new Date().getTime - EmailUtil.LastEmailTime.getTime) > 3600000l ) {
      val title = "<h1 class=\"h1\">Latest Batch Completed:</h1>"
      val details = "<strong>Details: </strong><br>"
      val body = title+details+
        "<br> <strong>Submission Time: </strong>"+ submissionTime +
        "<br> <strong>Scheduling Delay: </strong>"+ schedulingDelay + " ms "+
        "<br> <strong>Processing Start Time: </strong>"+ processingStartTime+
        "<br> <strong>Processing End Time: </strong>"+ processingEndTime +
        "<br> <strong>Processing Delay: </strong>"+ processingDelay +" ms "+
        "<br> <strong>Num Records: </strong>"+ numRecords  //maybe check if numrecords is 0?
      EmailUtil.sendEmail("BedMaster Hourly Health ",body)
      EmailUtil.LastEmailTime = new Date()
    }
  }

  override def onOutputOperationStarted(outputOperationStarted: StreamingListenerOutputOperationStarted): Unit = {
    if(outputOperationStarted.outputOperationInfo.failureReason.isDefined)  {
      val failureReason = outputOperationStarted.outputOperationInfo.failureReason.get
      val description = outputOperationStarted.outputOperationInfo.description
      val name = outputOperationStarted.outputOperationInfo.name
      val id = outputOperationStarted.outputOperationInfo.id
      val batchTime =  new Date(outputOperationStarted.outputOperationInfo.batchTime.milliseconds)
      val duration = outputOperationStarted.outputOperationInfo.duration.get
      val title = "<h1 class=\"h1\">Output Operation Started!</h1>"
      val details = "<strong>Details: </strong><br>"
      val body = title+details+
        "<br> <strong>Description: </strong>"+ description+
        "<br> <strong>Name: </strong>"+name+
        "<br> <strong>ID: </strong>"+id+
        "<br> <strong>Batch Time: </strong>"+batchTime+
        "<br> <strong>Duration: </strong>"+duration+" ms "+
        "<br> <strong>Failure Reason: </strong>"+failureReason
      if((new Date().getTime - EmailUtil.LastErrorEmailTime.getTime) > 900000l ) {
        EmailUtil.sendEmail("Alert!",body)
        EmailUtil.LastErrorEmailTime = new Date()
      }
    }
  }
  override def onOutputOperationCompleted(outputOperationCompleted: StreamingListenerOutputOperationCompleted): Unit = {
    /*val description = outputOperationCompleted.outputOperationInfo.description
     val name = outputOperationCompleted.outputOperationInfo.name
     val id = outputOperationCompleted.outputOperationInfo.id
     val batchTime =  outputOperationCompleted.outputOperationInfo.batchTime
     val duration = outputOperationCompleted.outputOperationInfo.duration.get
     val title = "<h1 class=\"h1\">Output Operation Completed!</h1>"
     val details = "<strong>Details: </strong><br>"
     val body = title+details+
               "<br> <strong>Description: </strong>"+ description+
               "<br> <strong>Name: </strong>"+name+
               "<br> <strong>ID: </strong>"+id+
               "<br> <strong>Batch Time: </strong>"+batchTime+
               "<br> <strong>Duration: </strong>"+duration

     sendEmail("Alert!",body) */
  }


}
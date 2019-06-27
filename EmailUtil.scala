
import java.util.{Calendar, Date}
import org.apache.commons.mail.HtmlEmail

/**
  * Created by Ryan DeCosmo on 6/30/17.
  */
object EmailUtil {
  // Private LastEmailTime variable, renamed to _LastEmailTime
  private var _LastEmailTime = new Date()
  def LastEmailTime = _LastEmailTime // Getter
  def LastEmailTime_= (value:Date):Unit = _LastEmailTime = value // Setter

  //add another field for the errors
  private var _LastErrorEmailTime = new Date()
  def LastErrorEmailTime = _LastErrorEmailTime // Getter
  def LastErrorEmailTime_= (value:Date):Unit = _LastErrorEmailTime = value // Setter

  def sendEmail(subject: String, body: String): Unit = {

    val contentLeft = "<html> <head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /> <meta property=\"og:title\" content=\"Alert\" /> <title>Alert</title> <style type=\"text/css\"> #outlook a{padding:0;} body{width:100% !important;} .ReadMsgBody{width:100%;} .ExternalClass{width:100%;} body{-webkit-text-size-adjust:none;} body{margin:0; padding:0;} img{border:0; height:auto; line-height:100%; outline:none; text-decoration:none;} table td{border-collapse:collapse;} #backgroundTable{height:100% !important; margin:0; padding:0; width:100% !important;} body, #backgroundTable{background-color:#fcfdff; } #templateContainer{border: 1px solid #734547; } h1, .h1{color:#202020; display:block; font-family:Arial; font-size:34px; font-weight:bold; line-height:100%; margin-top:0; margin-right:0; margin-bottom:10px; margin-left:0; text-align:left; } h2, .h2{color:#202020; display:block; font-family:Arial; font-size:30px; font-weight:bold; line-height:100%; margin-top:0; margin-right:0; margin-bottom:10px; margin-left:0; text-align:left; } h3, .h3{color:#202020; display:block; font-family:Arial; font-size:26px; font-weight:bold; line-height:100%; margin-top:0; margin-right:0; margin-bottom:10px; margin-left:0; text-align:left; } h4, .h4{color:#202020; display:block; font-family:Arial; font-size:22px; font-weight:bold; line-height:100%; margin-top:0; margin-right:0; margin-bottom:10px; margin-left:0; text-align:left; } #templatePreheader{background-color:#fcfdff; } .preheaderContent div{color:#505050; font-family:Arial; font-size:10px; line-height:100%; text-align:left; } .preheaderContent div a:link, .preheaderContent div a:visited, .preheaderContent div a .yshortcuts {color:#336699; font-weight:normal; text-decoration:underline; } #templateHeader{background-color:#FFFFFF; border-bottom:0; } .headerContent{color:#202020; font-family:Arial; font-size:34px; font-weight:bold; line-height:100%; padding:0; text-align:center; vertical-align:middle; } .headerContent a:link, .headerContent a:visited, .headerContent a .yshortcuts {color:#336699; font-weight:normal; text-decoration:underline; } #headerImage{height:auto; max-width:600px !important; } #templateContainer, .bodyContent{background-color:#FFFFFF; } .bodyContent div{color:#505050; font-family:Arial; font-size:14px; line-height:150%; text-align:left; } .bodyContent div a:link, .bodyContent div a:visited, .bodyContent div a .yshortcuts {color:#336699; font-weight:normal; text-decoration:underline; } .bodyContent img{display:inline; height:auto; } #templateFooter{background-color:#f2efef; border-top:0; } .footerContent div{color:#707070; font-family:Arial; font-size:12px; line-height:125%; text-align:left; } .footerContent div a:link, .footerContent div a:visited, .footerContent div a .yshortcuts {color:#336699; font-weight:normal; text-decoration:underline; } .footerContent img{display:inline; } #social{background-color:#FAFAFA; border:0; } #social div{text-align:center; } #utility{background-color:#FFFFFF; border:0; } #utility div{text-align:center; } #monkeyRewards img{max-width:190px; } </style> </head> <body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\"> <center> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"backgroundTable\"> <tr> <td align=\"center\" valign=\"top\"> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"templatePreheader\"> <tr> <td valign=\"top\" class=\"preheaderContent\"> <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\" > <tr> <td valign=\"top\"> <div mc:edit=\"std_preheader_content\"> <img src=\"https://www.qbrobotics.com/wp-content/uploads/2018/03/sample-logo.png\" style=\"max-width:200px;\" align=\"middle\"> </div> </td> </tr> </table> </td> </tr> </table> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"templateContainer\"> <tr> <td align=\"center\" valign=\"top\"> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"templateHeader\"> <tr> <td class=\"headerContent\"> <img src=\"https://sudheerhadoopdev.files.wordpress.com/2016/07/logo_spark_hadoop.png?w=700\" style=\"max-width:450px;\" id=\"headerImage campaign-icon\" mc:label=\"header_image\" mc:edit=\"header_image\" mc:allowdesigner mc:allowtext /> </td> </tr> </table> </td> </tr> <tr> <td align=\"center\" valign=\"top\"> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"templateBody\"> <tr> <td valign=\"top\" class=\"bodyContent\"> <table border=\"0\" cellpadding=\"20\" cellspacing=\"0\" width=\"100%\"> <tr> <td valign=\"top\"> <div mc:edit=\"std_content00\">"
    val contentRight = "</div> </td> </tr> </table> </td> </tr> </table> </td> </tr> <tr> <td align=\"center\" valign=\"top\"> <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"600\" id=\"templateFooter\"> <tr> <td valign=\"top\" class=\"footerContent\"> <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\"> <tr> <td valign=\"top\" width=\"600\"> <div mc:edit=\"std_footer\"> <strong>For assistance contact:</strong> <br /> Person 1: <a href=\"mailto:EMAIL1@myOrg.org\">EMAIL1@myOrg.org</a>, Person 2: <a href=\"mailto:EMAIL2@myOrg.org\">EMAIL2@myOrg.org</a>, Person 3: <a href=\"mailto:EMAIL3@myOrg.org\">EMAIL3@myOrg.org</a> </div> </td> </tr> <tr> <td colspan=\"2\" valign=\"middle\" id=\"utility\"> <div mc:edit=\"std_utility\"> &nbsp;<a href=\"https://bug.tracker.org/login\">Bug Tracker</a> | <a href=\"https://outlook.test.org/\">Mail</a> | <a href=\"http://test.org:8088/cluster\">Cluster</a> | <a href=\"https://monitoring.org:8443/#/login\">Monitor</a>&nbsp; </div> </td> </tr> </table> <div>Copyright &copy; 2019 YOUR_ORG, All rights reserved.</div> </td> </tr> </table> </td> </tr> </table> <br /> </td> </tr> </table> </center> </body> </html>"

    val email = new HtmlEmail()
    email.setHostName("smtp.nyp.org")
    //Add who you are sending to
    email.addTo("<EMAIL>@test.org")

    //add your CC list
    email.addCc("<EMAIL>@test.org")
    email.addCc("<EMAIL>@test.org")

    //set who the email is from
    email.setFrom("<FROM>@test.org")
    
    email.setSubject(subject)
    email.setHtmlMsg(contentLeft+body+contentRight)
    email.setTextMsg("Your email client does not support HTML messages")
    email.send()
  }
}
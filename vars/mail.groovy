def sendEmail(String recipient, String buildStatus, String jobName, String buildNumber, String buildUrl) {
    def emoji = buildStatus == 'SUCCESS' ? '✅' : (buildStatus == 'FAILURE' ? '⚠️' : '⚠️')
    def subject = "${emoji} ${buildStatus}: ${jobName} #${buildNumber}"


    def body = ""
    def attachLog = false

    if (buildStatus == 'SUCCESS') {
        body = """
        <h2>Build Succeeded ✅</h2>
        <p>The build is success. You have done a good job! 🎉</p>
        <ul>
          <li><b>Job:</b> ${jobName}</li>
          <li><b>Build Number:</b> ${buildNumber}</li>
          <li><b>Build URL:</b> <a href="${buildUrl}">${buildUrl}</a></li>
        </ul>
        """
    } else {
        body = """
        <h2>Build Failed ⚠️</h2>
        <ul>
          <li><b>Job:</b> ${jobName}</li>
          <li><b>Build Number:</b> ${buildNumber}</li>
          <li><b>Build URL:</b> <a href="${buildUrl}">${buildUrl}</a></li>
          <li><b>Console Output:</b> <a href="${buildUrl}console">${buildUrl}console</a></li>
        </ul>
        """
        attachLog = true  
    }

    
    emailext(
        to: recipient,
        subject: subject,
        body: body,
        mimeType: 'text/html',
        attachLog: attachLog
    )
}

return this

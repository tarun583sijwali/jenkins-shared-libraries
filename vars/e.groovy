def call(String recipient,
         String buildStatus = currentBuild.currentResult,
         String jobName = env.JOB_NAME,
         String buildNumber = env.BUILD_NUMBER,
         String buildUrl = env.BUILD_URL) {

    if (!recipient) {
        error "Recipient email is required!"
    }

    def emoji = buildStatus == 'SUCCESS' ? '✅' : '⚠️'
    def subject = "${emoji} ${buildStatus}: ${jobName} #${buildNumber}"

    // Get only string data, avoid non-serializable objects
    def user = "Unknown"
    def cause = currentBuild.rawBuild.getCause(hudson.model.Cause$UserIdCause)
    if (cause != null) {
        user = cause.getUserName()
    } else {
        user = currentBuild.rawBuild.getCauses().collect { it.shortDescription.toString() }.join(', ')
    }

    // Email body
    def body = buildStatus == 'SUCCESS' ? """
        <h2>Build Succeeded ✅</h2>
        <p>The build is successful. Great job! 🎉</p>
        <ul>
          <li><b>Job:</b> ${jobName}</li>
          <li><b>Build Number:</b> ${buildNumber}</li>
          <li><b>Build URL:</b> <a href="${buildUrl}">${buildUrl}</a></li>
          <li><b>Triggered By:</b> ${user}</li>
        </ul>
    """ : """
        <h2>Build Failed ⚠️</h2>
        <ul>
          <li><b>Job:</b> ${jobName}</li>
          <li><b>Build Number:</b> ${buildNumber}</li>
          <li><b>Build URL:</b> <a href="${buildUrl}">${buildUrl}</a></li>
          <li><b>Console Output:</b> <a href="${buildUrl}console">${buildUrl}console</a></li>
          <li><b>Triggered By:</b> ${user}</li>
        </ul>
    """

    emailext(
        to: recipient,
        subject: subject,
        body: body,
        mimeType: 'text/html',
        attachLog: buildStatus != 'SUCCESS'
    )
}

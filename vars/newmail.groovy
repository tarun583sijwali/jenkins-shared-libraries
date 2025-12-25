def call(String recipient, String buildStatus, String jobName, String buildNumber, String buildUrl) {
    def emoji = buildStatus == 'SUCCESS' ? '✅' : '⚠️'
    def subject = "${emoji} ${buildStatus}: ${jobName} #${buildNumber}"

    // Determine who triggered the build
    def user = "Unknown"
    def cause = currentBuild.rawBuild.getCause(hudson.model.Cause$UserIdCause)
    if (cause != null) {
        user = cause.getUserName()
    } else {
        // If not a user, get all causes and join their descriptions
        user = currentBuild.rawBuild.getCauses().collect { it.shortDescription }.join(', ')
    }

    // Email body
    def body = buildStatus == 'SUCCESS' ? """
        <h2>Build Succeeded ✅</h2>
        <p>The build is successful. You have done a good job! 🎉</p>
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

    // Send email
    emailext(
        to: recipient,
        subject: subject,
        body: body,
        mimeType: 'text/html',
        attachLog: buildStatus != 'SUCCESS'
    )
}

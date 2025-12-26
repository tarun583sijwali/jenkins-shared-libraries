def call(String recipient,
         String buildStatus = currentBuild.currentResult,
         String jobName = env.JOB_NAME,
         String buildNumber = env.BUILD_NUMBER,
         String buildUrl = env.BUILD_URL) {

    if (!recipient) {
        error "Recipient email is required!"
    }

    // Use environment variable for user
    def user = env.BUILD_USER ?: "Unknown"

    // Email subject
    def subject = "${buildStatus}: ${jobName} #${buildNumber}"

    // Email body with blue for SUCCESS, red for FAILURE
    def body = buildStatus == 'SUCCESS' ? """
        <h2 style="border:2px solid blue; padding:5px;">SUCCESS</h2>
        <p>The build is successful. Great job! 🎉</p>
        <ul>
          <li><b>Job:</b> ${jobName}</li>
          <li><b>Build Number:</b> ${buildNumber}</li>
          <li><b>Build URL:</b> <a href="${buildUrl}">${buildUrl}</a></li>
          <li><b>Triggered By:</b> ${user}</li>
        </ul>
    """ : """
        <h2 style="border:2px solid red; padding:5px;">FAILURE</h2>
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

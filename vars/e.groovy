// Email body
def body = buildStatus == 'SUCCESS' ? """
    <h2>Build Succeeded:</h2>
    <div style="border: 2px solid blue; padding: 5px; display: inline-block; color: blue;">
        SUCCESS
    </div>
    <p>The build is successful. Great job! 🎉</p>
    <ul>
      <li><b>Job:</b> ${jobName}</li>
      <li><b>Build Number:</b> ${buildNumber}</li>
      <li><b>Build URL:</b> <a href="${buildUrl}">${buildUrl}</a></li>
      <li><b>Triggered By:</b> ${user}</li>
    </ul>
""" : """
    <h2>Build Failed:</h2>
    <div style="border: 2px solid red; padding: 5px; display: inline-block; color: red;">
        FAILURE
    </div>
    <ul>
      <li><b>Job:</b> ${jobName}</li>
      <li><b>Build Number:</b> ${buildNumber}</li>
      <li><b>Build URL:</b> <a href="${buildUrl}">${buildUrl}</a></li>
      <li><b>Console Output:</b> <a href="${buildUrl}console">${buildUrl}console</a></li>
      <li><b>Triggered By:</b> ${user}</li>
    </ul>
"""

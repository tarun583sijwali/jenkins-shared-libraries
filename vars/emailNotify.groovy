def call(Map config = [:]) {
    emailext (
        subject: "Build ${config.status}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
        body: "Check console output at ${env.BUILD_URL}",
        to: config.to
    )
}

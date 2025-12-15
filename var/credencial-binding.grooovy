def call(String credentialsId = 'docker-hub-credentials') {
    withCredentials([usernamePassword(
        credentialsId: credentialsId,
        usernameVariable: 'DOCKER_USER',
        passwordVariable: 'DOCKER_PASS'
    )]) {
        echo "Logging in to Docker Hub as ${DOCKER_USER}"
        
        sh """
            echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
        """
    }
}

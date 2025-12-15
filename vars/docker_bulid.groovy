def call(String dockerHubUser, String imageName, String imageTag = 'latest') {
  echo "Building Docker image: ${dockerHubUser}/${imageName}:${imageTag}"
  sh "docker build -t ${dockerHubUser}/${imageName}:${imageTag} ."
}

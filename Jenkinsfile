podTemplate(
  label: 'skaffold',
  containers: [
    containerTemplate(name: 'skaffold-insider', image: 'hhayakaw/skaffold-insider:v1.0.0', ttyEnabled: true, command: 'cat'),
    containerTemplate(name: 'dind',             image: 'docker:18.05-dind', ttyEnabled: true, command: 'cat', )    
  ],
  volumes: [
    emptyDirVolume(mountPath: '/var/lib/docker', name: 'dind-storage')
  ],
  envVars: [
    podEnvVar(key: 'DOCKER_HOST', key: 'tcp://localhost:2375')
  ] 
  
) {
  node('skaffold') {
    withCredentials([
      usernamePassword(credentialsId: 'docker_id', usernameVariable: 'DOCKER_ID_USR', passwordVariable: 'DOCKER_ID_PSW')
    ]) {
      stage('Info') {
        container('skaffold-insider') {
          sh """
            uname -a
            whoami
            pwd
            ls -al
          """
        }
      }
      stage('Test skaffold') {
        git 'https://github.com/takara9/cowweb.git'
        container('skaffold-insider') {
          sh """
            docker login --username=$DOCKER_ID_USR --password=$DOCKER_ID_PSW --host=$DOCKER_HOST
            skaffold run -p release
          """
        }
      }
    }
  }
}

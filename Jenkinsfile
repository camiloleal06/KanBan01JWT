pipeline {
    agent any
    
    tools {
        maven 'M3'
    }
    
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        
         
          stage("SonarQube analysis") {
            agent any
            steps {
              withSonarQubeEnv('sonar') {
                sh 'mvn -B -DskipTests clean install sonar:sonar'
              }
            }
          }
        
     }
}

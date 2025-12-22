pipeline {
    agent {label 'worker-node-1'}

    environment {
        APP_NAME = 'portfolio-app'
        GROUP_ID = 'com.example'
        ARTIFACT_ID = 'portfolio-app'
        NEXUS_RELEASES = 'http://34.224.66.28:8081/repository/maven-releases/'
        NEXUS_SNAPSHOTS = 'http://34.224.66.28:8081/repository/maven-snapshots/'
    }

    tools {
        maven 'maven3'
        jdk 'jdk17'
    }

    stages {
        stage('git checkout'){
            steps{
                git url: 'https://github.com/zerajudeen-zera/MyPortFolio.git'
            }
        }
        stage('mvn build and test'){
            steps {
                sh 'mvn -B clean verify -DskipTests'
            }
        }
        stage('Sonar Analysis') {
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]){
                    sh """
                    mvn sonar:sonar \
                    -Dsonar.projectKey=portfoliokey \
                    -Dsonar.projectName='portfolio' \
                    -Dsonar.host.url=http://52.204.89.213:9000 \
                    -Dsonar.login=${SONAR_TOKEN}
                    """
                }
                
            }
        }
        stage('Wait for Sonar results quality gate'){
            steps{
                script {
                    withSonarQubeEnv('sonarserver'){
                        timeout(time: 5, unit: 'MINUTES'){
                            waitForQualityGate()
                        }
                    }
                }
            }

        }
        
    }
}
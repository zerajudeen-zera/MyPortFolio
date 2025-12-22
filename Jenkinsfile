pipeline {
    agent {label 'worker-node-1'}

    environment {
        APP_NAME = 'portfolio-app'
        GROUP_ID = 'com.example'
        VERSION = '0.0.1-SNAPSHOT'
        ARTIFACT_ID = 'portfolio-app'
        NEXUS_RELEASES = 'http://34.224.66.28:8081/repository/maven-releases/'
        NEXUS_SNAPSHOTS = 'http://34.224.66.28:8081/repository/maven-snapshots/'
        REGION         = 'us-east-1'
        AWS_ACCOUNT_ID = '262101604988'
        ECR_REPO       = 'zera18/myportfolio'

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
                    withSonarQubeEnv('sonarserver'){
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
        }
        stage('Wait for Sonar results quality gate'){
            steps{
                script {
                    withSonarQubeEnv('sonarserver'){
                        timeout(time: 2, unit: 'MINUTES'){
                            waitForQualityGate abortPipeline: true
                        }
                    }
                }
            }

        }
        stage('Deploy to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus',
                                                usernameVariable: 'NEXUS_USER',
                                                passwordVariable: 'NEXUS_PASS')]) {
                sh """
                    mvn -B deploy -DskipTests \
                    -Dnexus.username=${NEXUS_USER} \
                    -Dnexus.password=${NEXUS_PASS}
                """
                }
            }
        }
        stage('Docker Build & Push to ECR') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding',
                                  credentialsId: 'awscredentials']]) {
                    withCredentials([usernamePassword(credentialsId: 'nexus',
                                                     usernameVariable: 'NEXUS_USER',
                                                     passwordVariable: 'NEXUS_PASS')]) {
                        sh """
                          # Authenticate Docker with ECR
                          aws ecr get-login-password --region ${REGION} | \
                            docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com

                          # Build Docker image (fetch JAR from Nexus inside Dockerfile)
                          docker build \
                            --build-arg NEXUS_USER=${NEXUS_USER} \
                            --build-arg NEXUS_PASS=${NEXUS_PASS} \
                            --build-arg JAR_VERSION=${VERSION} \
                            -t ${APP_NAME}:${VERSION} Dockerfile.nexusbuild

                          # Tag image for your private repo
                          docker tag ${APP_NAME}:${VERSION} ${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/${ECR_REPO}:${VERSION}

                          # Push image to ECR
                          docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/${ECR_REPO}:${VERSION}
                        """
                    }
                }
            }
        }




        
    }
}
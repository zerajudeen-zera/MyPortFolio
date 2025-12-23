pipeline {
    agent { label 'worker-node-1' }

    environment {
        APP_NAME       = 'portfolio-app'
        GROUP_ID       = 'com/example'
        ARTIFACT_ID    = 'portfolio-app'
        NEXUS_SNAPSHOTS= 'http://54.87.34.231:8081/repository/maven-snapshots/'
        REGION         = 'us-east-1'
        AWS_ACCOUNT_ID = '262101604988'
        ECR_REPO       = 'zera18/myportfolio'
    }

    tools {
        maven 'maven3'
        jdk 'jdk17'
    }

    stages {
        stage('Git Checkout') {
            steps {
                git url: 'https://github.com/zerajudeen-zera/MyPortFolio.git'
            }
        }

        stage('Set Version') {
            steps {
                script {
                    env.BASE_VERSION = "0.0.1-SNAPSHOT"   // Always deploy snapshots
                    env.VERSION = "${env.BASE_VERSION}-${env.BUILD_NUMBER}" // For Docker/ECR tags
                    echo "Base version: ${env.BASE_VERSION}"
                    echo "Image version: ${env.VERSION}"
                }
            }
        }

        stage('Maven Build & Test') {
            steps {
                sh 'mvn -B clean verify'
            }
        }

        stage('Sonar Analysis') {
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv('sonarserver') {
                        sh """
                          mvn sonar:sonar \
                          -Dsonar.projectKey=portfolioappkey \
                          -Dsonar.projectName='portfolioapp' \
                          -Dsonar.host.url=http://54.161.219.102:9000 \
                          -Dsonar.login=${SONAR_TOKEN}
                        """
                    }
                }
            }
        }

        stage('Wait for Sonar Quality Gate') {
            steps {
                script {
                    withSonarQubeEnv('sonarserver') {
                        timeout(time: 5, unit: 'MINUTES') {
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
                        -Dusername=${NEXUS_USER} \
                        -Dpassword=${NEXUS_PASS}
                    """
                }
            }
        }

        stage('Docker Build & Push to ECR') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding',
                                  credentialsId: 'awscredentials'],
                                 usernamePassword(credentialsId: 'nexus',
                                                  usernameVariable: 'NEXUS_USER',
                                                  passwordVariable: 'NEXUS_PASS')]) {
                    sh """
                      aws ecr get-login-password --region ${REGION} | \
                        docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com

                      docker build \
                        -f Dockerfile.nexusbuild \
                        --build-arg NEXUS_USER=${NEXUS_USER} \
                        --build-arg NEXUS_PASS=${NEXUS_PASS} \
                        --build-arg JAR_VERSION=${BASE_VERSION} \
                        --build-arg GROUP_ID=${GROUP_ID} \
                        --build-arg ARTIFACT_ID=${ARTIFACT_ID} \
                        --build-arg REPO_URL=${NEXUS_SNAPSHOTS} \
                        -t ${APP_NAME}:${VERSION} .

                      docker tag ${APP_NAME}:${VERSION} ${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/${ECR_REPO}:${VERSION}
                      docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/${ECR_REPO}:${VERSION}
                    """
                }
            }
        }
        stage('Update GitOps Repo') {
            steps {
                withCredentials([string(credentialsId: 'gitops-pat', variable: 'GIT_PAT')]) {
                sh '''
                  rm -rf portfolio-gitops
                  git config --global user.email "ci-bot@example.com"
                  git config --global user.name "CI Bot"
            
                  git clone https://${GIT_PAT}@github.com/zerajudeen-zera/portfolio-gitops.git portfolio-gitops
                  cd portfolio-gitops
            
                  yq e -i ".spec.template.spec.containers[0].image = \\"${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/${ECR_REPO}:${VERSION}\\"" deployment.yaml
            
                  git add deployment.yaml
                  git commit -m "Update image to ${VERSION}" || echo "No changes to commit"
                  git push origin master
                '''
                }
            }
        }
    }
}
pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }

    agent any

    tools {
        maven 'maven-3.9.4'
    }

    stages {
        stage('Code Compilation') {
            steps {
                echo 'Code Compilation is In Progress!'
                sh 'mvn clean compile'
                echo 'Code Compilation is Completed Successfully!'
            }
        }
        stage('Code QA Execution') {
            steps {
                echo 'Junit Test case check in Progress!'
                sh 'mvn clean test'
                echo 'Junit Test case check Completed!'
            }
        }
        stage('Code Package') {
            steps {
                echo 'Creating War Artifact'
                sh 'mvn clean package'
                echo 'Creating War Artifact done'
            }
        }
        stage('Building & Tag Docker Image') {
            steps {
                echo 'Starting Building Docker Image'
                sh "docker build -t ankita3037/flipkart-ms:dev-flipkart-ms-v1.${BUILD_NUMBER} ."
                sh "docker build -t flipkart-ms:dev-flipkart-ms-v1.${BUILD_NUMBER} ."
                echo 'Completed Building Docker Image'
            }
        }
        stage('Docker Image Scanning') {
            steps {
                echo 'Docker Image Scanning Started'
                sh 'java -version'
                echo 'Docker Image Scanning Started'
            }
        }
        stage('Docker push to Docker Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhubCred', variable: 'dockerhubCred')]) {
                        sh "docker login docker.io -u ankita3037 -p ${dockerhubCred}"
                        echo "Push Docker Image to DockerHub: In Progress"
                        sh "docker push ankita3037/flipkart-ms:dev-flipkart-ms-v1.${BUILD_NUMBER}"
                        echo "Push Docker Image to DockerHub: Completed"
                    }
                }
            }
        }
        stage('Docker Image Push to Amazon ECR') {
            steps {
                script {
                    withDockerRegistry([credentialsId: 'ecr:ap-south-1:ecr-credentials', url: "https://350458189256.dkr.ecr.ap-south-1.amazonaws.com"]) {
                        sh """
                        echo "Tagging the Docker Image: In Progress"
                        docker tag flipkart-ms:latest 350458189256.dkr.ecr.ap-south-1.amazonaws.com/flipkart-ms:latest
                        echo "Tagging the Docker Image: Completed"
                        echo "Push Docker Image to ECR: In Progress"
                        docker push 350458189256.dkr.ecr.ap-south-1.amazonaws.com/flipkart-ms:latest
                        echo "Push Docker Image to ECR: Completed"
                        """
                    }
                }
            }
        }
	}
}
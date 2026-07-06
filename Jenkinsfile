pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }

    tools {
        maven 'maven-3.9.16'
    }

    stages {
        stage('Code Compilation') {
            steps {
                echo 'Starting Code Compilation...'
                sh 'mvn clean compile'
                echo 'Code Compilation Completed Successfully!'
            }
        }

        stage('Code QA Execution') {
            steps {
                echo 'Running JUnit Test Cases...'
                sh 'mvn test'
                echo 'JUnit Test Cases Completed Successfully!'
            }
        }

        stage('Code Package') {
            steps {
                echo 'Creating Artifact...'
                sh 'mvn package'
                sh '''
                    # If WAR is expected
                    cp target/*.war target/practice-docker-aws-harboor-${BUILD_NUMBER}.war
                '''
                archiveArtifacts artifacts: 'target/practice-docker-aws-harboor-*.war', fingerprint: true
                echo 'Artifact Created Successfully!!'
            }
        }

        stage('Build & Tag Docker Image') {
            steps {
                sh "docker build -t sagarchattar/practice-docker-aws-harboor:latest -t practice-docker-aws-harboor:latest ."
            }
        }

        stage('Docker Image Scanning') {
            steps {
                echo 'Scanning Docker Image with Trivy...'
                sh 'trivy image sagarchattar/practice-docker-aws-harboor:latest || echo "Scan Failed - Proceeding with Caution"'
                echo 'Docker Image Scanning Completed!'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhubCred', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'docker login -u $DOCKER_USER -p $DOCKER_PASS'
                        sh "docker tag practice-docker-aws-harboor:latest $DOCKER_USER/practice-docker-aws-harboor:latest"
                        sh "docker push $DOCKER_USER/practice-docker-aws-harboor:latest"
                    }
                }
            }
        }

        stage('Clean Up Local Docker Images') {
            steps {
                echo 'Cleaning Up Local Docker Images...'
                sh '''
                docker rmi sagarchattar/practice-docker-aws-harboor:latest || echo "Image not found or already deleted"
                docker rmi practice-docker-aws-harboor:latest || echo "Image not found or already deleted"
                docker image prune -f
                '''
                echo 'Local Docker Images Cleaned Up Successfully!!'
            }
        }
    }
}
pipeline {
    agent any

    environment {
        IMAGE_NAME = "vehicle-insurance-app"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ahmedelasmai/VehicleInsurance.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh '''
                docker run --rm \
                -v $PWD:/app \
                -w /app \
                maven:3.9-eclipse-temurin-17 \
                mvn clean verify
                '''
            }
        }

        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Publish Coverage Report') {
            steps {
                publishHTML([
                    reportDir: 'target/site/jacoco',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Coverage Report'
                ])
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/'
            }
        }
    }
}
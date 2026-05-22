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

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f postgres-pvc.yaml'
                sh 'kubectl apply -f postgres-deployment.yaml'
                sh 'kubectl apply -f postgres-service.yaml'

                sh 'kubectl apply -f app-deployment.yaml'
                sh 'kubectl apply -f app-service.yaml'
            }
        }
    }
}
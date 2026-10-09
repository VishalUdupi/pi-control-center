pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package'
            }
        }
    }

    post {
        success {
            echo 'Pi Control Center build completed successfully!'
        }
        failure {
            echo 'Pi Control Center build failed. Check the console output.'
        }
    }
}
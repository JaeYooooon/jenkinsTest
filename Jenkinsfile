pipeline {
    agent any

    environment {
        SPRING_BOOT_EC2_IP = '3.36.94.176' // 스프링 부트가 배포될 EC2 인스턴스 IP
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'master', url: 'https://github.com/JaeYooooon/jenkinsTest.git'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Deploy') {
            steps {
                sshagent(['dhy04050']) {
                    sh '''
                    scp -o StrictHostKeyChecking=no build/libs/jenkinsTest-0.0.1-SNAPSHOT.jar ubuntu@$SPRING_BOOT_EC2_IP:/home/ubuntu/
                    ssh -o StrictHostKeyChecking=no ubuntu@$SPRING_BOOT_EC2_IP 'pkill -f "java -jar" || true'
                    ssh -o StrictHostKeyChecking=no ubuntu@$SPRING_BOOT_EC2_IP 'nohup java -jar /home/ubuntu/jenkinsTest-0.0.1-SNAPSHOT.jar --server.port=8081 &'
                    '''
                }
            }
        }
    }
}

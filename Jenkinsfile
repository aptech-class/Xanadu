pipeline {
    agent any

    tools {
        maven 'mvn'
    }
    environment {
        ENV_FILE = credentails('env-file')
    }

    stages {
        stage('Test jenkins') {
            steps {
                echo 'Jenkins file is ok!'
            }
        }

        stage('set env') {
            steps {
                sh 'echo \"${ENV_FILE}\" > .env'
            }
        }

        stage('Build app') {
            steps {
                sh 'mvn --version'
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Packageking/push images, deploy to dev') {
            steps {
                withDockerRegistry(credentialsId: 'dockerhub', url:'https://index.docker.io/v1/') {
                    sh 'docker compose up -d --build'
                    sh 'docker compose push'
                }
            }
        }
        // stage('Deploy to server') {
        //     agent {
        //         docker {
        //             image 'duncannguyen/ansible'
        //         }
        //     }

    //     steps {
    //         withCredentials(file[credentialsId:''])
    //     }
    // }
    }
}
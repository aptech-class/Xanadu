pipeline {
    agent any

    tools {
        maven 'mvn'
    }

    stages {
        stage('Test jenkins') {
            steps {
                echo 'Jenkins file is ok!'
                sh 'whoami'
            }
        }

        // stage('set env') {
        //     steps {
        //         withCredentials([file(credentialsId: 'env-file', variable: 'envFile')]) {
        //             sh "cp ${envFile} .env"
        //             sh 'cat .env'
        //         }
        //     }
        // }

        // stage('Build app') {
        //     steps {
        //         sh 'mvn --version'
        //         sh 'mvn clean package -DskipTests=true'
        //     }
        // }

        // stage('Packageking/push images, deploy to dev') {
        //     steps {
        //         withDockerRegistry(credentialsId: 'dockerhub', url:'https://index.docker.io/v1/') {
        //             sh 'docker compose up -d --build'
        //             sh 'docker compose push'
        //             sh 'docker system prune --force'
        //         }
        //     }
        // }

        // stage('Copy server-docker-compose') {
        //     steps {
        //         sh 'docker cp server-docker-compose.yml ansible:/project/java/xanadu'
        //         sh 'docker cp .env ansible:/project/java/xanadu'
        //     }
        // }

        stage('Deploy to server') {
            agent {
                docker {
                    image 'duncannguyen/ansible'
                }
            }
            steps {
                withCredentials([file(credentialsId:'bizfly-private-key', variable:'privateKey')]) {
                    sh 'ls -la'
                    sh 'ls -la ${WORKSPACE}'
                    sh 'cp ${privateKey} private-key'
                    sh 'ansible --version'
                    sh 'ansible -i hosts --private-key private-key -m ping all'
                }
            }
        }
    }
}

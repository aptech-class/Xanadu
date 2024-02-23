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

        stage('set env') {
            steps {
                withCredentials([file(credentialsId: 'env-file', variable: 'envFile')]) {
                    sh "cp ${envFile} .env"
                    sh 'cat .env'
                }
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
                    sh 'docker system prune --force'
                }
            }
        }

        // set up to server
        stage('Pull image') {
            steps {
                sh 'docker pull duncannguyen/ansible'
            }
        }

        stage('Deploy to server') {
            agent {
                docker {
                    image 'duncannguyen/ansible'
                    args '-u root'
                }
            }
            steps {
                withCredentials([file(credentialsId:'bizfly-private-key', variable:'privateKey')]) {
                    sh 'ls -la'
                    sh 'chmod +w private-key'
                    sh "cp ${privateKey} private-key"
                    sh 'chmod 400 private-key'
                    sh 'cat private-key'
                    sh 'ansible --version'
                    sh '''
                            ansible -i hosts --private-key private-key -m ping all \
                            -e "ansible_ssh_common_args=\'-o StrictHostKeyChecking=no\'"
                        '''
                    sh 'ansible-playbook -i hosts --private-key private-key playbook.yml'
                }
            }
        }
    }
}

pipeline {
    agent any

    stages {
        stage(name: 'Test jenkins'){
            steps{
                echo 'Jenkins file is ok!'
            }
        }
        // stage(name: 'Build app') {
        //     steps {
        //         sh 'mvnw --version'
        //         sh 'mvnw clean package -DskipTests=true'
        //     }
        // }
        // stage(name: 'Packageking/push images, deploy to dev') {
        //     steps {
        //         withDockerRegistry(credentialsId: 'dockerhub', url:'https://index.docker.io/v1/') {
        //             sh 'docker compose up -d --build'
        //             sh 'docker compose push'
        //         }
        //     }
        // }
        // stage(name: 'Deploy to server') {
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

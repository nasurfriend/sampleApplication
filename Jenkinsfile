// Uses Declarative syntax to run commands inside a container.
pipeline {
    agent {
        kubernetes {
            // Rather than inline YAML, in a multibranch Pipeline you could use: yamlFile 'jenkins-pod.yaml'
            // Or, to avoid YAML:
            // containerTemplate {
            //     name 'shell'
            //     image 'ubuntu'
            //     command 'sleep'
            //     args 'infinity'
            // }
            yaml '''
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: maven
    image: registry.redhat.io/openshift4/ose-jenkins-agent-maven
    command:
    - sleep
    args:
    - infinity
'''
            // Can also wrap individual steps:
            // container('shell') {
            //     sh 'hostname'
            // }
            defaultContainer 'maven'
        }
    }
    stages {
        stage('Test') {
            steps {
               sh "echo test"
            }
        }
        stage('Pull') {
            steps {
                sh "git clone --branch master https://github.com/nasurfriend/sampleApplication.git"
				sh "pwd"
			    dir('./spring-boot-hello-world') {
                   sh """
                       pwd
                       ls -ltr
                       
                   """
                }
            }
        }
        stage('Build') {
            steps {
			    dir('./sampleApplication') {
                   sh """
                       mvn clean install
                       
                   """
                }
            }
        }
    }
}

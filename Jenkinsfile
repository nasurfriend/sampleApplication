pipeline {
    agent {
        kubernetes {
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
	environment{
		application_name = "${application}"
		ocp_environment= 'sandbox-env'
	}
    stages {
        stage('Prerequisite') {
            steps {
               sh "echo test"

				sh 'curl -k https://get.helm.sh/helm-v3.7.0-linux-amd64.tar.gz > helm && tar -zxvf helm linux-amd64 && chmod +x ./linux-amd64/helm'
                sh 'linux-amd64/helm version'
				sh 'curl -k https://downloads-openshift-console.apps.cywd.jtbq.p1.openshiftapps.com/amd64/linux/oc.tar > oc && tar -xf oc && chmod +x oc'
				
                withCredentials([usernamePassword(credentialsId: 'b75b3b5c-55eb-4f15-a665-015f2648b2cf', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh "./oc login --insecure-skip-tls-verify https://api.cywd.jtbq.p1.openshiftapps.com:6443 -u $USERNAME -p $PASSWORD && ./oc project $ocp_environment"
				}
				sh './oc whoami'
                sh "ls -ltrh" 
            }
        }
        stage('Pull') {
            steps {
				echo "application name: ${application_name}"
				
                sh "git clone --branch master https://github.com/nasurfriend/sampleApplication.git"
				sh "pwd"
				sh "ls -ltr"
			    dir('./sampleApplication') {
                   sh """
                       pwd
                       ls -ltr
                       
                   """
                }
            }
        }
		stage('Deployment') {
			steps {
				script {
					echo "application name: ${application_name}"
					sh "./oc project sandbox-deployment"
					dir("./${application_name}") {
						appName="${application}".toLowerCase()
						sh "../linux-amd64/helm upgrade --install ${appName}-deployment ./OCP/Deployments --set tag=${version},namespace=sandbox-deployment,applicationName=${appName}"
					}
				}
			}
		}
    }
}

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
	environment{
		application_name = 'sampleApplication'
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
			    dir('./OCP/Builds') {
                   sh """
                       pwd
                       ls -ltr
                       
                   """
                }
            }
        }
        stage('Build') {
            steps {
				echo "application name: ${application_name}"
				
			    dir('./sampleApplication') {
                   sh """
                       mvn clean install
                       
                   """
                }
            }
        }
		stage('Dockerised') {
            steps {
			    echo "application name: ${application_name}"
				
				script {
					
						version = sh( script: "mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | sed -n -e '/^\\[.*\\]/ !{ /^[0-9]/ { p; q } }'", returnStdout:true ).trim()
						imgver = version
					
						echo "Image Ver: ${imgver}"
						sh "pwd && ls -ltrha "
						appName="${application_name}".toLowerCase()
						sh "./oc project sandbox-env"
						dir("./${application_name}") {
							sh 'ls -ltr'
							appName="${application_name}".toLowerCase()
							sh "../linux-amd64/helm upgrade --install ${appName}-deployment ./OCP/Builds --set tag=${version},namespace=jenkins,applicationName=${appName}"
							sh "../oc start-build ${appName} --from-dir=. --follow --wait"
						}
				}
            }
        }
    }
}

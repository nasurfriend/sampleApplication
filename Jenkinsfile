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
	}
    stages {
        stage('Test') {
            steps {
               sh "echo test"
            }
        }
        stage('Pull') {
            steps {
				echo "application name: ${application_name}"
				
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
					dir("./sampleApplication") {
						version = sh( script: "mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | sed -n -e '/^\\[.*\\]/ !{ /^[0-9]/ { p; q } }'", returnStdout:true ).trim()
						imgver = version
					}
						echo "Image Ver: ${imgver}"
						sh "pwd && ls -ltrha "
						appName="${application_name}".toLowerCase()
						sh "./oc project jenkins-ephimeral"
						sh "linux-amd64/helm upgrade --install ${appName} ./helmchart --set tag=${version},namespace=jenkins-ephimeral,applicationName=${appName}"
						dir("./sampleApplication") {
							appName="${application_name}".toLowerCase()
							sh "../../oc start-build ${appName} --from-dir=. --follow --wait"
						}
				}
            }
        }
    }
}

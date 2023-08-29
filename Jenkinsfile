pipeline {
	agent any
    tools {
        maven 'Maven-3.9.4'
		jdk 'azul-18.0.2.1'
    }

    stages {
        stage('Build') {
			steps{
				bat 'mvn -f HRM/pom.xml clean package && exit %%ERRORLEVEL%%'
			}
		}
		
		stage('Copy jar') {
			steps{
				bat script: '''copy HRM\\target\\HRM-0.0.1-SNAPSHOT.jar HRM\\docker\\HRM-0.0.1-SNAPSHOT.jar'''
			}
		}
		
		stage('Clean up containers and images') {
		    steps{
				bat 'docker container rm -f docker-testdatabase-1'
				bat 'docker container rm -f docker-hrm-api-1'
				script{
					try{
						bat 'docker image rm docker-hrm-api'
					} catch (Exception err) {
						echo "Error: ${err}"
					}
					try{
						bat 'docker image rm docker-testdatabase'
					} catch (Exception err) {
						echo "Error: ${err}"
					}
				}
		    }
		}
		
		stage('Build Images') {
		    steps {
		        bat 'docker-compose -f HRM/docker/docker-compose.yml build'
		    }
		}
		
		stage('Run Containers') {
		    steps {
				bat 'docker-compose -f HRM/docker/docker-compose.yml up -d'
		    }
		}
    }
}
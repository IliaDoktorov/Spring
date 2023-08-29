pipeline {
	agent any
    tools {
        maven 'Maven-3.9.4'
		jdk 'azul-18.0.2.1'
    }

    stages {
        stage('Clone') {
			steps {
				git 'https://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@github.com/IliaDoktorov/Spring.git'
			}
		}
		
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
		
		stage('Build Images') {
		    steps {
		        bat 'docker-compose -f HRM/docker/docker-compose.yml build'
		    }
		}
		
		stage('Remove existing containers') {
		    steps {
		        bat 'docker container rm -f docker-testdatabase-1'
				bat 'docker container rm -f docker-hrm-api-1'
		    }
		}
		
		stage('Run Containers') {
		    steps {
				bat 'docker-compose -f HRM/docker/docker-compose.yml up -d'
		    }
		}
    }
}
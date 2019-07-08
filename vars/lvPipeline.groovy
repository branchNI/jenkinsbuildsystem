#!/usr/bin/env groovy

def call(viPath, utfPath, reportPath) {
	node {
		echo 'Starting Build...'

		stage ('Simple VI Test') {
			bat "LabVIEWCLI -OperationName RunVI -VIPath %CD%\\${viPath} hello"
		}
		
		stage('SCM Checkout') {
			echo 'Attempting to get source from repo...'
			timeout(time: 4, unit: 'MINUTES') {
				checkout scm
			}
		}
		
		echo 'Running unit tests...'
		
		stage ('Unit Tests') {
			bat "LabVIEWCLI -OperationName RunUnitTests -ProjectPath %CD%\\${utfPath} -JUnitReportPath + \${reportPath}"
		}
	}
}


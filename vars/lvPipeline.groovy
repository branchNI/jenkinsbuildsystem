#!/usr/bin/env groovy

def call(viPath, utfPath, reportPath) {
	node {
		echo 'Simple VI test'

		stage ('Simple_VI_Test') {
			bat "LabVIEWCLI -OperationName RunVI -VIPath " + viPath + " hello"
		}
		
		echo 'Running unit tests'
		
		stage ('Unit_Tests') {
			bat "LabVIEWCLI -OperationName RunUnitTests -ProjectPath " + utfPath + " -JUnitReportPath " + reportPath
		}
	}
}


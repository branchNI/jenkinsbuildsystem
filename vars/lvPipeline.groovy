#!/usr/bin/env groovy
def PULL_REQUEST = env.CHANGE_ID

//ENTER THE ABOVE INFORMATION

def call(viPath, utfPath, reportPath) {
	node {
		echo 'Starting Build...'

		stage('SCM Checkout') {
			echo 'Attempting to get source from repo...'
			timeout(time: 4, unit: 'MINUTES') {
				checkout scm
			}
		}

		stage ('Simple VI Test') {
			bat "LabVIEWCLI -OperationName RunVI -VIPath \"%CD%\\${viPath}\" hello"
			sleep(time: 3, unit: "SECONDS")
		}
		
		echo 'Running unit tests...'
		
		stage ('Unit Tests') {
			bat "LabVIEWCLI -OperationName RunUnitTests -ProjectPath \"%CD%\\${utfPath}\" -JUnitReportPath \"%CD%\\${reportPath}\""
		}
		
		echo 'Running diff...'
		
		//HARD CODED FOR NOW
		stage('Diff') {
			echo 'Diffed'
		}
		
		/*
		echo 'Posting comment to PR...'
		
		stage('Comment') {
			bat "python github_commenter.py -t \"${GITHUB_ACCESS_TOKEN}\" -d \"C:\\Users\\Brandon\\Documents\\Diffing\\output\" -p \"${PULL_REQUEST}\" -i \"${GITHUB_USERNAME}/${GITHUB_REPONAME}/pr-${PULL_REQUEST}\" -r \"${GITHUB_USERNAME}/${GITHUB_REPONAME}\""
		}
		*/
	}
}


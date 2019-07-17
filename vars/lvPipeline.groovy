#!/usr/bin/env groovy
def PULL_REQUEST = env.CHANGE_ID

//ENTER THE ABOVE INFORMATION

def call(viPath, utfPath, lvVersion) {

	switch(lvVersion){  //This is to abstract out the different Jenkinsfile conventions of setting version to 14.0 instead of 2014.
	  case "18.0":
		lvVersion="2018"
		break
	  case "19.0":
		lvVersion="2019"
		break
	  case "20.0":
		lvVersion="2020"
		break
	}

	node {
		echo 'Starting Build...'

		stage ('Pre-Clean'){
		preClean()
		}
	  
		stage('SCM Checkout') {
			echo 'Attempting to get source from repo...'
			timeout(time: 4, unit: 'MINUTES') {
				checkout scm
			}
		}

		stage ('Create Directories'){
          bat 'mkdir TEMPDIR'
		  bat 'mkdir PICREPO'
        }
		
		/*
		
		stage ('Simple VI Test') {
			bat "LabVIEWCLI -OperationName RunVI -VIPath \"%CD%\\${viPath}\" hello"
			sleep(time: 3, unit: "SECONDS")
		}
		
		echo 'Running unit tests...'
		
		stage ('Unit Tests') {
			bat "LabVIEWCLI -OperationName RunUnitTests -ProjectPath \"%CD%\\${utfPath}\" -JUnitReportPath \"%CD%\\TEMPDIR\\report.xml\""
		}
		
		*/
		
		echo 'Running diff...'
		
		// If this change is a pull request, diff the VIs.
		echo 'CHANGE_ID: '
		echo env.CHANGE_ID
		
		if (env.CHANGE_ID) {
			stage ('Diff VIs'){
				try {
				timeout(time: 60, unit: 'MINUTES') {
					lvDiff(lvVersion)
					echo 'Diff Succeeded!'
				}
				} catch (err) {
					currentBuild.result = "SUCCESS"
					echo "Diff Failed: ${err}"
				}
			}
		}		
		
		/*
		echo 'Posting comment to PR...'
		
		stage('Comment') {
			bat "python github_commenter.py -t \"${GITHUB_ACCESS_TOKEN}\" -d \"C:\\Users\\Brandon\\Documents\\Diffing\\output\" -p \"${PULL_REQUEST}\" -i \"${GITHUB_USERNAME}/${GITHUB_REPONAME}/pr-${PULL_REQUEST}\" -r \"${GITHUB_USERNAME}/${GITHUB_REPONAME}\""
		}
		*/
	}
}


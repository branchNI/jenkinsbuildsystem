#!/usr/bin/env groovy
def PULL_REQUEST = env.CHANGE_ID

//ENTER THE ABOVE INFORMATION

def call(viPath, utfPath, lvVersion, lvPath) {

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
		
		//TEST
		echo 'job name: '
		echo env.JOB_NAME

		stage ('Pre-Clean'){
		preClean()
		}
	  
		stage('SCM Checkout') {
			echo 'Attempting to get source from repo...'
			timeout(time: 4, unit: 'MINUTES') {
				checkout scm
			}
			
			echo 'Cloning build tools...'
			timeout(time: 5, unit: 'MINUTES') {
				cloneBuildTools()
			}
		}

		stage ('Create Directories'){
          bat 'mkdir TEMPDIR'
		  bat 'mkdir PICREPO'
        }
		
		stage ('Simple VI Test') {
			//bat "LabVIEWCLI -LabVIEWPATH ${lvPath} -OperationName RunVI -VIPath \"%CD%\\${viPath}\" hello"
			bat "LabVIEWCLI -LabVIEWPATH ${lvPath} -OperationName RunVI -VIPath \"%CD%\\${viPath}\" hello"
			sleep(time: 3, unit: "SECONDS")
		}
		
		echo 'Running unit tests...'
		
		stage ('Unit Tests') {
			//bat "LabVIEWCLI -LabVIEWPATH ${lvPath} -OperationName RunUnitTests -ProjectPath \"%CD%\\${utfPath}\" -JUnitReportPath \"%CD%\\TEMPDIR\\report.xml\""
			bat "LabVIEWCLI -OperationName RunUnitTests -ProjectPath \"%CD%\\${utfPath}\" -JUnitReportPath \"%CD%\\TEMPDIR\\report.xml\""
		}
		
		echo 'Running diff...'
		
		// If this change is a pull request, diff the VIs.
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


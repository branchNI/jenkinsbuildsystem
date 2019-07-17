def call(lvVersion) {
	def diffDir = "${WORKSPACE}\\DIFFDIR"
	def stepsDir = "${WORKSPACE}\\jenkinsbuildsystem\\steps"
	
	//bat "if exist ${diffDir} rmdir /s /q ${diffDir}"
	//bat "mkdir ${diffDir}"
	
	
	echo 'Running LabVIEW diff build between origin/master and this commit'
      
    bat "python -u \"${stepsDir}\\labview_diff.py\" \"${WORKSPACE}\" \"${diffDir}\" ${lvVersion} --target=origin/master"
    
	//bat "git difftool --no-prompt --extcmd=\"${WORKSPACE}\\jenkinsbuildsystem\\steps\\labview-diff.bat\" \$LOCAL \$REMOTE diff_dir ${lvVersion}\" origin/master HEAD"
	
	// Silencing echo so as to not print out the token.
	//bat "@python L:\\github_commenter.py --token=${GITHUB_DIFF_TOKEN} --pic-dir=${diffDir} --pull-req=${CHANGE_ID} --info=${JOB_NAME} --pic-repo=${DIFFING_PIC_REPO}"
}

def call(lvProjectPath, targetName, buildSpecName, lvVersion, lvBitness) {
	def stepsDir = "${WORKSPACE}\\jenkinsbuildsystem\\steps"
	def projectPath = "${WORKSPACE}\\${lvProjectPath}"
		
	echo 'Running LabVIEW build spec \"${buildSpecName}\" in \"${lvProjectPath}"'

	bat "python -u \"${stepsDir}\\labview_diff.py\" \"${WORKSPACE}\" \"${diffDir}\" ${lvVersion} ${lvBitness} --target=origin/master"
    
	bat "python -u \"${stepsDir}\\github_commenter.py\" --token=\"${ACCESS_TOKEN}\" --pic-dir=\"${diffDir}\" --pull-req=\"${env.CHANGE_ID}\" --info=\"${ORG_NAME}/${repo}/${env.CHANGE_ID}\" --pic-repo=\"${ORG_NAME}/${PIC_REPO}\""
}

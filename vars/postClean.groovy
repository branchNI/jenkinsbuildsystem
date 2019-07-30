def call() {
	def stepsDir = "${WORKSPACE}\\${BUILD_SYSTEM_REPO}\\steps"
	
	echo 'Archiving all artifacts'
	bat "Python -u \"${stepsDir}\\move_files.py\" \"${LV_BUILD_OUTPUT_DIR}\" \"${WORKSPACE}\\TEMPDIR\""
	archiveArtifacts artifacts: 'TEMPDIR/**'
	echo 'Archive complete!'
}
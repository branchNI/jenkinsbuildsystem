def call() {
	echo 'Archiving all artifacts'
	echo '${LV_BUILD_OUTPUT_DIR}'
	bat "Python -u \"${stepsDir}\\move_files.py\" \"${LV_BUILD_OUTPUT_DIR}\" \"${WORKSPACE}\\TEMPDIR\""
	archiveArtifacts artifacts: 'TEMPDIR/**'
	echo 'Archive complete!'
}
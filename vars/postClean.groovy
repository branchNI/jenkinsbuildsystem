def call() {
	echo 'Archiving all artifacts'
	archiveArtifacts artifacts: 'TEMP_DIR\**'
}
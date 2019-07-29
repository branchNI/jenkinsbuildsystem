def call() {
	echo 'Archiving all artifacts'
	archiveArtifacts artifacts: 'TEMPDIR/**'
}
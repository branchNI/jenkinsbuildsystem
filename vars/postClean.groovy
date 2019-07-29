def call() {
	echo 'Archiving all artifacts'
	archiveArtifacts artifacts: 'C:\Windows\Temp\jenkinsArchive\**'
}
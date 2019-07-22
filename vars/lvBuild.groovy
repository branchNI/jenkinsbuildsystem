def call(lvProjectPath, targetName, buildSpecName, lvVersion, lvBitness) {
	def stepsDir = "${WORKSPACE}\\jenkinsbuildsystem\\steps"
	def projectPath = "${WORKSPACE}\\${lvProjectPath}"
		
	echo 'Running LabVIEW build spec \"${buildSpecName}\" for target \"${targetName}\" in \"${lvProjectPath}\"'

	bat "python -u \"${stepsDir}\\labview_build.py\" \"${WORKSPACE}\" \"${diffDir}\" ${lvVersion} ${lvBitness} --target=origin/master"
}

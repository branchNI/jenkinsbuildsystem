def call(lvVersion, utf_project_path, report_path) {
	def stepsDir = "${WORKSPACE}\\jenkinsbuildsystem\\steps"
	
	echo 'Running unit tests on \"${utf_project_path}\"'

	bat python -u \"${stepsDir}\\labview_utf.py\" \"${utf_project_path\" \"${report_path}\" ${lvVersion}
}


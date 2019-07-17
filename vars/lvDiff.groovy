def call(lvVersion, ORG_NAME) {
	def diffDir = "${WORKSPACE}\\DIFFDIR"
	def stepsDir = "${WORKSPACE}\\jenkinsbuildsystem\\steps"
	def prNum = env.CHANGE_ID
	def repo = getComponentParts()['repo']
	def gitHubdifftoken = "41940eef93d0080aea72" + "fa004f02551bc5b56174"

	echo 'Running LabVIEW diff build between origin/master and this commit'

	bat "python -u \"${stepsDir}\\labview_diff.py\" \"${WORKSPACE}\" \"${diffDir}\" ${lvVersion} --target=origin/master"
    
	bat "python -u \"${stepsDir}\\github_commenter.py\" --token=\"${gitHubdifftoken}\" --pic-dir=\"${diffDir}\" --pull-req=env.CHANGE_ID --info=\"ORG_NAME/${repo}/${env.CHANGE_ID}\" --pic-repo=\"ORG_NAME/${repo}\""
	
	//bat "@python -u \"${resourcesDir}/github_commenter.py\" --token="${githubDiffToken}" --pic-dir=\"${diffDir}\" --pull-req=\"${CHANGE_ID}\" --info=\"${JOB_NAME}\" --pic-repo=\"${diffingPicRepo}\"
	
	//bat "@python L:\\github_commenter.py --token=${GITHUB_DIFF_TOKEN} --pic-dir=${diffDir} --pull-req=${CHANGE_ID} --info=${JOB_NAME} --pic-repo=${DIFFING_PIC_REPO}"
}

#!/usr/bin/env groovy

def call(viPath) {
	node {
		bat "LabVIEWCLI -OperationName RunVI -VIPath C:\\Users\\Brandon\\Desktop\\Hello.vi hello"
	}
}


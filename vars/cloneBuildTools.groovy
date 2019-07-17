def call(ORG_NAME){
   echo 'Cloning build tools to workspace.'
   
   def branch = env."library.vs-build-tools.version"
   buildToolsDir = cloneRepo("https://github.com/$ORG_NAME/jenkinsbuildsystem", branch)
   return buildToolsDir
}

pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven 3.9.6"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Anton-khm/SauceDemoQA30.git'

                // Run Maven on a Unix agent.
                sh "mvn clean test -Dbrowser=${params.BROWSER}"
            }

            parameters{
                choice(choices: ['chrome', 'firefox', 'edge'], name: 'BROWSER')
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
                }
            }
        }
    }
}

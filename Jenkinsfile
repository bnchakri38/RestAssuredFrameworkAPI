pipeline 
{
    agent any
    
    tools{
        maven 'maven'
        }

    stages 
    {
               
        stage("Deploy to Dev"){
            steps{
                echo("deploy to Dev")
            }
        }
        
        stage('Sanity API Automation Test on DEV') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/bnchakri38/RestAssuredFrameworkAPI.git'
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml"
                    
                }
            }
        }
        
        
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to qa done")
            }
        }
          
                
                
        stage('Regression API Automation Tests on QA') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/bnchakri38/RestAssuredFrameworkAPI.git'
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml"
                    
                }
            }
        }
                
     
        stage('Publish Allure Reports') {
           steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
        
        stage('Publish ChainTest Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'target/chaintest', 
                                  reportFiles: 'Index.html', 
                                  reportName: 'HTML API Regression ChainTest Report', 
                                  reportTitles: ''])
            }
        }
        
        stage("Deploy to Stage"){
            steps{
                echo("deploy to Stage")
            }
        }
        
        stage('Sanity API Automation Test on Stage') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/bnchakri38/RestAssuredFrameworkAPI.git'
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml"
                    
                }
            }
        }
        
        
        stage('Publish sanity ChainTest Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'target/chaintest', 
                                  reportFiles: 'Index.html', 
                                  reportName: 'HTML API Sanity ChainTest Report', 
                                  reportTitles: ''])
            }
        }
        
        
        stage("Deploy to PROD"){
            steps{
                echo("deploy to PROD")
            }
        }
        
        stage('Sanity API Automation Test on PROD') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/bnchakri38/RestAssuredFrameworkAPI.git'
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml"
                    
                }
            }
        }
        
        
    }
}
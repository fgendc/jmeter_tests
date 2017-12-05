def jobName = 'jmeter-ci-test'
def GIT_URL = "${GIT_URL}".trim()
def JMETER_TEST_FILE = "${JMETER_TEST_FILE}".trim()
def JMETER_FORMAT = "${JMETER_FORMAT}".trim()
def JMETER_BIN = "${JMETER_BIN}".trim()

System.println(JMETER_TEST_FILE)

job(jobName) {
  /*parameters {
    stringParam('JiraProject', '')
    stringParam('VARIABLE_FROM_POST', '')
  }*/
  scm {
    git{
      remote {
        name('origin')
        url(GIT_URL)
      }
      branch('*/master')
    }
  }
  steps {    
    shell(JMETER_BIN + ' -Jjmeter.save.saveservice.output_format=' + JMETER_FORMAT + ' -n -t ' + JMETER_TEST_FILE + ' -l Test.jtl')
  }
  configure { project ->
        project / publisher << 'hudson.plugins.performance.PerformancePublisher'{
            errorFailedThreshold '-1'
            errorUnstableThreshold '-1'
            errorUnstableResponseTimeThreshold ''
            relativeFailedThresholdPositive '0.0'
            relativeFailedThresholdNegative '0.0'
            relativeUnstableThresholdPositive '0.0'
            relativeUnstableThresholdNegative '0.0'
            nthBuildNumber '0'
            configType 'ART'
            graphType 'ART'
            modeOfThreshold 'false'
            failBuildIfNoResultFile 'true'
            compareBuildPrevious 'false'
            optionType 'ART'
            xml ''
            modePerformancePerTestCase 'false'
            excludeResponseTime 'false'
            modeThroughput 'false'
            modeEvaluation 'false'
            ignoreFailedBuilds 'false'
            ignoreUnstableBuilds 'false'
            persistConstraintLog 'false'
            sourceDataFiles 'Test.jtl'
        }
  }
    
}
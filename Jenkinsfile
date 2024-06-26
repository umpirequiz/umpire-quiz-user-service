import groovy.transform.Field

@Field
def qualityGateStatus = null

pipeline {
  agent any
  environment {
    REPO_NAME = env.GIT_URL.replace('.git', '').split('/').last()
    VERSION = getNextSemanticVersion minorPattern: '^[Ff]eat.*', patchPattern: '^[Ff]ix'
    PREV_VERSION = getHighestSemanticVersion().toString()
    SONAR_BREAKS_BUILD = true
    SONAR_PROJECT_KEY = 'Umpire-Quiz-user-service'
    SONAR_PROJECT_NAME = 'Umpire Quiz User Service'
  }
  tools {
    maven 'Maven 3.9.6'
    jdk 'JDK21'
  }
  stages {
    stage('Set Version') {
      when { not { equals(actual: "${VERSION}", expected: "${PREV_VERSION}") } }
      steps {
        script {
          SNAPSHOT_STRING = (BRANCH_NAME == 'main') ? "" : "-SNAPSHOT"
        }
        sh "git checkout $BRANCH_NAME"
        sh "mvn versions:set -DnewVersion=${VERSION}${SNAPSHOT_STRING}"
        withCredentials([string(credentialsId: 'Github-token', variable: 'TOKEN')]) {
          script {
            REPO_URL_WITH_AUTH = 'https://${TOKEN}@github.com/Javaklas-XIV/${REPO_NAME}.git'
          }
          sh "git config remote.origin.url ${REPO_URL_WITH_AUTH}"
        }
        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
          sh 'git commit -am "ci: Updated version number."'
          sh "git push origin ${BRANCH_NAME}"
        }
      }
    }

    stage('Unit Tests') {
      when {
        not {
          branch 'main'
        }
      }
      steps {
        sh "mvn clean package"
        junit testResults: '**/target/surefire-reports/*.xml'
      }
    }

    stage('All Tests') {
      when {
        branch 'main'
      }
      steps {
        script {
          withSonarQubeEnv(credentialsId: 'Sonar-Secret') {
            sh "mvn clean verify sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.projectName='${SONAR_PROJECT_NAME}'"
          }
        }
        waitForQualityGate abortPipeline: SONAR_BREAKS_BUILD, credentialsId: 'Sonar-Secret'
        sh "find . -iname mutations.xml | xargs sed -i -E -e 's#</?(blocks|indexes)>##g'" // Change mutations.xml for jenkins compat.
        junit testResults: '**/target/surefire-reports/*.xml, **/target/failsafe-reports/*.xml'
        pitmutation killRatioMustImprove: true, minimumKillRatio: 95.0
      }
    }

    stage('Docker push') {
      when {
        allOf {
          not { equals(actual: "${VERSION}", expected: "${PREV_VERSION}") }
          branch 'main'
        }
      }
      steps {
        withCredentials([usernamePassword(credentialsId: 'Harbor_Robot', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
          sh 'mvn docker:build\
                  docker:push\
                  -Ddocker.push.username=$USERNAME\
                  -Ddocker.push.password=$PASSWORD'
        }
        sh 'mvn docker:remove'
      }
    }

    stage('Github release') {
      when {
        allOf {
          not { equals(actual: "${VERSION}", expected: "${PREV_VERSION}") }
          branch 'main'
        }
      }
      steps {
        script {
          id = release()
        }
      }
    }

    stage('Update Gebruikers Test Omgeving') {
      when {
        allOf {
          not { equals(actual: "${VERSION}", expected: "${PREV_VERSION}") }
          branch 'main'
        }
      }
      steps {
        withCredentials([sshUserPrivateKey(credentialsId: 'Umpire-Quiz-Acceptatie', keyFileVariable: 'KEY', usernameVariable: 'USER')]) {
          sh 'ssh -i $KEY $USER@192.168.178.240 ~/update-Quiz.sh'
        }
      }
    }
  }

  post {
    cleanup {
      cleanWs()
    }
  }
}

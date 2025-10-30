pipeline {
  agent any

  tools {
    maven 'maven3'
  }

  environment {
    APP_NAME = "service-trajet"
    IMAGE_TAG = "${BUILD_NUMBER}"
    IMAGE_NAME = "saadalaouisosse/${APP_NAME}:${IMAGE_TAG}"
    K8S_NAMESPACE = "transport"
  }

  stages {

    stage('Checkout') {
      steps {
        git branch: 'main', url: 'https://github.com/saadalaoui23/pipelinedevops.git'
      }
    }

    stage('Build & Test') {
      steps {
        bat 'mvn clean test'
      }
    }

    stage('Build Docker Image') {
      steps {
        bat "docker build -t ${IMAGE_NAME} ."
      }
    }

    stage('Push Docker Image') {
      steps {
        withCredentials([string(credentialsId: 'dockerhub-token', variable: 'DOCKER_TOKEN')]) {
          bat """
            echo $DOCKER_TOKEN | docker login -u saadalaouisosse --password-stdin
            docker push ${IMAGE_NAME}
          """
        }
      }
    }

    stage('Deploy to Kubernetes') {
      steps {
        // mise à jour du manifeste avant déploiement
        bat """
powershell -Command "(Get-Content Deployment.yaml) -replace 'image: saadalaouisosse/trajet-service:1.0.0', 'image: ${IMAGE_NAME}' | Set-Content Deployment.yaml"
kubectl apply -f postgres-deployment.yaml -n ${K8S_NAMESPACE}
kubectl apply -f postgres-service.yaml -n ${K8S_NAMESPACE}
kubectl apply -f Deployment.yaml -n ${K8S_NAMESPACE}
kubectl apply -f service.yaml -n ${K8S_NAMESPACE}
kubectl rollout status deployment/${APP_NAME} -n ${K8S_NAMESPACE}
"""

      }
    }
  }

  post {
    success {
      echo "Déploiement réussi sur Kubernetes : ${IMAGE_NAME}"
    }
    failure {
      echo "Pipeline échoué."
    }
    always {
      cleanWs()
    }
  }
}

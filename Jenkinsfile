pipeline {
  agent any

  tools {
    maven 'maven3'
  }

  environment {
    APP_NAME = "service-trajet"
    IMAGE_TAG = "${BUILD_NUMBER}"
    IMAGE_NAME = "saadalaouisosse/${APP_NAME}:${IMAGE_TAG}"
    KUBECONFIG = "C:\\ProgramData\\Jenkins\\.kube\\config"
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
    stage('SAST - SonarCloud Analysis') {
  steps {
    withSonarQubeEnv('sonarcloud') {
      withCredentials([string(credentialsId: 'sonarcloud-token', variable: 'SONAR_TOKEN')]) {
        bat """
          mvn sonar:sonar ^
            -Dsonar.projectKey=saadalaoui23_pipelinedevops ^
            -Dsonar.organization=saadalaoui23 ^
            -Dsonar.host.url=https://sonarcloud.io ^
            -Dsonar.login=%SONAR_TOKEN%
        """
      }
    }
  }
}


      stage('SCA - Dependency Check') {
  steps {
    bat '''
      if not exist dependency-check-report mkdir dependency-check-report
      dependency-check.bat ^
        --project "service-trajet" ^
        --scan . ^
        --format "HTML" ^
        --format "JSON" ^
        --out dependency-check-report
    '''
  }
  post {
    always {
      archiveArtifacts artifacts: 'dependency-check-report/**', allowEmptyArchive: true
    }
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

  stage('Init Docker & Minikube') {
  steps {
    bat '''
      echo ===== Vérification de Docker =====
      docker version >nul 2>&1
      if %ERRORLEVEL% NEQ 0 (
        echo Docker non disponible.
        echo Assurez-vous que Docker Desktop est lancé avant Jenkins.
        exit /b 1
      ) else (
        echo Docker est actif.
      )

      echo ===== Vérification du cluster Minikube =====
      minikube status >nul 2>&1
      if %ERRORLEVEL% NEQ 0 (
        echo Cluster inactif → démarrage...
        minikube start --driver=docker --memory=4096 --cpus=2 --image-mirror-country=fr
      ) else (
        echo Cluster déjà actif.
      )

      kubectl config use-context minikube
      kubectl get nodes
    '''
  }
}


    stage('Deploy to Kubernetes') {
      steps {
        bat """
      powershell -Command "(Get-Content Deployment.yaml) -replace 'image: saadalaouisosse/trajet-service:1.0.0', 'image: ${IMAGE_NAME}' | Set-Content Deployment.yaml"

      
      kubectl get secret trajet-horraire-secret -n ${K8S_NAMESPACE} >nul 2>&1
      if %ERRORLEVEL% NEQ 0 (
        echo "Création du secret trajet-horraire-secret..."
        kubectl create secret generic trajet-horraire-secret ^
          --from-literal=DB_HOST=trajet-horraire-db ^
          --from-literal=DB_PORT=5432 ^
          --from-literal=DB_NAME=service_trajet_horraire ^
          --from-literal=DB_USER=trajet_horraire ^
          --from-literal=DB_PASS=trajet_horraire ^
          --from-literal=SPRING_PROFILES_ACTIVE=prod ^
          -n ${K8S_NAMESPACE}
      ) else (
        echo "Secret déjà présent, aucune création nécessaire."
      )

      # Appliquer les manifests
      kubectl apply -f postgres-deployment.yaml -n ${K8S_NAMESPACE}
      kubectl apply -f postres-service.yaml -n ${K8S_NAMESPACE}
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

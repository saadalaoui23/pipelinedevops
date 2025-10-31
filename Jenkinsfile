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
            echo %DOCKER_TOKEN% | docker login -u saadalaouisosse --password-stdin
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
        exit /b 1
      )

      echo ===== Vérification du cluster Minikube =====
      minikube status >nul 2>&1
      if %ERRORLEVEL% NEQ 0 (
        echo Cluster inactif, démarrage...
        minikube start --driver=docker --memory=4096 --cpus=2
      ) else (
        echo Cluster actif.
      )

      echo ===== Copie de la configuration Minikube pour Jenkins =====
      if not exist "C:\\ProgramData\\Jenkins\\.kube" mkdir "C:\\ProgramData\\Jenkins\\.kube"
      
      REM Copier le kubeconfig de minikube vers Jenkins
      copy /Y "%USERPROFILE%\\.kube\\config" "C:\\ProgramData\\Jenkins\\.kube\\config"
      
      REM Copier les certificats minikube
      if not exist "C:\\ProgramData\\Jenkins\\.minikube" mkdir "C:\\ProgramData\\Jenkins\\.minikube"
      xcopy /E /I /Y "%USERPROFILE%\\.minikube\\ca.crt" "C:\\ProgramData\\Jenkins\\.minikube\\"
      xcopy /E /I /Y "%USERPROFILE%\\.minikube\\profiles" "C:\\ProgramData\\Jenkins\\.minikube\\profiles\\"

      echo ===== Mise à jour du kubeconfig pour Jenkins =====
      powershell -Command ^
        "$config = Get-Content 'C:\\ProgramData\\Jenkins\\.kube\\config' -Raw;" ^
        "$config = $config -replace [regex]::Escape('%USERPROFILE%'), 'C:\\ProgramData\\Jenkins';" ^
        "$config = $config -replace [regex]::Escape($env:USERPROFILE), 'C:\\ProgramData\\Jenkins';" ^
        "$config | Set-Content 'C:\\ProgramData\\Jenkins\\.kube\\config'"

      echo ===== Configuration kubectl =====
      set KUBECONFIG=C:\\ProgramData\\Jenkins\\.kube\\config
      kubectl config use-context minikube
      
      echo ===== Test de connectivité =====
      kubectl get nodes
      if %ERRORLEVEL% NEQ 0 (
        echo Échec de connexion au cluster.
        exit /b 1
      )
      
      echo ===== Création du namespace =====
      kubectl get namespace transport >nul 2>&1
      if %ERRORLEVEL% NEQ 0 (
        kubectl create namespace transport
      )
      
      echo Initialisation terminée avec succès.
    '''
  }
}

    stage('Deploy to Kubernetes') {
      steps {
        bat """
          powershell -Command "(Get-Content Deployment.yaml) -replace 'image: saadalaouisosse/trajet-service:1.0.0', 'image: ${IMAGE_NAME}' | Set-Content Deployment.yaml"

          kubectl get secret trajet-horraire-secret -n ${K8S_NAMESPACE} >nul 2>&1
          if %ERRORLEVEL% NEQ 0 (
            echo Création du secret trajet-horraire-secret...
            kubectl create secret generic trajet-horraire-secret ^
              --from-literal=DB_HOST=trajet-horraire-db ^
              --from-literal=DB_PORT=5432 ^
              --from-literal=DB_NAME=service_trajet_horraire ^
              --from-literal=DB_USER=trajet_horraire ^
              --from-literal=DB_PASS=trajet_horraire ^
              --from-literal=SPRING_PROFILES_ACTIVE=prod ^
              -n ${K8S_NAMESPACE}
          ) else (
            echo Secret déjà présent, aucune création nécessaire.
          )

          kubectl apply -f postgres-deployment.yaml -n ${K8S_NAMESPACE}
          kubectl apply -f postres-service.yaml -n ${K8S_NAMESPACE}
          kubectl apply -f Deployment.yaml -n ${K8S_NAMESPACE}
          kubectl apply -f service.yaml -n ${K8S_NAMESPACE}
          kubectl rollout status deployment/${APP_NAME} -n ${K8S_NAMESPACE} --timeout=5m
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
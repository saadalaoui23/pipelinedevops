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
    DOCKER_REGISTRY = "docker.io"
  }

  stages {

    stage('Checkout') {
      steps {
        echo "=== 📥 STAGE: Checkout ==="
        git branch: 'main', url: 'https://github.com/saadalaoui23/pipelinedevops.git'
        echo "✅ Code cloned successfully"
      }
    }

    stage('Build & Test') {
      steps {
        echo "=== 🔨 STAGE: Build & Test ==="
        bat 'mvn clean test'
        echo "✅ Build and tests completed"
      }
    }

    stage('SAST - SonarCloud Analysis') {
      steps {
        echo "=== 🔍 STAGE: SAST Analysis ==="
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
        echo "✅ SonarCloud analysis completed"
      }
    }

    stage('SCA - Dependency Check') {
      steps {
        echo "=== 📦 STAGE: Dependency Check ==="
        bat '''
          if not exist dependency-check-report mkdir dependency-check-report
          dependency-check.bat ^
            --project "service-trajet" ^
            --scan . ^
            --format "HTML" ^
            --format "JSON" ^
            --out dependency-check-report
        '''
        echo "✅ Dependency check completed"
      }
      post {
        always {
          archiveArtifacts artifacts: 'dependency-check-report/**', allowEmptyArchive: true
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        echo "=== 🐳 STAGE: Build Docker Image ==="
        bat "docker build -t ${IMAGE_NAME} ."
        echo "✅ Docker image built: ${IMAGE_NAME}"
      }
    }

    stage('Push Docker Image') {
      steps {
        echo "=== 📤 STAGE: Push Docker Image ==="
        withCredentials([string(credentialsId: 'dockerhub-token', variable: 'DOCKER_TOKEN')]) {
          bat """
            echo %DOCKER_TOKEN% | docker login -u saadalaouisosse --password-stdin
            docker push ${IMAGE_NAME}
          """
        }
        echo "✅ Docker image pushed to registry"
      }
    }

    stage('Verify Kubernetes Cluster') {
      steps {
        echo "=== ☸️  STAGE: Verify Kubernetes Cluster ==="
        bat '''
          echo ===== Verification de la configuration Kubernetes =====
          
          REM Vérifier que kubectl est disponible
          kubectl version --client >nul 2>&1
          if %ERRORLEVEL% NEQ 0 (
            echo kubectl non disponible.
            exit /b 1
          )
          
          echo ===== Verification de la connectivité au cluster =====
          kubectl get nodes
          if %ERRORLEVEL% NEQ 0 (
            echo Echec de connexion au cluster.
            exit /b 1
          )
          
          echo ===== Verification du namespace =====
          kubectl get namespace %K8S_NAMESPACE% >nul 2>&1
          if %ERRORLEVEL% NEQ 0 (
            echo Creation du namespace %K8S_NAMESPACE%...
            kubectl create namespace %K8S_NAMESPACE%
          ) else (
            echo Namespace %K8S_NAMESPACE% existe deja.
          )
          
          echo ===== Verification réussie =====
        '''
      }
    }

    stage('Deploy to Kubernetes') {
      steps {
        echo "=== 🚀 STAGE: Deploy to Kubernetes ==="
        bat """
          echo ===== Mise a jour de l'image dans le manifest =====
          powershell -Command "(Get-Content Deployment.yaml) -replace 'image: saadalaouisosse/trajet-service:1.0.0', 'image: ${IMAGE_NAME}' | Set-Content Deployment.yaml"
          
          echo ===== Verification et creation du secret =====
          kubectl get secret trajet-horraire-secret -n %K8S_NAMESPACE% >nul 2>&1
          if %ERRORLEVEL% NEQ 0 (
            echo Creation du secret trajet-horraire-secret...
            kubectl create secret generic trajet-horraire-secret ^
              --from-literal=DB_HOST=postgres-service ^
              --from-literal=DB_PORT=5432 ^
              --from-literal=DB_NAME=service_trajet_horraire ^
              --from-literal=DB_USER=trajet_horraire ^
              --from-literal=DB_PASS=trajet_horraire ^
              --from-literal=SPRING_PROFILES_ACTIVE=prod ^
              -n %K8S_NAMESPACE%
          ) else (
            echo Secret existe deja, aucune creation necessaire.
          )
          
          echo ===== Application des manifests Kubernetes =====
          kubectl apply -f postgres-deployment.yaml -n %K8S_NAMESPACE%
          kubectl apply -f postgres-service.yaml -n %K8S_NAMESPACE%
          kubectl apply -f Deployment.yaml -n %K8S_NAMESPACE%
          kubectl apply -f service.yaml -n %K8S_NAMESPACE%
          
          echo ===== Attente du rollout du deployment =====
          kubectl rollout status deployment/%APP_NAME% -n %K8S_NAMESPACE% --timeout=5m
          if %ERRORLEVEL% NEQ 0 (
            echo Echec du rollout status
            kubectl get pods -n %K8S_NAMESPACE%
            exit /b 1
          )
        """
        echo "✅ Deployment completed successfully"
      }
    }

    stage('Verify Deployment') {
      steps {
        echo "=== ✅ STAGE: Verify Deployment ==="
        bat '''
          echo ===== Verification des pods =====
          kubectl get pods -n %K8S_NAMESPACE%
          
          echo ===== Verification des services =====
          kubectl get svc -n %K8S_NAMESPACE%
          
          echo ===== Logs du dernier pod =====
          for /f "tokens=1" %%i in ('kubectl get pods -n %K8S_NAMESPACE% -l app=service-trajet -o jsonpath={.items[0].metadata.name} 2^>nul') do (
            echo Logs pour le pod: %%i
            kubectl logs %%i -n %K8S_NAMESPACE% --tail=50
          )
        '''
      }
    }

  }

  post {
    success {
      echo "✅ Pipeline succeeded: ${APP_NAME} deployed with image ${IMAGE_NAME}"
      bat "echo Pipeline Status: SUCCESS > pipeline-status.txt"
    }
    failure {
      echo "❌ Pipeline failed."
      bat '''
        echo Pipeline Status: FAILED > pipeline-status.txt
        kubectl get pods -n %K8S_NAMESPACE%
        for /f "tokens=1" %%i in ('kubectl get pods -n %K8S_NAMESPACE% -l app=service-trajet -o jsonpath={.items[0].metadata.name} 2^>nul') do (
          echo --- Logs d'erreur ---
          kubectl logs %%i -n %K8S_NAMESPACE%
        )
      '''
    }
    always {
      echo "Pipeline execution completed"
      cleanWs()
    }
  }
}
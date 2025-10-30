# Étape 1 : build de l’application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copier le pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier le code source et construire le jar
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : image d’exécution allégée
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copier le jar depuis le build
COPY --from=build /app/target/*.jar service-trajet-horaire.jar

# Exposer le port d’écoute de Spring Boot
EXPOSE 8080

# Définir les variables d’environnement (facultatif, remplacées par .env)
ENV SPRING_PROFILES_ACTIVE=prod
ENV LANG=C.UTF-8

# Lancer l’application
ENTRYPOINT ["java", "-jar", "service-trajet-horaire.jar"]

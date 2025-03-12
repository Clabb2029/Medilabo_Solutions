# Microservice Diabetes Risk

## Description
Le **microservice-diabetes-risk** est responsable de l'évaluation du risque de diabète pour un patient. Il fonctionne en analysant les notes médicales associées à un patient et en détectant la présence de mots-clés spécifiques liés au diabète. Ce microservice ne possède pas de base de données propre et repose sur les microservices **microservice-patient** et **microservice-note** pour récupérer les informations nécessaires,  et s'intègre avec les autres services via **Eureka Client**.

## Fonctionnalités
- **Analyse du risque de diabète** en fonction des notes médicales d’un patient.
- **Communication avec les microservices patient et note** pour récupérer les données nécessaires.
- **Détection d'occurrences de mots-clés liés au diabète**.
- **Utilisation de Feign Client pour interagir efficacement avec les autres microservices**.

## Prérequis
- **Java 17**
- **Spring Boot**
- **Eureka Client**
- **Spring Cloud OpenFeign**

## Installation et Exécution
### 1. Cloner le dépôt
```bash
git clone https://github.com/Clabb2029/Medilabo_Solutions.git
cd microservice-diabetes-risk
```

### 2. Lancer le microservice
#### Avec Maven
```bash
mvn spring-boot:run
```

#### Avec Docker
```bash
docker build -t microservice-diabetes-risk .
docker run -p 8084:8084 microservice-diabetes-risk
```


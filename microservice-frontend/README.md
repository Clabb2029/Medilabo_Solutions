# Microservice Frontend

## Description
Le **microservice-frontend** est responsable de l'affichage côté client. Il fournit une interface utilisateur pour interagir avec les autres microservices de l'application, et s'intègre avec les autres services via **Eureka Client**.



## Fonctionnalités
- **Affichage dynamique** des données grâce à **Thymeleaf**.
- **Communication avec les microservices backend** via **Spring Cloud OpenFeign**.
- **Navigation et gestion des données** avec **Spring HATEOAS**.
- **Interaction avec les microservices suivants** :
    - **microservice-patient** (gestion des patients)
    - **microservice-note** (gestion des notes médicales)
    - **microservice-diabetes-risk** (évaluation du risque de diabète)

## Prérequis
- **Java 17**
- **Spring Boot**
- **Thymeleaf**
- **Spring Cloud OpenFeign**
- **Spring HATEOAS**
- **Eureka Client**

## Installation et Exécution
### 1. Cloner le dépôt
```bash
git clone https://github.com/Clabb2029/Medilabo_Solutions.git
cd microservice-frontend
```

### 2. Lancer le microservice
#### Avec Maven
```bash
mvn spring-boot:run
```

#### Avec Docker
```bash
docker build -t microservice-frontend .
docker run -p 8082:8082 microservice-frontend
```

## Communication avec les autres microservices
Le **microservice-frontend** utilise **Spring Cloud OpenFeign** pour interagir avec les microservices backend. OpenFeign permet d'écrire des clients REST de manière déclarative, réduisant ainsi le code boilerplate et simplifiant les appels réseau.

Exemple d'utilisation d'OpenFeign pour interagir avec **microservice-patient** :
```java
@FeignClient(name = "microservice-patient")
public interface PatientClient {
    @GetMapping("/patients/{id}")
    PatientDTO getPatientById(@PathVariable("id") Long id);
}
```

Grâce à OpenFeign, il n'est pas nécessaire de gérer manuellement les requêtes HTTP, ce qui rend le code plus lisible et maintenable.
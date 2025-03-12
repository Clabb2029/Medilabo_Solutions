# Microservice Eureka Server

## Description
Le microservice **Eureka Server** est un service utilisé pour l'enregistrement et la gestion des microservices dans l'architecture de l'application. Il permet aux différents microservices de se détecter dynamiquement et de communiquer entre eux sans nécessiter de configuration statique des adresses réseau.

## Fonctionnalités
- Enregistrement automatique des microservices.
- Fourniture d'une liste dynamique des instances disponibles.
- Surveillance de l'état des microservices enregistrés.
- Équilibrage de charge côté client grâce à l'intégration avec Spring Cloud.

## Prérequis
- **Java 17**
- **Spring Boot**
- **Spring Cloud Netflix Eureka**

## Installation et Exécution
### 1. Cloner le dépôt
```bash
git clone https://github.com/Clabb2029/Medilabo_Solutions.git
cd eureka-server
```

### 2. Lancer le microservice
#### Avec Maven
```bash
mvn spring-boot:run
```

#### Avec Docker
Si vous utilisez Docker, assurez-vous d'avoir Docker installé puis exécutez :
```bash
docker build -t eureka-server .
docker run -p 8761:8761 eureka-server
```

## Accès à l'interface Eureka
Une fois le serveur lancé, l'interface de gestion Eureka est accessible à l'adresse :
```
http://localhost:8761/
```


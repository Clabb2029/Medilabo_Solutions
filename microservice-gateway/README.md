# Microservice Gateway

## Description
Le **microservice-gateway** est le point d'entrée unique de l'application. Il sert de passerelle pour acheminer les requêtes vers les différents microservices et implémente la gestion de la sécurité, y compris l'authentification des utilisateurs (médecins), et s'intègre avec les autres services via **Eureka Client**.

## Fonctionnalités
- **Routage intelligent** des requêtes vers les microservices appropriés.
- **Sécurisation de l'application** avec Spring Security et WebFlux.
- **Authentification et gestion des sessions**.
- **Filtrage et gestion des accès** aux ressources.
- **Connexion à la base de données MySQL Medilabo_Solutions** pour l'authentification.
- **Script SQL disponible pour la création de la table doctor et l'ajout d'un utilisateur de test.**

## Prérequis
- **Java 17**
- **Spring Boot**
- **Spring Cloud Gateway**
- **Spring WebFlux**
- **Spring Security**
- **Eureka Client**
- **MySQL**
- **R2DBC**

## Installation et Exécution
### 1. Cloner le dépôt
```bash
git clone https://github.com/Clabb2029/Medilabo_Solutions.git
cd microservice-gateway
```

### 2. Créer la table doctor dans la base de données
Le microservice-gateway gère l'authentification des utilisateurs en s'appuyant sur une base de données MySQL. Un script SQL, disponible dans le dossier doc, permet de créer la table doctor et d'ajouter un utilisateur de test afin de faciliter la connexion et l'évaluation de l'application.

### 3. Lancer le microservice
#### Avec Maven
```bash
mvn spring-boot:run
```

#### Avec Docker
```bash
docker build -t microservice-gateway .
docker run -p 8080:8080 microservice-gateway
```

## Sécurité et Authentification
Le microservice-gateway implémente un mécanisme de connexion basé sur **Spring Security WebFlux** avec **BCrypt** pour le hachage des mots de passe.

### Mécanisme d'authentification
- **Page de connexion** : `/microservice-frontend/login`
- **Redirection en cas de succès** : `/microservice-frontend/patient-list`
- **Gestion des erreurs** : Redirection vers `/microservice-frontend/login?error`
- **Déconnexion** : Disponible via l'endpoint `/logout`
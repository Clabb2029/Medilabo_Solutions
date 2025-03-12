# Microservice Patient

## Description
Le **microservice-patient** est un microservice backend dédié à la gestion des patients. Il permet d'effectuer les opérations suivantes :
- **Créer** un patient
- **Lire** les informations d'un patient
- **Modifier** un patient
- **Supprimer** un patient

Ce microservice stocke les données des patients dans une base de données **MySQL** et s'intègre avec les autres services via **Eureka Client**.

## Fonctionnalités
- **CRUD** sur les patients (Create, Read, Update, Delete)
- **Connexion à la base de données MySQL**
- **Utilisation de Spring HATEOAS** pour enrichir les réponses API

## Prérequis
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Web**
- **MySQL Connector**
- **Spring Cloud Eureka Client**
- **Spring HATEOAS**

## Installation et Exécution
### 1. Cloner le dépôt
```bash
git clone https://github.com/Clabb2029/Medilabo_Solutions.git
cd microservice-patient
```

### 2. Créer la table patient dans la base de données
Le microservice-patient gère la partie patients en s'appuyant sur une base de données MySQL. Un script SQL, disponible dans le dossier doc, permet de créer la table patient et d'insérer quelques données de test afin de faciliter l'utilisation et l'évaluation de l'application.

### 3. Lancer le microservice
#### Avec Maven
```bash
mvn spring-boot:run
```

#### Avec Docker
```bash
docker build -t microservice-patient .
docker run -p 8081:8081 microservice-patient
```

## API REST
Le microservice expose une API REST pour gérer les patients.

### Routes disponibles
- **Récupérer tous les patients** : `GET /patients`
- **Récupérer un patient par ID** : `GET /patient/{id}`
- **Créer un patient** : `POST /createPatient`
- **Mettre à jour un patient** : `PUT /patient/{id}/update`
- **Supprimer un patient** : `DELETE /patient/{id}/delete`
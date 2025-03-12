# Microservice Note

## Description
Le **microservice-note** est un microservice backend responsable de la gestion des notes médicales des patients. Il permet la création et la modification des notes associées à un patient. Ce microservice utilise une base de données NoSQL **MongoDB**, et s'intègre avec les autres services via **Eureka Client**.

## Fonctionnalités
- **Création de notes médicales** pour un patient.
- **Modification des notes existantes**.
- **Stockage des données dans MongoDB**.
- **Enregistrement et récupération efficaces des données** grâce à une structure flexible.

## Prérequis
- **Java 17**
- **Spring Boot**
- **MongoDB**
- **Eureka Client**

## Installation et Exécution
### 1. Cloner le dépôt
```bash
git clone https://github.com/Clabb2029/Medilabo_Solutions.git
cd microservice-note
```

### 2. Remplir la collection notes dans la base de données
Un fichier BSON est disponible dans le dossier doc pour pré-remplir la base de données avec des notes de test afin de faciliter l'utilisation et l'évaluation de l'application.

### 3. Lancer le microservice
#### Avec Maven
```bash
mvn spring-boot:run
```

#### Avec Docker
```bash
docker build -t microservice-note .
docker run -p 8083:8083 microservice-note
```


